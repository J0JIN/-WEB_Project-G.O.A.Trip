<script setup>
import { ref, reactive, computed } from "vue";
import { storeToRefs } from "pinia";
import { usePointStore } from "../stores/Pointer";
import { getEndPoint, getStopOver } from "../api/attraction";

import Navigationbar from "../components/layout/Navigationbar.vue";
import CardComponent from "../components/Trip/CardComponent.vue";
import LoadingBar from "../components/layout/LoadingBar.vue";

const PointStore = usePointStore();

const { getList } = storeToRefs(PointStore);
const { StartPoint, setStart, setEnd, setStopOver } = PointStore;
const sampleData = getList;
const spoint = StartPoint;

const type = ref("car");
const time = ref(0);
const stopover = ref(0);
const onClickReset = () => {
  time.value = 0;
  stopover.value = 0;
};

const param = computed(() => {
  return {
    start_long: spoint.longitude,
    start_lat: spoint.latitude,
    min_time: parseFloat(time.value),
    max_time: parseFloat(time.value) + 0.5,
    stopover: stopover.value,
    walk_car: type.value,
  };
});

// 목적지 가져오기
const endpoint = ref();
const getend = async (param) => {
  console.log("param >>", param);
  await getEndPoint(
    param,
    (resp) => {
      // console.log("data >>", resp.data);
      endpoint.value = resp.data;
      // console.log("endpoint >>", endpoint.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

// 경유지 가져오기
const stopoverpoint = ref([]);
const getstopover = async (param) => {
  await getStopOver(
    param,
    (resp) => {
      // console.log("datas>>>>", resp.data);
      stopoverpoint.value.push(resp.data);
    },
    (error) => {
      console.log(error);
    }
  );
};

let arr = ref({});
let data = ref({});
const onClickSearch = async () => {
  console.log("param>", param);
  await getend(param.value);

  arr.length = 0;
  arr = {
    startPoint: StartPoint,
    endPoint: endpoint.value,
    stopoverNum: stopover.value,
  };

  console.log("arr >>> ", arr);

  stopoverpoint.value = [];
  await getstopover(arr);

  data.length = 0;
  data.value = {
    stopover: [StartPoint, ...stopoverpoint.value[0], endpoint.value],
  };

  console.log("여기>>>>", data.value);
};
</script>

<template>
  <div>
    <Navigationbar></Navigationbar>
    <div class="container-fluid">
      <body>
        <div id="search-box" class="container mt-4">
          <!-- 차량, 도보여행-->
          <div id="search-box-select">
            <input
              type="radio"
              class="btn-check"
              name="options-select"
              id="car-select"
              autocomplete="off"
              v-model="type"
              value="car"
              checked
            />
            <label class="btn btn-outline-success" for="car-select">차량 여행</label>

            <input
              type="radio"
              class="btn-check"
              name="options-select"
              id="walk-select"
              autocomplete="off"
              v-model="type"
              value="walk"
            />
            <label class="btn btn-outline-success" for="walk-select">도보 여행</label>
          </div>

          <hr />
          <label for="customRange3" class="form-label"
            >예상 시간 : {{ time }} h ~ {{ parseFloat(time) + 0.5 }} h</label
          >
          <input
            v-model="time"
            type="range"
            class="form-control-range custom-range"
            min="0"
            max="4.5"
            step="0.5"
            id="customRange3"
          />

          <hr />
          <label for="customRange3" class="form-label">최대 경유지 수 : {{ stopover }}개</label>
          <input
            v-model="stopover"
            type="range"
            class="form-control-range custom-range"
            min="0"
            max="4"
            step="1"
            id="customRange3"
          />
          <hr />
          <div id="search-btn">
            <button type="button" class="btn btn-danger" @click="onClickReset">초기화</button>
            <button type="button" class="btn btn-success" @click="onClickSearch">검색</button>
          </div>
        </div>
      </body>

      <div id="app" class="container mt-4">
        <div class="row">
          <div class="col-md-12">
            <div v-if="Object.keys(data).length > 0">
              <CardComponent :data="data"></CardComponent>
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
</template>

<style scoped>
body {
  margin: 20px;
}
#search-box {
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

label {
  display: block;
  margin-bottom: 8px;
}

input,
select {
  width: 100%;
  padding: 8px;
  margin-bottom: 15px;
  box-sizing: border-box;
}

#search-box-select {
  display: flex;
  justify-content: space-between;
}

#search-box-select label {
  width: 50%;
}

.form-label {
  text-align: left;
}

#search-btn {
  display: flex;
  justify-content: right;
}

#search-btn button {
  margin-left: 5px;
}

.custom-range {
  padding: 0;
  margin: 0;
}

.container-fluid {
  max-width: 80%;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}
</style>
