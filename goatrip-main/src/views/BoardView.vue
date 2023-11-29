<script setup>
import Navigationbar from "../components/layout/Navigationbar.vue";
</script>

<template>
  <Navigationbar></Navigationbar>
  <div>
    <RouterView :key="$route.fullPath"></RouterView>
  </div>
</template>

<style scoped></style>
