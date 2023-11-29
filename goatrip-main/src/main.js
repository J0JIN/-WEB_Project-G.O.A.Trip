import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import VueApexCharts from "vue3-apexcharts";
import piniaPluginPersisedstate from "pinia-plugin-persistedstate";
import App from "./App.vue";
import router from "./router";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersisedstate);

// app.use(createPinia());
app.use(router);
app.use(pinia);
app.use(VueApexCharts);

app.mount("#app");
