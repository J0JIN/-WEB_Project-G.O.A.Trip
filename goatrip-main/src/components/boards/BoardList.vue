<script setup>
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { listArticle } from "../../api/board.js";

import VSelect from "../../components/common/VSelect.vue";
import BoardListItem from "../../components/boards/item/BoardListItem.vue";
import VPageNavigation from "../../components/common/VPageNavigation.vue";

const router = useRouter();
const route = useRoute();
const { category } = route.params;

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "제목", value: "title" },
  { text: "작성자아이디", value: "writer" },
]);

const selectCategory = ref(0);
selectCategory.value = category;

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

var setTitle = "";
if (route.params.category == 1) {
  setTitle = "공지사항";
} else if (route.params.category == 2) {
  setTitle = "QnA";
} else {
  setTitle = "자유 게시판";
}

const param = ref({
  category: selectCategory.value,
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
});

onMounted(() => {
  getArticleList();
});

const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.key = val;
};

const getArticleList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value);
  listArticle(
    param.value,
    ({ data }) => {
      console.log("리스트에서 얻어온 값 : ", data);
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.error(error);
    }
  );
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: "article-write" });
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 text-center">
          <div class="Title">{{ setTitle }}</div>
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="row align-self-center mb-2">
          <div class="col-md-2 text-start">
            <button type="button" class="btn btn-outline-primary btn-sm" @click="moveWrite">
              글쓰기
            </button>
          </div>
          <div class="col-md-5 offset-5">
            <form class="d-flex">
              <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
              <div class="input-group input-group-sm ms-1">
                <input
                  type="text"
                  class="form-control"
                  v-model="param.word"
                  placeholder="검색어..."
                />
                <button class="btn btn-dark" type="button" @click="getArticleList">검색</button>
              </div>
            </form>
          </div>
        </div>
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col" class="articletitle">제목</th>
              <th scope="col">작성자</th>
              <th scope="col">조회수</th>
              <th scope="col">작성일</th>
            </tr>
          </thead>
          <tbody>
            <BoardListItem
              v-for="article in articles"
              :key="article.article_id"
              :article="article"
            ></BoardListItem>
          </tbody>
        </table>
      </div>
      <VPageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      ></VPageNavigation>
    </div>
  </div>
</template>

<style scoped>
div {
  font-family: "nsLight";
}

.Title {
  background-color: var(--light);
  padding: 20px 10px 10px;
  margin-bottom: 20px;
  border-bottom: 2px solid rgb(0, 0, 0);
  border-radius: 3%;
  font-family: "nsBold";
}
.articletitle {
  font-family: "nsBold";
}
</style>
