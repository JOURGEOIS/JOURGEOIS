import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";

// ! main State
export interface CustomCocktailState {
  // 원본 칵테일 이름
  originalCocktailName: string;

  // 원본 칵테일 재료
  originalCocktailIngredients: string[];

  // 원본 칵테일 레시피 정보
  originalCocktailRecipe: string[];

  // 검색 자동완성 재료 리스트
  searchIngredientsList: string[];
}

export const customCocktail: Module<CustomCocktailState, RootState> = {
  namespaced: true,

  state: {
    // 원본 칵테일 이름
    originalCocktailName: "",

    // 원본 칵테일 재료
    originalCocktailIngredients: [],

    // 원본 칵테일 레시피 정보
    originalCocktailRecipe: [],

    // 검색 자동완성 재료 리스트
    searchIngredientsList: [],
  },

  getters: {
    // 원본 칵테일 이름
    getOriginalCocktailName: (state) => state.originalCocktailName,

    // 원본 칵테일 재료 정보
    getOriginalCocktailIngredients: (state) =>
      state.originalCocktailIngredients,

    // 원본 칵테일 레세피 정보
    getOriginalCocktailRecipe: (state) => state.originalCocktailIngredients,

    // 검색 자동 완성 재료 리스트
    getSearchIngredientsList: (state) => state.searchIngredientsList,
  },

  mutations: {
    // 원본 칵테일 이름
    SET_ORIGINAL_COCKTAIL_NAME: (state, value: string) =>
      (state.originalCocktailName = value),

    // 원본 칵테일 재료
    SET_ORIGINAL_COCKTAIL_INGREDIENTS: (state, value: string[] | string) => {
      if (typeof value === "string") {
        state.originalCocktailIngredients.push(value);
      } else {
        state.originalCocktailIngredients = value;
      }
    },

    // 원본 칵테일 레시피
    SET_ORIGINAL_COCKTAIL_RECIPE: (state, value: string[] | string) => {
      if (typeof value === "string") {
        state.originalCocktailRecipe.push(value);
      } else {
        state.originalCocktailRecipe = value;
      }
    },

    // 검색 자동 완성 리스트
    SET_SEARCH_INGREDIENTS_LIST: (state, value: string[]) =>
      (state.searchIngredientsList = value),
  },

  actions: {
    // 칵테일 정보 가져오기
    getOriginalCocktailData: ({ commit }, id: number) => {
      axios({
        url: api.cocktail.getCocktailData(),
        method: "get",
        params: {
          id,
        },
      })
        .then((response) => {
          const data = response.data;
          const ingredients = data.materials.map(
            (item: string) => item.split(",")[0]
          );
          const recipe = data.recipe.split(" <> ");

          commit("SET_ORIGINAL_COCKTAIL_NAME", data.nameKR);
          commit("SET_ORIGINAL_COCKTAIL_INGREDIENTS", ingredients);
          commit("SET_ORIGINAL_COCKTAIL_RECIPE", recipe);
        })
        .catch((error) => console.error(error));
    },

    // 재료 검색 자동완성
    searchIngredients: ({ commit }, keyword: string) => {
      console.log("몇번");
      axios({
        url: api.lookups.autoComplete(),
        method: "get",
        params: {
          keyword,
        },
      })
        .then((response) => {
          const data = response.data;
          if (data) {
            const ingredients = data
              ?.filter((item: { type: string }) => item.type === "재료")
              ?.map((item: { nameKr: string }) => item.nameKr);
            commit("SET_SEARCH_INGREDIENTS_LIST", ingredients);
          }
        })
        .catch((error) => console.error(error));
    },

    // 검색 자동완성 리스트 리셋하기
    resetSearchIngredients: ({ commit }) => {
      commit("SET_SEARCH_INGREDIENTS_LIST", []);
    },

    // 재료 추가하기
    addIngredients: ({ commit, getters }, value: string) => {
      const ingredients: string[] = getters["getOriginalCocktailIngredients"];

      // 조건 (중복된 재료인지, 최대 재료 개수를 초과했는지 확인한다.)
      const conditionA: boolean = !ingredients.includes(value);
      const conditionB: boolean = !(ingredients.length > 9);

      if (conditionA && conditionB) {
        commit("SET_ORIGINAL_COCKTAIL_INGREDIENTS", value);
      } else {
        if (!conditionA) {
          alert("중복된 재료를 등록할 수 없습니다. ");
        }
        if (!conditionB) {
          alert("최대 10개의 재료를 등록할 수 있습니다.");
        }
      }
    },

    // 재료 삭제하기
    deleteIngredients: ({ commit, getters }, value: string) => {
      const ingredients: string[] = getters["getOriginalCocktailIngredients"];
      const newIngredients = ingredients.filter((item) => item !== value);
      commit("SET_ORIGINAL_COCKTAIL_INGREDIENTS", newIngredients);
    },
  },
};
