<template class="the-search-result-user">
  <div class="no-content" v-if="!searchUsers.length">검색 결과가 없습니다</div>
  <the-list-item-user
    v-for="(item, idx) in searchUsers"
    :key="idx"
    :data="item"
  ></the-list-item-user>
</template>

<script setup lang="ts">
import TheListItemUser from "@/components/cocktails/TheListItemUser.vue";
import { computed } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
const route = useRoute();
const store = useStore();

const searchUsers = computed(() => {
  return store.getters["searchResult/getSearchUsers"];
});
</script>

<style scoped lang="scss">
.no-content {
  text-align: center;
  padding: 30px 0;
  @include font-size-sub(15px);
}

.big-icon {
  padding: 0;
  @include font-size-sub(30px);
}
</style>
