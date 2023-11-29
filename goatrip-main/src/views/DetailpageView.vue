<script setup>
import { ref, computed } from "vue";
import { jwtDecode } from "jwt-decode";
import { useRoute } from "vue-router";
import { registArticle } from "../api/schedule";
import { storeToRefs } from "pinia";
import router from "../router";
import { usePointStore } from "../stores/Pointer";
import { getEndPoint, getStopOver } from "../api/attraction";

import Navigationbar from "../components/layout/Navigationbar.vue";
import VKakaoMapDetail from "../components/common/VKakaoMapDetail.vue";

const MAX_SIZE = 4;

const PointStore = usePointStore();
const { StartPoint, EndPoint, StopOverPoint, setStart } = PointStore;
const { getList } = storeToRefs(PointStore);

const sampleData = getList;
const StopOver = ref(StopOverPoint);

const removeItemList = ref([]);
const selectedItem = ref({});
const activeIndex = ref(0);
const title = ref("");

let token = sessionStorage.getItem("accessToken");
let decodeToken = jwtDecode(token);
let id = decodeToken.user_id;

const arr = computed(() => {
  return {
    startPoint: StartPoint,
    endPoint: EndPoint,
    stopoverNum: 1,
  };
});

const addItem = () => {
  if (StopOver.value.length < MAX_SIZE) {
    console.log("Inner addItem>>>", arr.value);
    getstopover(arr.value);
  }
};

// 경유지 가져오기
const getstopover = async (param) => {
  await getStopOver(
    param,
    (resp) => {
      // console.log("resp addItem>>>", resp.data[0]);
      StopOver.value.push(resp.data[0]);
      // console.log("resp addItem>>>", StartOver.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

const removeItem = (index) => {
  if (index !== -1) {
    const removedItem = StopOver.value[index];
    StopOver.value.splice(index, 1);
    removeItemList.value.unshift(removedItem);

    if (StopOver.value.length > MAX_SIZE) {
      StopOver.value.pop();
    }
    if (removeItemList.value.length > MAX_SIZE) {
      removeItemList.value.pop();
    }
  }

  if (activeIndex.value >= StopOver.value.length) {
    activeIndex.value = StopOver.value.length - 1;
  }
};

const activateItem = (index) => {
  activeIndex.value = index;
  selectedItem.value = StopOver.value[index];
  // console.log(StopOver.value);
};

const onClickCancel = () => {
  EndPoint = null;
  StopOverPoint.length = 0;
  router.go(-1);
};

const data = ref({
  sch_name: null,
  user_id: id,
  start_lat: StartPoint.latitude,
  start_long: StartPoint.longitude,
  stopover: [],
});

const onClickSubmit = () => {
  setStart();
  data.value.sch_name = title.value;
  data.value.stopover = [...StopOver.value, EndPoint];
  console.log("inner onclickSubmit>>", data.value);
  registArticle(data.value);

  EndPoint.value = {};
  StopOverPoint.length = 0;
  router.go(-1);
};
</script>

<template>
  <div>
    <Navigationbar></Navigationbar>
    <div class="container-fluid root">
      <div class="row">
        <div class="col-md-6">
          <div class="container-fluid user-style">
            <div>{{ StopOver.length }} / {{ MAX_SIZE }}</div>
            <div><button @click="addItem" class="btn btn-primary">경유지 추가</button></div>
          </div>
          <div>
            <ul class="list-group">
              <li class="list-group-item d-flex justify-content-between align-items-center">
                {{ StartPoint.title }}
                <button class="btn btn-xs btn-secondary disabled" style="font-size: 10px">
                  ㅡ
                </button>
              </li>
              <li
                v-for="(item, index) in StopOver"
                :key="index"
                class="list-group-item d-flex justify-content-between align-items-center"
                @click="activateItem(index)"
                :class="{ active: index === activeIndex }"
              >
                경유지 > {{ item.title }}
                <button
                  @click="removeItem(index)"
                  class="btn btn-xs btn-danger"
                  style="font-size: 10px"
                >
                  ㅡ
                </button>
              </li>
              <li class="list-group-item d-flex justify-content-between align-items-center">
                {{ EndPoint.title }}
                <button class="btn btn-xs btn-secondary disabled" style="font-size: 10px">
                  ㅡ
                </button>
              </li>
            </ul>
          </div>
        </div>
        <div class="col-md-6">
          <div class="container-fluid user-style">
            <VKakaoMapDetail :sampleData="sampleData"></VKakaoMapDetail>
          </div>
        </div>
      </div>
      <hr />
      <div class="row">
        <div class="col-md-6">
          <!-- <div class="container-fluid"> -->
          <h5 class="Title container-fluid user-style">목적지 : {{ EndPoint.title }}</h5>
          <div class="content container-fluid user-style">
            {{ EndPoint.overview }}
          </div>
          <!-- </div> -->
        </div>
        <div class="col-md-6">
          <!-- <div class="container-fluid"> -->
          <h5 class="Title container-fluid user-style">
            경유지 :
            {{ StopOver[activeIndex] ? StopOver[activeIndex].title : "선택된 제목 없음" }}
          </h5>
          <div class="content container-fluid user-style">
            {{ StopOver[activeIndex] ? StopOver[activeIndex].overview : "선택된 내용 없음" }}
          </div>
          <!-- </div> -->
        </div>
      </div>
      <div class="row">
        <div id="button-group">
          <button type="button" class="btn btn-secondary" @click="onClickCancel">취소</button>
          <button
            type="button"
            class="btn btn-primary"
            data-bs-toggle="modal"
            data-bs-target="#insertModal"
          >
            일정 등록
          </button>
        </div>
      </div>
    </div>
  </div>

  <!-- 부트스트랩 모달 -->
  <!-- 개인정보 변경 Modal -->
  <div
    class="modal fade"
    id="insertModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">일정 등록</h1>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div id="container">
            <div id="loginBox">
              <div id="inputBox">
                <div class="input-form-box">
                  <span>일정 제목</span>
                  <input type="text" name="uid" class="form-control" v-model="title" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary">취소</button>
          <button
            type="button"
            class="btn btn-primary"
            data-bs-dismiss="modal"
            @click="onClickSubmit"
          >
            등록
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.root {
  margin-top: 10px;
  width: 80%;
}
.container-fluid {
  padding-top: 5px;
  padding-bottom: 5px;
  /* border: 1px solid #ccc; */
  border-radius: 8px;
  margin-top: 5px;
  margin-bottom: 5px;
}

.user-style {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.active {
  background-color: #ccc;
  border-color: #ddd;
}

#button-group {
  display: flex;
  justify-content: right;
}

#button-group > button {
  margin-left: 5px;
}
div {
  font-family: "nsEBold";
}
.content {
  font-family: "nsLight";
}
</style>
