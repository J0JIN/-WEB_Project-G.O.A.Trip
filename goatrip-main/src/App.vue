<script setup>
import Header from "./components/layout/Header.vue";
import Footer from "./components/layout/Footer.vue";
</script>

<template>
  <div>
    <Header></Header>
    <!-- router view 설정 -->
    <RouterView></RouterView>
    <Footer></Footer>
  </div>
</template>

<style scoped></style>
