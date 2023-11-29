<script setup>
import router from "../../router";
import { ref, onMounted, computed } from "vue";
import { storeToRefs } from "pinia";
import { usePointStore } from "../../stores/Pointer";

import VKakaoIterMap from "../common/VKakaoIterMap.vue";

const PointStore = usePointStore();
const { StartPoint, setStart, setEnd, setStopOver } = PointStore;
const { data } = defineProps(["data"]);
const searchPageMap = ref(0);

// const { getList } = storeToRefs(PointStore);
// const sampleData = getList;

const onClickCard = (item) => {
  console.log("in onClickCard >>", item);
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
    <div class="card" @click="onClickCard(data.stopover)">
      <div class="card-body">
        <h5 class="card-title">추천 여행</h5>
      </div>
      <VKakaoIterMap :points="data.stopover" :mapId="searchPageMap" :slide="null"></VKakaoIterMap>
      <div class="card-body" v-for="item in data">
        {{ item.title }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.container-fluid {
  padding-top: 5px;
  padding-bottom: 5px;
  border: 1px solid #ccc;
  border-radius: 8px;
  margin-bottom: 3px;
}

.user-style {
  background-color: #f8f9fa;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 50px;
}

.active {
  background-color: #ccc;
  border-color: #ddd;
}

.scrollable-list {
  max-height: 500px; /* 원하는 높이로 조절하세요 */
  overflow-y: auto;
}
</style>
