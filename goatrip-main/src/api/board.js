import { localAxios } from "../util/http-commons";

const local = localAxios();

function listArticle(param, success, fail) {
  local.get(`/board/list`, { params: param }).then(success).catch(fail);
}

function detailArticle(article_id, success, fail) {
  local.get(`/board/${article_id}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
  console.log("boardjs article", article);
  local.post(`/board/register`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(article_id, success, fail) {
  local.get(`/board/modify/${article_id}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  local.put(`/board/modify`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(article_id, success, fail) {
  local.delete(`/board/${article_id}`).then(success).catch(fail);
}

export {
  listArticle,
    detailArticle,
    registArticle,
    getModifyArticle,
    modifyArticle,
    deleteArticle,
};
