<script setup>
import { ref } from "vue";
import router from "../../router";
import { useMemberStore } from "../../stores/member";
import { jwtDecode } from "jwt-decode";
import { userChange, userDeleteById } from "../../api/user";

const memberStore = useMemberStore();

const { userLogout, userLogin, getUserInfo } = memberStore;

let token = sessionStorage.getItem("accessToken");
let decodeToken = jwtDecode(token);
let id = decodeToken.user_id;

const user = ref({
  user_id: id,
  user_pw: "",
  user_name: "",
  user_email: "",
  user_email_domain: "",
});
const userModify = () => {
  console.log("변경합니다.");
  userChange(
    user.value,
    (response) => {
      let msg = "정보 수정 중 문제가 발생했습니다";
      if (response.status == 200) msg = "회원 정보 수정이 완료됐습니다.";
      alert(msg);
    },
    (error) => {
      console.error(error);
      alert("문제가 생겼다");
      router.go(0);
    }
  );
};
const onClickSearchTravel = () => {
  router.push({
    name: "search",
  });
};

const onClickBoard = () => {
  router.push({
    name: "board",
    params: {
      category: 3,
    },
  });
};

const onClickQnA = () => {
  router.push({
    name: "board",
    params: {
      category: 2,
    },
  });
};

const onClickNotice = () => {
  router.push({
    name: "board",
    params: {
      category: 1,
    },
  });
};

const onClickDeleteAccount = () => {
  router.push({
    path: "/",
  });
};

const onClickGoHome = () => {
  router.push({
    path: "/mainpage",
  });
};

const onClickMyTrip = () => {
  router.push({
    path: "/mytrip",
  });
};

const onClickLogout = () => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  console.log("디코드 토큰>>", decodeToken.user_id);
  userLogout(decodeToken.user_id);

  sessionStorage.clear();

  router.push({
    path: "/",
  });
};

const onClickDelete = () => {
  userDeleteById(
    id,
    (response) => {
      let msg = "회원탈퇴 중 문제가 발생했습니다";
      if (response.status == 200) {
        msg = "회원 탈퇴가 완료됐습니다.";
        sessionStorage.clear();
      }
      // alert(msg);
    },
    (error) => {
      console.error(error);
      // alert("문제가 생겼다");
      router.go(0);
    }
  );
};
</script>

<template>
  <div>
    <nav class="navbar navbar-expand-lg">
      <div class="container-fluid">
        <!-- 화면 축소시 설정-->
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link" @click="onClickGoHome">홈</a>
            </li>
            <!-- 마이페이지 -->
            <li class="nav-item dropdown">
              <a
                class="nav-link dropdown-toggle"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                마이페이지
              </a>
              <ul class="dropdown-menu">
                <li>
                  <a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#modifyModal"
                    >개인정보 수정</a
                  >
                </li>
                <li><hr class="dropdown-divider" /></li>
                <li>
                  <a class="dropdown-item" @click="onClickLogout">로그아웃</a>
                </li>
                <li><hr class="dropdown-divider" /></li>
                <li>
                  <a class="dropdown-item" data-bs-target="#deleteModal1" data-bs-toggle="modal"
                    >회원탈퇴</a
                  >
                </li>
              </ul>
            </li>
            <!-- 게시판 -->
            <li class="nav-item dropdown">
              <a
                class="nav-link dropdown-toggle"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                게시판
              </a>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" @click="onClickNotice">공지사항</a></li>
                <li><hr class="dropdown-divider" /></li>
                <li><a class="dropdown-item" @click="onClickQnA">QnA</a></li>
                <li><hr class="dropdown-divider" /></li>
                <li><a class="dropdown-item" @click="onClickBoard">자유게시판</a></li>
              </ul>
            </li>
            <!-- 내 여행 -->
            <li class="nav-item">
              <a class="nav-link" @click="onClickMyTrip">내 여행</a>
            </li>
          </ul>
          <form class="d-flex" role="search">
            <button class="btn" type="button" @click.prevent="onClickSearchTravel">
              랜덤 여행 검색
            </button>
          </form>
        </div>
      </div>
    </nav>
  </div>

  <!-- 개인정보 변경 Modal -->
  <div
    class="modal fade"
    id="modifyModal"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <form>
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">개인정보 변경</h1>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">
            <div id="container">
              <div id="loginBox">
                <div id="inputBox">
                  <!-- <div class="input-form-box">
                  <span>ID</span>
                  <input type="text" name="uid" class="form-control" v-model="id" />
                </div> -->
                  <div class="input-form-box">
                    <span>PW</span>
                    <input type="password" name="upw" class="form-control" v-model="user.user_pw" />
                  </div>
                  <!-- <div class="input-form-box">
                  <span>PW_CHK</span
                  ><input type="password" name="upw" class="form-control" v-model="pw2" />
                </div> -->
                  <div class="input-form-box">
                    <span>NAME </span
                    ><input
                      type="text"
                      name="uname"
                      class="form-control"
                      v-model="user.user_name"
                    />
                  </div>
                  <div class="input-form-box">
                    <span>EMAIL </span
                    ><input
                      type="text"
                      name="udomain"
                      class="form-control"
                      v-model="user.user_email"
                    />
                    <div>@</div>
                    <input
                      type="text"
                      name="umail"
                      class="form-control"
                      v-model="user.user_email_domain"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            <button type="submit" class="btn btn-primary" @click="userModify">변경</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!--회원 탈퇴 Modal-->
  <div
    class="modal fade"
    id="deleteModal1"
    aria-hidden="true"
    aria-labelledby="exampleModalToggleLabel"
    tabindex="-1"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalToggleLabel">회원 탈퇴</h1>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <div>정말로 회원 탈퇴하시겠습니까?</div>
          <div>회원 탈퇴 시 모든 데이터가 삭제되며, 복구할 수 없습니다.</div>
          <div>탈퇴 후에는 해당 계정으로 로그인할 수 없습니다.</div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
          <button
            type="button"
            class="btn btn-danger"
            data-bs-target="#deleteModal2"
            data-bs-toggle="modal"
            @click="onClickDelete"
          >
            회원 탈퇴
          </button>
        </div>
      </div>
    </div>
  </div>

  <div
    class="modal fade"
    id="deleteModal2"
    aria-hidden="true"
    aria-labelledby="exampleModalToggleLabel2"
    tabindex="-1"
    data-bs-backdrop="static"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalToggleLabel2">회원 탈퇴</h1>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
            @click="onClickDeleteAccount"
          ></button>
        </div>
        <div class="modal-body">
          <div>지금까지 서비스를 이용해주셔서 감사합니다.</div>
          <div>확인 버튼을 누르면 로그인 창으로 이동합니다.</div>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            data-bs-dismiss="modal"
            @click="onClickDeleteAccount"
          >
            확인
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.btn {
  border: 2px solid;
}
.btn:hover {
  background-color: var(--gray);
  color: var(--light);
}
#loginBox {
  width: 100%;
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
button {
  font-family: "nsBold";
}

#inputBox {
  margin: 10px;
}

a:hover {
  cursor: default;
}

.navbar {
  background-color: var(--theme2);
}
.modal {
  background-color: var(--modalback);
}

.navbar a {
  color: var(--theme3);
  font-family: "nsBold";
}

.navbar a:hover {
  color: var(--theme4);
}

.navbar-nav > li > .dropdown-menu a:hover {
  color: var(--theme2);
}

input[type="password"] {
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;

  &::placeholder {
    font-family: "nsLight";
  }
}
</style>
