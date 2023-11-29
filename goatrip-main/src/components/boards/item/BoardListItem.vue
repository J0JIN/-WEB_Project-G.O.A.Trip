<script setup>
defineProps({ article: Object });
// console.log("article : "+article.value);
</script>

<template>
  <tr class="text-center">
    <td class="text-start">
      <router-link
        :to="{ name: 'article-view', params: { article_id: article.article_id } }"
        class="article-title link-dark"
      >
        {{ article.title }}
      </router-link>
    </td>
    <td>{{ article.writer }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.create_date }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
.text-start {
  font-family: "nsBold";
}
</style>
