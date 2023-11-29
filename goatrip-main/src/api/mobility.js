import { mobilityAxios } from "../util/http-commons";

const mobility = mobilityAxios();

async function getMoblilty(param, success, fail) {
  console.log("paramMob >>>", param);
  await mobility.post(`/waypoints/directions`, param).then(success).catch(fail);
}
export { getMoblilty };
