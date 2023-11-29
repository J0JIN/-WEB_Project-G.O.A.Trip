import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const usePointStore = defineStore(
  "PointStore",
  () => {
    // state
    const StartPoint = ref({});
    const EndPoint = ref({});
    const StopOverPoint = ref([]);

    const itemList = ref([]);

    // getter
    const getList = computed(() => {
      itemList.value.length = 0;

      let sla = StartPoint.value.latitude;
      let slo = StartPoint.value.longitude;
      StopOverPoint.value.sort(
        (b, a) =>
          (b.latitude - sla) ** 2 +
          (b.longitude - slo) ** 2 -
          ((a.latitude - sla) ** 2 + (a.longitude - slo) ** 2)
      );

      itemList.value = [StartPoint.value, ...StopOverPoint.value, EndPoint.value];

      // console.log(itemList.value);
      return itemList.value;
    });

    // action
    const setStart = () => {
      let lat = 0;
      let lng = 0;
      function success(pos) {
        // 위치 정보를 가져오는데 성공했을 때 호출되는 콜백 함수 (pos : 위치 정보 객체)
        lat = pos.coords.latitude;
        lng = pos.coords.longitude;
        console.log(`현위치 : ${lat}, ${lng}`);
      }
      function fail(err) {
        lat = 37.510701;
        lng = 127.098557;
      }

      navigator.geolocation.getCurrentPosition(success, fail);
      StartPoint.value = {
        content_id: 0,
        title: "내 위치",
        addr1: "내 위치",
        first_image: "",
        latitude: 37.510701,
        longitude: 127.098557,
        overview: "내 위치입니다.",
      };
    };

    const setStopOver = (param) => {
      StopOverPoint.value = param;
    };

    const setEnd = (param) => {
      EndPoint.value = param;
    };

    return {
      StartPoint,
      EndPoint,
      StopOverPoint,
      setStart,
      setEnd,
      setStopOver,
      getList,
    };
  },
  { persist: true }
);
