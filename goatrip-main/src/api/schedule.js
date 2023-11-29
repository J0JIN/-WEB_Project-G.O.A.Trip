import { localAxios } from "../util/http-commons";

const local = localAxios();

function listArticle(user_id, success, fail) {
  local.get(`/schedule/${user_id}`).then(success).catch(fail);
}

function detailArticle(article_id, success, fail) {
  local.get(`/schedule/${article_id}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  local.post(`/schedule/register`, JSON.stringify(article)).then(success).catch(fail);
}

// function getModifyArticle(article_id, success, fail) {
//   local.get(`/board/modify/${article_id}`).then(success).catch(fail);
// }

function modifyArticle(article, success, fail) {
  local.put(`/schedule/modify`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(article_id, success, fail) {
  local.delete(`/schedule/delete/${article_id}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  // getModifyArticle,
  modifyArticle,
  deleteArticle,
};
