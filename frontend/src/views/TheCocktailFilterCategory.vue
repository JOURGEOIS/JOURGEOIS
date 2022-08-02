<template>
  <div class="the-cocktail-filter-category-view">
    <header-basic :prev="true" :success="false" @prevClicked="$router.go(-1)">
    </header-basic>
    <section></section>
  </div>
</template>

<script setup lang="ts">
import HeaderBasic from "@/components/basics/HeaderBasic.vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const index = route.params.category;
</script>

<style scoped lang="scss">
.the-cocktail-filter-category-view {
  @include flex(column);
  justify-content: flex-start;
  align-items: center;
  @include accountLayOut;

  section {
    @include flex(column);
    width: 100%;
    gap: 36px;
    margin-top: 36px;

    @media #{$tablet} {
      width: 80%;
    }

    @media #{$pc} {
      width: 70%;
    }
  }
}
</style>
