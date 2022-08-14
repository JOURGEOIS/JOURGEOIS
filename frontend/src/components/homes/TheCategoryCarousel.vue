<template>
  <div class="carousel">
    <div
      v-for="(_, idx) in categorys"
      class="categorys"
      :class="{ selected: selectedCategory === categoryContents[idx].name }"
      :key="`category-${idx}`"
      @click="clickCategory(idx)"
    >
      {{ categoryContents[idx].nameKr }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { CarouselCocktail } from "../../interface";
const router = useRouter();
const store = useStore();

const selectedCategory = computed(() => {
  return store.getters["carousel/getSelectedCategory"];
});

const categoryContents = reactive([
  {
    name: "ALONE",
    nameKr: "집에서 혼자",
    selected: true,
  },
  {
    name: "PARTY",
    nameKr: "친구들과 함께",
    selected: false,
  },
  {
    name: "LOVE",
    nameKr: "연인과 함께",
    selected: false,
  },
  {
    name: "SPECIAL",
    nameKr: "특별한 날엔",
    selected: false,
  },
]);

const categorys = ["", "", "", ""];

const clickCategory = (idx: number) => {
  store.dispatch("carousel/setSelectedCategory", categoryContents[idx].name);
  // categoryContents.forEach((content) => {
  //   content.selected = false;
  // });
  // categoryContents[idx].selected = true;
  // // store.dispatch("carousel/clickCategory")
};
</script>

<style scoped lang="scss">
@include scrollbar;

.carousel {
  display: flex;
  gap: 20px;
  width: calc(100% + 40px);
  padding: 0 15px;
  margin: 0 -20px;
  overflow-x: auto;
  -webkit-overflow-scrolling: touch;
  scroll-snap-type: x mandatory;

  .categorys {
    @include flex;
    flex: 0 0 auto;
    gap: 20px;
    @include for-click;

    .carousel-img {
      // width: 150px;
      // height: 150px;
      aspect-ratio: 1/1;
      background : {
        size: cover;
        position: center center;
      }
      border-radius: 5px;
      border: 1px solid $white200;
    }
    .carousel-text {
      width: 100%;
      @include font-size-sub(12px);
    }
  }
}

.selected {
  @include font(16px, $fw-bold);
  color: $primary-color;
}
</style>
