import { localAxios } from "../util/http-commons";

const local = localAxios();

async function userConfirm(param, success, fail) {
  await local.post(`/user/login`, param).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

async function userRegister(user, success, fail) {
  await local.post(`/user/register`, user).then(success).catch(fail);
}
async function userChange(user, success, fail) {
  await local.post(`/user/modify`, user).then(success).catch(fail);
}

async function userDeleteById(user_id, success, fail) {
  await local.get(`/user/delete/${user_id}`).then(success).catch(fail);
}

export {
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  userRegister,
  userChange,
  userDeleteById,
};
