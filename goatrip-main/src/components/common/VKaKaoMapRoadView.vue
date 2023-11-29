<script setup>
import { ref, watch, onMounted } from "vue";
import { getMoblilty } from "../../api/mobility";

var map;

const positions = ref([]);
const markers = ref([]);
var line;

const props = defineProps({
  sampleData: Array,
});

let result = {};
onMounted(() => {
  // 시작 지점, 끝 지점 추출
  const origin = { x: props.sampleData[0].longitude, y: props.sampleData[0].latitude };
  const destination = {
    x: props.sampleData[props.sampleData.length - 1].longitude,
    y: props.sampleData[props.sampleData.length - 1].latitude,
  };

  // 중간 지점들을 데이터 개수 - 2개로 선택
  const waypoints = [];
  for (let i = 1; i < props.sampleData.length - 1; i++) {
    waypoints.push({
      name: props.sampleData[i].title,
      x: props.sampleData[i].longitude,
      y: props.sampleData[i].latitude,
    });
  }

  // 결과 형식으로 변환
  result = {
    origin,
    destination,
    waypoints,
    priority: "RECOMMEND",
    car_fuel: "GASOLINE",
    car_hipass: false,
    alternatives: false,
    road_details: false,
  };

  getMobliltyList(result);

  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

let extractedData = [];
const getMobliltyList = async (param) => {
  console.log("Mobility param>>>", param);
  await getMoblilty(
    param,
    (resp) => {
      console.log("Mobility resp>>>>", resp.data);

      resp.data.routes.forEach((route) => {
        route.sections.forEach((section) => {
          section.roads.forEach((road) => {
            road.vertexes.forEach(
              (vertex, index) => {
                // 추출된 데이터 배열에 추가
                if (index % 2 === 0) {
                  extractedData.push([road.vertexes[index + 1], road.vertexes[index]]);
                }
              }

              //extractedData.push([vertex[index + 1], vertex[index]])
            );
          });
        });
      });

      console.log("정렬된 데이터 >>", extractedData);
      drawLine(extractedData);
    },
    (error) => {
      console.log(error);
    }
  );
};

watch(
  () => props.sampleData,
  () => {
    loadMarkers();
  }
);

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  // 로드뷰 도로를 지도위에 올린다.
  map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);

  var roadviewContainer = document.getElementById("roadview"); // 로드뷰를 표시할 div
  var roadview = new kakao.maps.Roadview(roadviewContainer); // 로드뷰 객체
  var roadviewClient = new kakao.maps.RoadviewClient(); // 좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객
  // console.log("call");

  var mapCenter = new kakao.maps.LatLng(
    37.510701,
    127.098557
    // props.sampleData[0].latitude,
    // props.sampleData[0].longitude
  );
  roadviewClient.getNearestPanoId(mapCenter, 150, function (panoId) {
    roadview.setPanoId(panoId, mapCenter); // panoId와 중심좌표를 통해 로드뷰 실행
  });

  var mapWalker = null;

  kakao.maps.event.addListener(roadview, "init", function () {
    // map walker를 생성한다. 생성시 지도의 중심좌표를 넘긴다.
    mapWalker = new MapWalker(mapCenter);
    mapWalker.setMap(map); // map walker를 지도에 설정한다.

    // 로드뷰가 초기화 된 후, 추가 이벤트를 등록한다.
    // 로드뷰를 상,하,좌,우,줌인,줌아웃을 할 경우 발생한다.
    // 로드뷰를 조작할때 발생하는 값을 받아 map walker의 상태를 변경해 준다.
    kakao.maps.event.addListener(roadview, "viewpoint_changed", function () {
      // 이벤트가 발생할 때마다 로드뷰의 viewpoint값을 읽어, map walker에 반영
      var viewpoint = roadview.getViewpoint();
      mapWalker.setAngle(viewpoint.pan);
    });

    // 로드뷰내의 화살표나 점프를 하였을 경우 발생한다.
    // position값이 바뀔 때마다 map walker의 상태를 변경해 준다.
    kakao.maps.event.addListener(roadview, "position_changed", function () {
      // 이벤트가 발생할 때마다 로드뷰의 position값을 읽어, map walker에 반영
      var position = roadview.getPosition();
      mapWalker.setPosition(position);
      map.setCenter(position);
    });
  });
  loadMarkers();
};

const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.

  deleteMarkers();

  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다

  positions.value = [];
  props.sampleData.forEach((item) => {
    let obj = {};
    obj.latlng = new kakao.maps.LatLng(item.latitude, item.longitude);
    obj.title = item.title;

    // console.log(obj);
    positions.value.push(obj);
  });

  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
  //drawLine();
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};

const drawLine = (extractedData) => {
  if (line) {
    line.setMap(null);
  }

  // 현재 사용 중인 코드
  // const linePath2 = extractedData;
  // console.log("여기1>>>", linePath2);
  console.log(extractedData, "ext");
  const positions = extractedData.map(
    (position) => new kakao.maps.LatLng(position[0], position[1])
    //(pos) => console.log(pos, "pos")
  );
  // new kakao.maps.LatLng(pos.data[0], pos.data[1]
  // console.log("여기1>>>", linePath2);

  // // 주석 처리된 코드
  // const linePath = positions.value.map((position) => position.latlng);
  // console.log("여기2>>>", linePath);

  line = new kakao.maps.Polyline({
    path: positions,
    strokeWeight: 3,
    strokeColor: "#000000",
    strokeOpacity: 0.7,
    strokeStyle: "solid",
  });

  line.setMap(map);
};

