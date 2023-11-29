<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { article_id } = route.params;

const article = ref({});
var setTitle = "";
if (route.params.category == 1) {
  setTitle = "공지사항";
} else if (route.params.category == 2) {
  setTitle = "QnA";
} else {
  setTitle = "자유 게시판";
}
onMounted(() => {
  getArticle();
});

const getArticle = () => {
  // const { articleno } = route.params;
  console.log(article_id + "번글 얻으러 가자!!!");
  detailArticle(
    article_id,
    ({ data }) => {
      console.log(data);
      article.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  router.push({ name: "article-modify", params: { article_id } });
}

function onDeleteArticle() {
  // const { articleno } = route.params;
  console.log(article_id + "번글 삭제하러 가자!!!");
  deleteArticle(
    article_id,
    (response) => {
      if (response.status == 200) moveList();
    },
    (error) => {
      console.error(error);
    }
  );
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 text-center">
          <div class="Title">{{ article.title }}</div>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <p>게시판 : {{ setTitle }}</p>
          <h2 class="text-secondary">제목 : {{ article.title }}</h2>
        </div>
        <hr />
        <div class="row">
          <div class="col-md-12">
            <div class="clearfix align-content-center">
              <div id="writer-hit">
                <span class="fw-bold">작성자 : {{ article.writer }} </span>
                <span class="text-secondary fw-light">
                  {{ article.cerate_date }} 조회수 : {{ article.hit }}
                </span>
              </div>
            </div>
            <hr />
          </div>
          <!-- <div class="col-md-4 align-self-center text-end">댓글 : 17</div> -->
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
        </div>
        <hr />
        <div class="d-flex justify-content-end">
          <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
            글목록
          </button>
          <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
            글수정
          </button>
          <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle">
            글삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
div {
  font-family: "nsLight";
}

#writer-hit {
  display: flex;
  justify-content: space-between;
}
</style>
