import { weatherAxios } from "../util/http-commons";

const weather = weatherAxios();

async function listWheather(param, success, fail) {
  await weather.get("", { params: param }).then(success).catch(fail);
}

export { listWheather };