function MapWalker(position) {
  //커스텀 오버레이에 사용할 map walker 엘리먼트
  var content = document.createElement("div");
  var figure = document.createElement("div");
  var angleBack = document.createElement("div");

  //map walker를 구성하는 각 노드들의 class명을 지정 - style셋팅을 위해 필요
  content.className = "MapWalker";
  figure.className = "figure";
  angleBack.className = "angleBack";

  content.appendChild(angleBack);
  content.appendChild(figure);

  //커스텀 오버레이 객체를 사용하여, map walker 아이콘을 생성
  var walker = new kakao.maps.CustomOverlay({
    position: position,
    content: content,
    yAnchor: 1,
  });

  this.walker = walker;
  this.content = content;
}

//로드뷰의 pan(좌우 각도)값에 따라 map walker의 백그라운드 이미지를 변경 시키는 함수
//background로 사용할 sprite 이미지에 따라 계산 식은 달라 질 수 있음
MapWalker.prototype.setAngle = function (angle) {
  var threshold = 22.5; //이미지가 변화되어야 되는(각도가 변해야되는) 임계 값
  for (var i = 0; i < 16; i++) {
    //각도에 따라 변화되는 앵글 이미지의 수가 16개
    if (angle > threshold * i && angle < threshold * (i + 1)) {
      //각도(pan)에 따라 아이콘의 class명을 변경
      var className = "m" + i;
      this.content.className = this.content.className.split(" ")[0];
      this.content.className += " " + className;
      break;
    }
  }
};

//map walker의 위치를 변경시키는 함수
MapWalker.prototype.setPosition = function (position) {
  this.walker.setPosition(position);
};

//map walker를 지도위에 올리는 함수
MapWalker.prototype.setMap = function (map) {
  this.walker.setMap(map);
};
</script>

<template>
  <div id="mapWrapper" style="width: 59%; height: 300px; float: left">
    <div id="map" style="width: 100%; height: 100%"></div>
  </div>

  <div id="rvWrapper" style="width: 40%; height: 300px; float: left">
    <div id="roadview" style="width: 100%; height: 100%"></div>
  </div>
</template>

<style>
.map_wrap {
  overflow: hidden;
  height: 330px;
}
/* 지도위에 로드뷰의 위치와 각도를 표시하기 위한 map walker 아이콘의 스타일 */
.MapWalker {
  position: absolute;
  margin: -26px 0 0 -51px;
}
.MapWalker .figure {
  position: absolute;
  width: 25px;
  left: 38px;
  top: -2px;
  height: 39px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png) -298px -114px
    no-repeat;
}
.MapWalker .angleBack {
  width: 102px;
  height: 52px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/2018/pc/roadview_minimap_wk_2018.png) -834px -2px
    no-repeat;
}
.MapWalker.m0 .figure {
  background-position: -298px -114px;
}
.MapWalker.m1 .figure {
  background-position: -335px -114px;
}
.MapWalker.m2 .figure {
  background-position: -372px -114px;
}
.MapWalker.m3 .figure {
  background-position: -409px -114px;
}
.MapWalker.m4 .figure {
  background-position: -446px -114px;
}
.MapWalker.m5 .figure {
  background-position: -483px -114px;
}
.MapWalker.m6 .figure {
  background-position: -520px -114px;
}
.MapWalker.m7 .figure {
  background-position: -557px -114px;
}
.MapWalker.m8 .figure {
  background-position: -2px -114px;
}
.MapWalker.m9 .figure {
  background-position: -39px -114px;
}
.MapWalker.m10 .figure {
  background-position: -76px -114px;
}
.MapWalker.m11 .figure {
  background-position: -113px -114px;
}
.MapWalker.m12 .figure {
  background-position: -150px -114px;
}
.MapWalker.m13 .figure {
  background-position: -187px -114px;
}
.MapWalker.m14 .figure {
  background-position: -224px -114px;
}
.MapWalker.m15 .figure {
  background-position: -261px -114px;
}
.MapWalker.m0 .angleBack {
  background-position: -834px -2px;
}
.MapWalker.m1 .angleBack {
  background-position: -938px -2px;
}
.MapWalker.m2 .angleBack {
  background-position: -1042px -2px;
}
.MapWalker.m3 .angleBack {
  background-position: -1146px -2px;
}
.MapWalker.m4 .angleBack {
  background-position: -1250px -2px;
}
.MapWalker.m5 .angleBack {
  background-position: -1354px -2px;
}
.MapWalker.m6 .angleBack {
  background-position: -1458px -2px;
}
.MapWalker.m7 .angleBack {
  background-position: -1562px -2px;
}
.MapWalker.m8 .angleBack {
  background-position: -2px -2px;
}
.MapWalker.m9 .angleBack {
  background-position: -106px -2px;
}
.MapWalker.m10 .angleBack {
  background-position: -210px -2px;
}
.MapWalker.m11 .angleBack {
  background-position: -314px -2px;
}
.MapWalker.m12 .angleBack {
  background-position: -418px -2px;
}
.MapWalker.m13 .angleBack {
  background-position: -522px -2px;
}
.MapWalker.m14 .angleBack {
  background-position: -626px -2px;
}
.MapWalker.m15 .angleBack {
  background-position: -730px -2px;
}
</style>
