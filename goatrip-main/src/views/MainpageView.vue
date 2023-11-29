<script setup>
import router from "../router";
import { ref, reactive, onMounted, computed } from "vue";
import { storeToRefs } from "pinia";
import { usePointStore } from "../stores/Pointer";
import { dfs_xy_conv, getUnderboundTime } from "../util/wheatherUtil";
import { listWheather } from "../api/weather";
import { getEndPoint, getStopOver } from "../api/attraction";

import Navigationbar from "../components/layout/Navigationbar.vue";
import VKakaoIterMap from "../components/common/VKakaoIterMap.vue";
import VWeatherChart from "../components/common/VWeatherChart.vue";
import LoadingBar from "../components/layout/LoadingBar.vue";

const PointStore = usePointStore();

const { StartPoint, setStart, setEnd, setStopOver } = PointStore;
const { getList } = storeToRefs(PointStore);

const slideSwitch = ref(false);

let arr = ref([]);
let ans = ref([]);

// 도착지점에 대한 param
const randParam = reactive([
  {
    start_long: StartPoint.longitude,
    start_lat: StartPoint.latitude,
    min_time: 0.5,
    max_time: 1,
    stopover: 2,
    walk_car: "car",
  },
  {
    start_long: StartPoint.longitude,
    start_lat: StartPoint.latitude,
    min_time: 2,
    max_time: 2.5,
    stopover: 3,
    walk_car: "walk",
  },
  {
    start_long: StartPoint.longitude,
    start_lat: StartPoint.latitude,
    min_time: 1,
    max_time: 1.5,
    stopover: 2,
    walk_car: "walk",
  },
]);

// 경유지점에 대한 param
const dummyStart = reactive({
  content_id: 0,
  title: "내 위치",
  addr1: "내 주소",
  first_image: "",
  latitude: StartPoint.latitude,
  longitude: StartPoint.longitude,
  overview: null,
});

const { VITE_WHEATHER_API_SERVICE_KEY } = import.meta.env;
const { date, time } = getUnderboundTime();
const rs = dfs_xy_conv("toXY", StartPoint.latitude, StartPoint.longitude);

const param = reactive({
  serviceKey: VITE_WHEATHER_API_SERVICE_KEY,
  pageNo: 1,
  numOfRows: 1000,
  dataType: "JSON",
  base_date: date,
  base_time: time,
  nx: rs.x,
  ny: rs.y,
});

onMounted(async () => {
  // 캐러셀 객체 가져오기
  const carousel = document.getElementById("carouselExampleCaptions");
  if (carousel) {
    carousel.addEventListener("slide.bs.carousel", handleSlide);
  }

  await getListWeather(param);

  setStart();
  const spoint = StartPoint;

  await getend(randParam);

  for (let i = 0; i < 3; i++) {
    arr[i] = {
      startPoint: dummyStart,
      endPoint: endpoint.value[i],
      stopoverNum: randParam[i].stopover,
    };
    // console.log("여기>>", arr[i]);
  }

  await getstopover(arr);

  for (let j = 0; j < 3; j++) {
    const data = {
      stopover: [dummyStart, ...stopoverpoint.value[j], endpoint.value[j]],
    };
    ans.value.push(data);
  }
});

// 날씨 리스트 가져오기
let filteredData;
let filteredDataSKY;
let filteredDataPTY;
const getListWeather = async (param) => {
  await listWheather(
    param,
    ({ data }) => {
      let dataArray = data.response.body.items.item;
      // console.log(dataArray);
      filteredData = filterDataByCategoryAndTime(dataArray, "TMP");
      filteredData = filteredData.splice(0, 10);
      // console.log(filteredData);

      filteredDataSKY = filterDataByCategoryAndTime(dataArray, "SKY");
      filteredDataSKY = filteredDataSKY.splice(0, 1);
      // console.log(filteredDataSKY);

      filteredDataPTY = filterDataByCategoryAndTime(dataArray, "PTY");
      filteredDataPTY = filteredDataPTY.splice(0, 1);
      // console.log(filteredDataPTY[0].fcstValue);
    },
    (err) => {
      console.log(err);
    }
  );
};

const filterDataByCategoryAndTime = (data, category) => {
  const filteredData = data.filter((item) => {
    return item.category === category;
  });
  return filteredData;
};

// 목적지 가져오기
const endpoint = ref([]);
const getend = async (param) => {
  // console.log("여가>>>", param);
  for (let i = 0; i < 3; i++) {
    await getEndPoint(
      param[i],
      (resp) => {
        endpoint.value.push(resp.data);
        // console.log("변수들>>>", randParam1[i]);
        // console.log("목적지>>>>" + i, endpoint.value[i]);
      },
      (error) => {
        console.log(error);
      }
    );
  }
};

// 경유지 가져오기
const stopoverpoint = ref([]);
const getstopover = async (param) => {
  for (let i = 0; i < 3; i++) {
    console.log(">>>", param[i]);
    await getStopOver(
      param[i],
      (resp) => {
        stopoverpoint.value.push(resp.data);
        // console.log("경유지>>>>" + i, stopoverpoint.value[i]);
      },
      (error) => {
        console.log(error);
      }
    );
  }
};

// 캐러셀 이동시 이벤트 핸들러
const handleSlide = () => {
  slideSwitch.value = !slideSwitch.value;
  // console.log(slideSwitch.value);
};

