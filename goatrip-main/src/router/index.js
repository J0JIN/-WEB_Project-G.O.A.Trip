import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "",
      component: () => import("../components/user/Login.vue"),
    },
    {
      path: "/assign",
      name: "assign",
      component: () => import("../components/user/Assign.vue"),
    },
    {
      path: "/mainpage",
      name: "mainpage",
      component: () => import("../views/MainpageView.vue"),
    },
    {
      path: "/search",
      name: "search",
      component: () => import("../views/SearchpageView.vue"),
    },
    {
      path: "/detail",
      name: "detail",
      component: () => import("../views/DetailpageView.vue"),
    },
    {
      path: "/modify",
      name: "modify",
      component: () => import("../views/ModifypageView.vue"),
    },
    {
      path: "/board/:category",
      name: "board",
      // component: () => import("../views/BoardView.vue"),
      redirect: (to) => ({ name: "article-list", params: { category: to.params.category } }),
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("../components/boards/BoardList.vue"),
        },
        {
          path: "view/:article_id",
          name: "article-view",
          component: () => import("../components/boards/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("../components/boards/BoardWrite.vue"),
        },
        {
          path: "modify/:article_id",
          name: "article-modify",
          component: () => import("../components/boards/BoardModify.vue"),
        },
      ],
    },
    {
      path: "/mytrip",
      name: "mytrip",
      component: () => import("../views/MyTripView.vue"),
    },
  ],
});

export default router;
