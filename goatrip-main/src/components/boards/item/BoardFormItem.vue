<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { jwtDecode } from "jwt-decode";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);
let token = sessionStorage.getItem("accessToken");
let decodeToken = jwtDecode(token);
let id = decodeToken.user_id;

const article = ref({
  article_id: 0,
  title: "ddd",
  category: route.params.category,
  content: "",
  writer: id,
  user_name: "",
  hit: 0,
  create_date: "",
});
onMounted(() => {
  if (props.type === "modify") {
    let { article_id } = route.params;
    console.log(article_id + "번글 얻어와서 수정할거야");
    getModifyArticle(
      article_id,
      ({ data }) => {
        alert("???", data);
        article.value = data;
        isUseId.value = true;
      },
      (error) => {
        console.error(error);
      }
    );
    isUseId.value = true;
  }
});

const subjectErrMsg = ref("");
const contentErrMsg = ref("");

watch(
  () => article.value.title,
  (value) => {
    if (value) {
      console.log("value", value);
      let len = value.length;
      if (len == 0 || len > 30) {
        subjectErrMsg.value = "제목을 확인해 주세요!!!";
      } else subjectErrMsg.value = "";
    }
  },
  { immediate: true }
);

watch(
  () => article.value.content,
  (value) => {
    if (value) {
      let len = value.length;
      if (len == 0 || len > 500) {
        contentErrMsg.value = "내용을 확인해 주세요!!!";
      } else contentErrMsg.value = "";
    }
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", article.value);
  registArticle(
    article.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글등록이 완료되었습니다.";
      alert(msg);
      moveList();
    },
    (error) => console.error(error)
  );
}

function updateArticle() {
  console.log(article.value.article_id + "번글 수정하자!!", article.value);
  modifyArticle(
    article.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
      alert(msg);
      moveList();
      // router.push({ name: "article-view" });
      // router.push(`/board/view/${article.value.articleNo}`);
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.push({ name: "article-list" });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        id="userid"
        type="text"
        class="form-control"
        v-model="article.writer"
        :disabled="isUseId"
        placeholder="작성자ID..."
        readonly
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input id="subject" type="text" class="form-control" v-model="article.title" />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea id="content" class="form-control" v-model="article.content" rows="10"></textarea>
    </div>
    <div class="col-auto text-center">
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>글수정</button>
      <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveList">
        목록
      </button>
    </div>
  </form>
</template>

<style scoped></style>
