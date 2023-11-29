<script setup>
import router from "../router";
import { jwtDecode } from "jwt-decode";
import { ref, onMounted, computed } from "vue";
import { storeToRefs } from "pinia";
import { usePointStore } from "../stores/Pointer";
import { listArticle } from "../api/schedule";

import VKakaoIterMap from "../components/common/VKakaoIterMap.vue";
import Navigationbar from "../components/layout/Navigationbar.vue";

const sampleDatas = ref([]);

const PointStore = usePointStore();

const { setStart, setEnd, setStopOver } = PointStore;
const { getList } = storeToRefs(PointStore);

const slideSwitch = ref(false);

let id;
onMounted(() => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  id = decodeToken.user_id;

  getTrip(id);
});

const getTrip = (param) => {
  listArticle(
    param,
    ({ data }) => {
      sampleDatas.value = data;
      console.log(">>>>", sampleDatas.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

const onClickCard = (item) => {
  item.splice(0, 1);
  console.log(">>", item);

  setEnd(item.splice(item.length - 1, 1)[0]);
  console.log(">>", item.value);

  setStopOver(item);

  const list = getList;
  console.log("카드클릭! >>", list);

  router.push({
    name: "modify",
  });
};
</script>

<template>
  <Navigationbar></Navigationbar>

  <div class="container mt-4">
    <div class="row">
      <div class="col">
        <img
          src="../assets/image/redmount.jpg"
          class="img-fluid"
          alt="이미지 설명"
          style="width: 100%; height: 200px; object-fit: cover"
        />
        <!-- <img src="../assets/image/whereami.png" alt="" style="width: 100%; height: 300px" /> -->
        <!-- <img
          src="../assets/image/redmount.jpg"
          alt="자연 사진"
          style="width: 100%; height: 400px"
        /> -->
      </div>
    </div>
  </div>

  <div id="app" class="container mt-4">
    <div class="row">
      <div class="col-md-3" v-for="(item, index) in sampleDatas" :key="index">
        <div class="card" @click="onClickCard(item.stopover)" style="border: none">
          <!-- <div class="card-body"> -->
          <div class="Title">
            <h5 class="card-title">{{ item.sch_name }}</h5>
          </div>
          <!-- </div> -->
          <VKakaoIterMap
            :points="item.stopover"
            :mapId="index"
            :slide="slideSwitch"
          ></VKakaoIterMap>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
