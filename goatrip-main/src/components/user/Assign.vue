<script setup>
import router from "../../router";
import { ref } from "vue";
import { userRegister } from "../../api/user";

const user = ref({
  user_id: "",
  user_pw: "",
  user_name: "",
  user_email: "",
  user_email_domain: "",
});

const onClickHome = () => {
  router.push({
    path: "/",
  });
};

const onClickRegister = () => {
  console.log(user.value);
  userRegister(
    user.value,
    (response) => {
      let msg = "회원 가입 중 문제가 발생했습니다.";
      if (response.status == 201) msg = "회원 가입이 완료됐습니다.";
      alert(msg);
      router.push({
        path: "/",
      });
    },
    (error) => {
      console.error(error);
      alert("문제가 생겼다");
      router.go(0);
    }
  );
};

// const id = ref("");
// const pw = ref("");
// const name = ref("");
// const email = ref("");
// const domain = ref("");
</script>

<template>
  <div id="container">
    <div id="loginBox">
      <div id="loginBoxTitle" class="Title">
        <h4>Assign Title</h4>
      </div>
      <form>
        <div id="inputBox">
          <div class="input-form-box">
            <span>ID </span
            ><input type="text" name="uid" class="form-control" v-model="user.user_id" />
          </div>
          <div class="input-form-box">
            <span>PW </span
            ><input type="password" name="upw" class="form-control" v-model="user.user_pw" />
          </div>
          <div class="input-form-box">
            <span>NAME </span
            ><input type="text" name="uname" class="form-control" v-model="user.user_name" />
          </div>
          <div class="input-form-box">
            <span>EMAIL </span
            ><input type="email" name="udomain" class="form-control" v-model="user.user_email" />
            <div>@</div>
            <input
              type="email"
              name="umail"
              class="form-control"
              v-model="user.user_email_domain"
            />
          </div>
          <div class="button-login-box">
            <button type="button" class="btn btn-danger btn-xs" @click.prevent="onClickHome">
              뒤로 가기
            </button>
            <button type="submit" class="btn btn-primary btn-xs" @click.prevent="onClickRegister">
              회원 가입
            </button>
          </div>
        </div>
      </form>
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
