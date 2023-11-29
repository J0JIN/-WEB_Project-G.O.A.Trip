import axios from "axios";
import { httpStatusCode } from "./http-status";

const { VITE_VUE_API_URL } = import.meta.env;
const { VITE_WHEATHER_API_URL } = import.meta.env;
const { VITE_MOBILITY_API_URL } = import.meta.env;
const { VITE_KAKAO_MOBILITY_SERVICE_KEY } = import.meta.env;

// weather vue api axios instance
function weatherAxios() {
  const instance = axios.create({
    baseURL: VITE_WHEATHER_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
  });

  // Request 발생 시 적용할 내용.
  instance.defaults.headers.common["Authorization"] = "";
  instance.defaults.headers.post["Content-Type"] = "application/json";
  instance.defaults.headers.put["Content-Type"] = "application/json";

  return instance;
}

function mobilityAxios() {
  const instance = axios.create({
    baseURL: VITE_MOBILITY_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
      Authorization: `KakaoAK ${VITE_KAKAO_MOBILITY_SERVICE_KEY}`,
    },
  });
  return instance;
}
export { localAxios, weatherAxios, mobilityAxios };
