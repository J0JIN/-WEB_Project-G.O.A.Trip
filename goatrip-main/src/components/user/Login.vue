<script setup>
import router from "../../router";
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "../../stores/member";

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;

const onClickAssignment = () => {
  router.push({
    name: "assign",
  });
};

const loginUser = ref({
  user_id: "",
  user_pw: "",
});

const onClickLogin = async () => {
  // if (loginUser.value.user_id === "123" && loginUser.value.user_pw === "123") {
  //   alert("로그인 성공");
  //   router.push({
  //     name: "mainpage",
  //   });
  // } else {
  //   alert("로그인 실패");
  // }

  // JWT 로그인

  console.log("login ing!!!! !!!");
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");
  console.log("111. ", token);
  console.log("isLogin: ", isLogin);
  if (isLogin) {
    console.log("로그인 성공아닌가???");
    getUserInfo(token);
  } else {
    alert("로그인 실패");
  }
  router.push({
    name: "mainpage",
  });
};
</script>

<template>
  <div id="container">
    <div id="loginBox">
      <div id="loginBoxTitle" class="Title">
        <h4>Login Title</h4>
      </div>
      <div id="inputBox">
        <div class="input-form-box">
          <span>ID</span
          ><input type="text" name="uid" class="form-control" v-model="loginUser.user_id" />
        </div>
        <div class="input-form-box">
          <span>PW</span
          ><input
            type="password"
            name="upw"
            class="form-control"
            v-model="loginUser.user_pw"
            @keyup.enter="onClickLogin"
          />
        </div>
        <div class="button-login-box">
          <button type="button" class="btn btn-danger btn-xs" @click.prevent="onClickAssignment">
            회원 가입
          </button>
          <button type="button" class="btn btn-primary btn-xs" @click.prevent="onClickLogin">
            로그인
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

html,
body {
  height: 100%;
  background: #ffffff;
}

#container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 500px;
}

#loginBox {
  width: 300px;
  text-align: center;
  background-color: #ffffff;
}
.input-form-box {
  border: 0px solid #ff0000;
  display: flex;
  margin-bottom: 5px;
}
.input-form-box > span {
  display: block;
  text-align: left;
  padding-top: 5px;
  min-width: 65px;
}
.button-login-box {
  margin: 10px 0;
  display: flex;
  justify-content: space-between;
}

.button-login-box > button {
  width: 45%;
}

#inputBox {
  margin: 10px;
}

#inputBox button {
  padding: 3px 5px;
}
</style>
