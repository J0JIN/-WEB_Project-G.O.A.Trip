import { localAxios } from "../util/http-commons";

const local = localAxios();

async function getEndPoint(param, success, fail) {
  console.log("paramEnd >>>", param);
  await local.post(`/trip/getend`, param).then(success).catch(fail);
}

async function getStopOver(param, success, fail) {
  // console.log("paramStopOver >>>", param);
  await local.post(`/trip/getstopover`, param).then(success).catch(fail);
}

export { getEndPoint, getStopOver };
