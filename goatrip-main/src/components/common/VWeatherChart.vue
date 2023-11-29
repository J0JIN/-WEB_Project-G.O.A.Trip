<script setup>
import { ref } from "vue";
const { data } = defineProps(["data"]);

const chartOptions = ref({
  chart: {
    id: "weather-chart",
    type: "area",
  },
  xaxis: {
    categories: data.map((item) => item.fcstTime),
  },
  yaxis: {
    min: Math.min(...data.map((item) => item.fcstValue)) - 2,
    max: Math.max(...data.map((item) => item.fcstValue)) + 2,
    title: {
      text: "Temperature",
    },
  },
  dataLabels: {
    enabled: true,
    style: {
      colors: ["#089071"], // 숫자의 색상 설정
    },
  },
  stroke: {
    curve: "smooth",
    colors: ["#089071"],
  },
  fill: {
    type: "gradient", // 선 아래 영역을 gradient로 설정
    colors: ["#00000000"], // gradient의 색상 설정
    opacityFrom: 0.5,
    opacityTo: 0.2,
  },
  // grid: {
  //   borderColor: "#e7e7e7",
  //   row: {
  //     colors: ["#f3f3f3", "transparent"], // takes an array which will be repeated on columns
  //     opacity: 0.5,
  //   },
  // },
});

const chartSeries = ref([
  {
    name: "Temperature",
    data: data.map((item) => item.fcstValue),
  },
]);
</script>

<template>
  <div>
    <apexchart
      :type="chartOptions.chart.type"
      :options="chartOptions"
      :series="chartSeries"
      height="200"
    />
  </div>
</template>

<style scoped></style>