// 카드 클릭시 동작
const onClickCard = (item) => {
  item.splice(0, 1);
  setEnd(item.splice(item.length - 1, 1)[0]);
  setStopOver(item);

  router.push({
    name: "detail",
  });
};
</script>

<template>
  <div>
    <Navigationbar> </Navigationbar>
    <div class="container mt-4">
      <div class="row">
        <div class="col-md-9">
          <div class="Title"><h5>실시간 온도</h5></div>
          <!-- <hr /> -->
          <div>
            <div class="row" v-if="filteredData && filteredData.length > 0">
              <VWeatherChart :data="filteredData"></VWeatherChart>
            </div>
            <div v-else>
              <div class="d-flex justify-content-center">
                <LoadingBar></LoadingBar>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="Title"><h5>실시간 날씨</h5></div>
          <!-- <hr /> -->
          <div class="user-style">
            <!-- <div v-if="true" class="container-fluid wheather-box">
              <h5>날씨</h5>
              <hr />
            </div> -->
            <div class="wheather-box">
              <!-- <h5>강수</h5>
              <hr /> -->
              <div v-if="filteredDataPTY && filteredDataPTY.length > 0">
                <div v-if="filteredDataPTY[0].fcstValue === '0'">
                  <img src="../assets/image/weather/notrain.png" alt="비가 안내립니다" />
                </div>
                <div v-else-if="filteredDataPTY[0].fcstValue === '1'" class="container-fluid">
                  <img src="../assets/image/weather/rain.png" alt="비가 내립니다" />
                </div>
                <div v-else-if="filteredDataPTY[0].fcstValue === '2'" class="container-fluid">
                  비와 눈이 내립니다
                </div>
                <div v-else-if="filteredDataPTY[0].fcstValue === '3'" class="container-fluid">
                  눈이 내립니다
                </div>
                <div v-else class="container-fluid">소나기가 내립니다</div>
              </div>
              <div v-else>
                <div class="d-flex justify-content-center">
                  <LoadingBar></LoadingBar>
                </div>
              </div>

              <!-- 날씨 -->
              <div v-if="filteredDataSKY && filteredDataSKY.length > 0">
                <div v-if="filteredDataSKY[0].fcstValue === '1'" class="container-fluid">
                  날이 맑습니다.
                </div>
                <div v-else-if="filteredDataSKY[0].fcstValue === '3'" class="container-fluid">
                  구름이 많습니다
                </div>
                <div v-else class="container-fluid">날이 흐립니다</div>
              </div>
              <div v-else>
                <div class="d-flex justify-content-center">
                  <LoadingBar></LoadingBar>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="app" class="container mt-4">
      <div class="row">
        <div
          id="carouselExampleCaptions"
          class="carousel carousel-dark slide"
          data-bs-ride="carousel"
        >
          <div class="carousel-indicators">
            <button
              type="button"
              data-bs-target="#carouselExampleCaptions"
              data-bs-slide-to="0"
              class="active"
              aria-current="true"
              aria-label="Slide 1"
            ></button>
            <button
              type="button"
              data-bs-target="#carouselExampleCaptions"
              data-bs-slide-to="1"
              aria-label="Slide 2"
            ></button>
            <button
              type="button"
              data-bs-target="#carouselExampleCaptions"
              data-bs-slide-to="2"
              aria-label="Slide 3"
            ></button>
          </div>
          <div class="carousel-inner">
            <div
              v-for="(item, index) in ans"
              :key="index"
              :class="{ 'carousel-item': true, active: index === 0 }"
            >
              <div>
                <div
                  v-if="item.stopover && item.stopover.length > 0"
                  @click="onClickCard(item.stopover)"
                >
                  <div class="Title">
                    <h5>랜덤 추천 여행지 : {{ item.stopover[item.stopover.length - 1]?.title }}</h5>
                  </div>
                  <!-- <hr /> -->
                  <VKakaoIterMap
                    :points="item.stopover"
                    :mapId="index"
                    :slide="slideSwitch"
                  ></VKakaoIterMap>
                </div>
              </div>
            </div>
          </div>
          <button
            class="carousel-control-prev"
            type="button"
            data-bs-target="#carouselExampleCaptions"
            data-bs-slide="prev"
          >
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button
            class="carousel-control-next"
            type="button"
            data-bs-target="#carouselExampleCaptions"
            data-bs-slide="next"
          >
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#app {
  /* margin-bottom: 50px; */
}

/* .Title {
  background-color: var(--light);
  padding: 20px 10px 10px;
  margin-bottom: 20px;
  border-bottom: 2px solid rgb(0, 0, 0);
  border-radius: 3%;
  font-family: "nsBold";
} */

.spinner-style {
  display: flex;
  justify-items: center;
  align-items: center;
}

.container-fluid {
  padding-top: 5px;
  padding-bottom: 5px;
  border: 1px solid #ccc;
  border-radius: 8px;
  margin-top: 20px;
  margin-bottom: 5px;
  text-align: center;
  font-family: "nsLight";
}

.user-style {
  height: 200px;
}

img {
  margin: auto;
  display: block;
  width: 100px;
  height: 100%;
}
/* div {
  font-family: "nsLight";
} */
</style>
