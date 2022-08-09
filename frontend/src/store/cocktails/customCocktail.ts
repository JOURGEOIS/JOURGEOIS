import { Module } from "vuex";
import { RootState } from "../index";
import axios from "axios";
import api from "../../api/api";
import { checkBadWord } from "../../functions/checkText";
import router from "../../router";

// ! main State
export interface CustomCocktailState {
  // 원본 칵테일 번호
  originalCocktailId: number;

  // 원본 칵테일 이름
  originalCocktailName: string;

  // 원본 칵테일 재료
  originalCocktailIngredients: string[];

  // 원본 칵테일 레시피 정보
  originalCocktailRecipe: string[];

  // 검색 자동완성 재료 리스트
  searchIngredientsList: string[];

  // 팝업 알림
  alertStatus: boolean;

  // 오류 메시지
  errorMessage: string;

  // 성공 메시지
  successMessage: string;

  // 커스텀 칵테일
  title: string;
  description: string;
  imgLink: string;

  // 로딩 팝업
  loadingStatus: boolean;
}

export const customCocktail: Module<CustomCocktailState, RootState> = {
  namespaced: true,

  state: {
    // 원본 칵테일 번호
    originalCocktailId: 0,

    // 원본 칵테일 이름
    originalCocktailName: "",

    // 원본 칵테일 재료
    originalCocktailIngredients: [],

    // 원본 칵테일 레시피 정보
    originalCocktailRecipe: [],

    // 검색 자동완성 재료 리스트
    searchIngredientsList: [],

    // 에러 팝업 상태
    alertStatus: false,

    // 에러 메시지
    errorMessage: "",

    // 성공 메시지
    successMessage: "",

    // 커스텀 칵테일 정보
    title: "",
    description: "",
    imgLink: "",

    // 로딩 팝업 상태
    loadingStatus: false,
  },

  getters: {
    // 원본 칵테일 번호
    getOriginalCocktailId: (state) => state.originalCocktailId,

    // 원본 칵테일 이름
    getOriginalCocktailName: (state) => state.originalCocktailName,

    // 원본 칵테일 재료 정보
    getOriginalCocktailIngredients: (state) =>
      state.originalCocktailIngredients,

    // 원본 칵테일 레세피 정보
    getOriginalCocktailRecipe: (state) => state.originalCocktailRecipe,

    // 검색 자동 완성 재료 리스트
    getSearchIngredientsList: (state) => state.searchIngredientsList,

    // 알럿 팝업 상태
    getAlertStatus: (state) => state.alertStatus,

    // 에러 메시지
    getErrorMessage: (state) => state.errorMessage,

    // 성공 메시지
    getSuccessMessage: (state) => state.successMessage,

    // 커스텀 칵테일 세팅
    getTitle: (state) => state.title,
    getDescription: (state) => state.description,
    getImgLink: (state) => state.imgLink,

    // 로딩 팝업
    getLoadingStatus: (state) => state.loadingStatus,
  },

  mutations: {
    // 원본 칵테일 번호
    SET_ORIGINAL_COCKTAIL_ID: (state, value: number) => {
      state.originalCocktailId = value;
    },

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

    // 에러 팝업
    SET_ALERT_STATUS: (state, value: boolean) => {
      state.alertStatus = value;
    },

    // 에러 메시지
    SET_ERROR_MESSAGE: (state, value: string) => {
      state.errorMessage = value;
    },

    // 성공 메시지
    SET_SUCCESS_MESSAGE: (state, value: string) => {
      state.successMessage = value;
    },

    // 커스텀 칵테일 정보
    SET_TITLE: (state, value: string) => {
      state.title = value;
    },
    SET_DESCRIPTION: (state, value: string) => {
      state.description = value;
    },
    SET_IMG_LINK: (state, value: string) => {
      state.imgLink = value;
    },

    // 로딩 팝업
    SET_LOADING_STATUS: (state, value: boolean) => {
      state.loadingStatus = value;
    },
  },

  actions: {
    // vuex 리셋
    resetCocktailData: ({ commit }) => {
      commit("SET_ORIGINAL_COCKTAIL_ID", 0);
      commit("SET_ORIGINAL_COCKTAIL_NAME", "");
      commit("SET_ORIGINAL_COCKTAIL_INGREDIENTS", []);
      commit("SET_ORIGINAL_COCKTAIL_RECIPE", []);
      commit("SET_ALERT_STATUS", false);
      commit("SET_ERROR_MESSAGE", "");
      commit("SET_SUCCESS_MESSAGE", "");
      commit("SET_TITLE", "");
      commit("SET_DESCRIPTION", "");
      commit("SET_IMG_LINK", "");
    },

    // description
    setDescription: ({ commit }, value: string) => {
      commit("SET_DESCRIPTION", value);
    },

    // Title
    setTitle: ({ commit }, value: string) => {
      commit("SET_TITLE", value);
    },

    // 알럿 팝업
    changeAlertStatus: ({ commit }, value: boolean) => {
      commit("SET_ALERT_STATUS", value);
    },

    // 로딩 팝업
    toggleLoadingStatus: ({ commit }, value: Boolean) => {
      commit("SET_LOADING_STATUS", value);
    },

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

          // 전처리한 데이터 저장
          commit("SET_ORIGINAL_COCKTAIL_ID", id);
          commit("SET_ORIGINAL_COCKTAIL_NAME", data.nameKR);
          commit("SET_ORIGINAL_COCKTAIL_INGREDIENTS", ingredients);
          commit("SET_ORIGINAL_COCKTAIL_RECIPE", recipe);
        })
        .catch((error) => console.error(error));
    },

    // 사진 업로드 임시 저장
    uploadImage: ({ rootGetters, dispatch, commit }, data) => {
      axios({
        url: api.post.uploadImage(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
          "Content-Type": "multipart/form-data",
        },
        data,
      })
        .then((response) => {
          commit("SET_IMG_LINK", response.data.url);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            commit("SET_ERROR_MESSAGE", "잠시 후에 시도해주세요");
            commit("SET_ALERT_STATUS", true);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "customCocktail/uploadImage",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    //------------------------------------ 재료 검색 ----------------------------------------
    // 재료 검색 자동완성
    searchIngredients: ({ commit }, keyword: string) => {
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
          commit("SET_ERROR_MESSAGE", "이미 등록된 재료입니다.");
          commit("SET_ALERT_STATUS", true);
        }
        if (!conditionB) {
          commit("SET_ERROR_MESSAGE", "최대 10개까지 등록할 수 있습니다.");
          commit("SET_ALERT_STATUS", true);
        }
      }
    },

    // 재료 삭제하기
    deleteIngredients: ({ commit, getters }, value: string) => {
      const ingredients: string[] = getters["getOriginalCocktailIngredients"];
      const newIngredients = ingredients.filter((item) => item !== value);
      commit("SET_ORIGINAL_COCKTAIL_INGREDIENTS", newIngredients);
    },

    //------------------------------------ 레시피 ----------------------------------------
    // 레시피 단계 추가하기
    addRecipeStep: ({ commit, getters }) => {
      const steps: string[] = getters["getOriginalCocktailRecipe"];
      if (steps.length > 9) {
        commit("SET_ERROR_MESSAGE", "최대 10개까지 등록할 수 있습니다.");
        commit("SET_ALERT_STATUS", true);
      } else {
        commit("SET_ORIGINAL_COCKTAIL_RECIPE", "");
      }
    },

    // 해당 인덱스 레시피 삭제하기
    deleteRecipeStep: ({ commit, getters }, value: number) => {
      const steps: string[] = getters["getOriginalCocktailRecipe"];
      steps.splice(value, 1);
      commit("SET_ORIGINAL_COCKTAIL_RECIPE", steps);
    },

    //------------------------------------ 제출 ----------------------------------------
    // 칵테일 제출전 유효성 검사 (빈 필드가 있는지 확인한다.)
    submitCustomCocktailForm: ({ commit, getters, dispatch }, data) => {
      const { title, img } = data;

      // 빈필드 확인 (빈 필드가 있으면 false)
      const imgRequired = img instanceof File;
      const titleRequired = !!title;
      const recipeRequired = !!(
        getters["getOriginalCocktailRecipe"].length > 0
      );
      const ingredientsRequired = !!(
        getters["getOriginalCocktailIngredients"].length > 0
      );

      // 전부 true면 유효성 검사 통과!
      if (
        imgRequired &&
        titleRequired &&
        recipeRequired &&
        ingredientsRequired
      ) {
        // 비속어 유효성 검사, 레시피 빈칸 유효성 검사
        dispatch("checkTextInput", data);
      } else {
        commit(
          "SET_ERROR_MESSAGE",
          "이름, 이미지, 재료, 제작 항목은 필수 입력입니다"
        );
        commit("SET_ALERT_STATUS", true);
      }
    },

    checkTextInput: ({ dispatch, getters, commit }, data) => {
      const { title, description } = data;

      // 레시피 중 빈 문자만 제출했는지 확인 (빈문자열이 있으면 false)
      const recipeNull = !getters["getOriginalCocktailRecipe"].some(
        (item: string) => item === ""
      );
      if (!recipeNull) {
        commit("SET_ERROR_MESSAGE", "제작 항목에는 빈 값을 입력할 수 없습니다");
        commit("SET_ALERT_STATUS", true);
        return;
      }

      // 비속어 확인 (비속어가 있으면 true)
      const titleCheck = checkBadWord(title);
      const descriptionCheck = checkBadWord(description);

      if (titleCheck || descriptionCheck) {
        commit("SET_ERROR_MESSAGE", "부적절한 언어가 포함되었습니다");
        commit("SET_ALERT_STATUS", true);
        return;
      }

      // 저장
      dispatch("saveCustomCocktail", data);
    },

    // 저장
    saveCustomCocktail: ({ commit, getters, rootGetters, dispatch }, data) => {
      // 로딩 on
      commit("SET_LOADING_STATUS", true);
      const { img, title, description } = data;
      // 저장하기
      axios({
        url: api.post.postCocktail(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
          "Content-Type": "multipart/form-data",
        },
        data: {
          img,
          description,
          title,
          baseCocktail: getters["getOriginalCocktailId"],
          ingredients: getters["getOriginalCocktailIngredients"].join(),
          recipe: getters["getOriginalCocktailRecipe"].join(" <> "),
          type: 1,
        },
      })
        .then((response) => {
          // 로딩 off
          commit("SET_LOADING_STATUS", false);

          // 상세 페이지로 이동
          router.replace({
            name: "TheCustomCocktailDescView",
            params: {
              cocktailId: getters["getOriginalCocktailId"],
              feedId: response.data,
            },
          });
        })
        .catch((error) => {
          // 로딩 off
          commit("SET_LOADING_STATUS", false);

          if (error.response.status !== 401) {
            // 실패 팝업
            if (error.response.data.fail) {
              commit("SET_ERROR_MESSAGE", error.response.data.fail);
            } else {
              commit("SET_ERROR_MESSAGE", "잠시 후에 시도해주세요");
            }
            commit("SET_ALERT_STATUS", true);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "customCocktail/saveCustomCocktail",
              params: data,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // ========================== 커스텀 칵테일 수정 ============================
    // 커스텀 칵테일 db 불러오기
    getCustomCocktailData: ({ rootGetters, commit, dispatch }, id) => {
      axios({
        url: api.post.postCocktail(),
        method: "get",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
        },
        params: {
          postId: id,
        },
      })
        .then((response) => {
          const data = response.data.customCocktail;
          const recipe = data.recipe.split(" <> ");

          commit("SET_ORIGINAL_COCKTAIL_NAME", data.baseCocktailName);
          commit("SET_ORIGINAL_COCKTAIL_ID", data.baseCocktail);
          commit("SET_ORIGINAL_COCKTAIL_INGREDIENTS", data.ingredients);
          commit("SET_ORIGINAL_COCKTAIL_RECIPE", recipe);
          commit("SET_TITLE", data.title);
          commit("SET_DESCRIPTION", data.description);
          commit("SET_IMG_LINK", data.imgLink);
        })
        .catch((error) => {
          if (error.response.status !== 401) {
            // 실패 팝업
            commit("SET_ERROR_MESSAGE", "잠시 후에 시도해주세요");
            commit("SET_ALERT_STATUS", true);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "customCocktail/getCustomCocktailData",
              params: id,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // 커스텀 칵테일 업데이트
    updateCustomCocktail: (
      { rootGetters, getters, commit, dispatch },
      params
    ) => {
      const { title, description, img, postId } = params;
      const recipe = getters["getOriginalCocktailRecipe"];
      const ingredients = getters["getOriginalCocktailIngredients"];

      // 빈 필드 유효성 검사
      const titleRequired = !title;
      const recipeRequired = !(ingredients.length > 0);
      const ingredientsRequired = !(recipe.length > 0);

      if (titleRequired || recipeRequired || ingredientsRequired) {
        commit(
          "SET_ERROR_MESSAGE",
          "이름, 이미지, 재료, 제작 항목은 필수 입력입니다"
        );
        commit("SET_ALERT_STATUS", true);
        return;
      }

      // 비속어 유효성 검사
      const titleCheck = checkBadWord(title);
      const descriptionCheck = checkBadWord(description);
      if (titleCheck || descriptionCheck) {
        commit("SET_ERROR_MESSAGE", "부적절한 언어가 포함되었습니다");
        commit("SET_ALERT_STATUS", true);
        return;
      }

      // 빈 문자 확인
      const recipeNull = !recipe.some((item: string) => item === "");
      if (!recipeNull) {
        commit("SET_ERROR_MESSAGE", "제작 항목에는 빈 값을 입력할 수 없습니다");
        commit("SET_ALERT_STATUS", true);
        return;
      }

      // 요청 보낼 데이터
      const data: any = {
        title,
        description,
        postId,
        ingredients: ingredients.join(),
        recipe: recipe.join(" <> "),
      };
      if (img) {
        data.img = img;
      }

      // 로딩 on
      commit("SET_LOADING_STATUS", true);
      // 요청 보내기
      axios({
        url: api.post.postCocktail(),
        method: "put",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
          "Content-Type": "multipart/form-data",
        },
        data,
      })
        .then((response) => {
          // 로딩 off
          commit("SET_LOADING_STATUS", false);

          router.replace({
            name: "TheCustomCocktailDescView",
            params: {
              cocktailId: getters["getOriginalCocktailId"],
              feedId: response.data,
            },
          });
        })
        .catch((error) => {
          // 로딩 off
          commit("SET_LOADING_STATUS", false);

          if (error.response.status !== 401) {
            if (error.response.data.fail) {
              commit("SET_ERROR_MESSAGE", error.response.data.fail);
            } else {
              commit("SET_ERROR_MESSAGE", "잠시 후에 시도해주세요");
            }
            commit("SET_ALERT_STATUS", true);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "customCocktail/updateCustomCocktail",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    //========================== 슈퍼 커스텀 칵테일 ===========
    // superCustomCocktail 업로드
    submitSuperCustomCocktail: (
      { rootGetters, getters, commit, dispatch },
      params
    ) => {
      const { title, description, img } = params;
      const recipe = getters["getOriginalCocktailRecipe"];
      const ingredients = getters["getOriginalCocktailIngredients"];

      // 빈 필드 유효성 검사
      const imgRequired = !(img instanceof File);
      const titleRequired = !title;
      const recipeRequired = !(ingredients.length > 0);
      const ingredientsRequired = !(recipe.length > 0);

      if (
        titleRequired ||
        recipeRequired ||
        ingredientsRequired ||
        imgRequired
      ) {
        commit(
          "SET_ERROR_MESSAGE",
          "이름, 이미지, 재료, 제작 항목은 필수 입력입니다"
        );
        commit("SET_ALERT_STATUS", true);
        return;
      }

      // 비속어 유효성 검사
      const titleCheck = checkBadWord(title);
      const descriptionCheck = checkBadWord(description);
      if (titleCheck || descriptionCheck) {
        commit("SET_ERROR_MESSAGE", "부적절한 언어가 포함되었습니다");
        commit("SET_ALERT_STATUS", true);
        return;
      }
      // 빈 문자 확인
      const recipeNull = !recipe.some((item: string) => item === "");
      if (!recipeNull) {
        commit("SET_ERROR_MESSAGE", "제작 항목에는 빈 값을 입력할 수 없습니다");
        commit("SET_ALERT_STATUS", true);
        return;
      }

      // 요청 보낼 데이터
      const data: any = {
        title,
        description,
        img,
        ingredients: ingredients.join(),
        recipe: recipe.join(" <> "),
      };

      console.log(data);
      // 로딩 on
      commit("SET_LOADING_STATUS", true);
      // 요청 보내기
      axios({
        url: api.post.postCocktail(),
        method: "post",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
          "Content-Type": "multipart/form-data",
        },
        data,
      })
        .then((response) => {
          // 로딩 off
          commit("SET_LOADING_STATUS", false);

          router.replace({
            name: "TheSuperCustomCocktailDescView",
            params: {
              feedId: response.data,
            },
          });
        })
        .catch((error) => {
          // 로딩 off
          commit("SET_LOADING_STATUS", false);

          if (error.response.status !== 401) {
            if (error.response.data.fail) {
              commit("SET_ERROR_MESSAGE", error.response.data.fail);
            } else {
              commit("SET_ERROR_MESSAGE", "잠시 후에 시도해주세요");
            }
            commit("SET_ALERT_STATUS", true);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "customCocktail/submitSuperCustomCocktail",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },

    // superCustomCocktail 수정
    updateSuperCustomCocktail: (
      { rootGetters, getters, commit, dispatch },
      params
    ) => {
      const { title, description, img, postId } = params;
      const recipe = getters["getOriginalCocktailRecipe"];
      const ingredients = getters["getOriginalCocktailIngredients"];

      // 빈 필드 유효성 검사
      const titleRequired = !title;
      const recipeRequired = !(ingredients.length > 0);
      const ingredientsRequired = !(recipe.length > 0);

      if (titleRequired || recipeRequired || ingredientsRequired) {
        commit(
          "SET_ERROR_MESSAGE",
          "이름, 이미지, 재료, 제작 항목은 필수 입력입니다"
        );
        commit("SET_ALERT_STATUS", true);
        return;
      }

      // 비속어 유효성 검사
      const titleCheck = checkBadWord(title);
      const descriptionCheck = checkBadWord(description);
      if (titleCheck || descriptionCheck) {
        commit("SET_ERROR_MESSAGE", "부적절한 언어가 포함되었습니다");
        commit("SET_ALERT_STATUS", true);
        return;
      }

      // 빈 문자 확인
      const recipeNull = !recipe.some((item: string) => item === "");
      if (!recipeNull) {
        commit("SET_ERROR_MESSAGE", "제작 항목에는 빈 값을 입력할 수 없습니다");
        commit("SET_ALERT_STATUS", true);
        return;
      }

      // 요청 보낼 데이터
      const data: any = {
        title,
        description,
        postId,
        ingredients: ingredients.join(),
        recipe: recipe.join(" <> "),
      };
      if (img) {
        data.img = img;
      }

      // 로딩 on
      commit("SET_LOADING_STATUS", true);
      // 요청 보내기
      axios({
        url: api.post.postCocktail(),
        method: "put",
        headers: {
          Authorization: rootGetters["personalInfo/getAccessToken"],
          "Content-Type": "multipart/form-data",
        },
        data,
      })
        .then((response) => {
          // 로딩 off
          commit("SET_LOADING_STATUS", false);

          router.replace({
            name: "TheSuperCustomCocktailDescView",
            params: {
              feedId: response.data,
            },
          });
        })
        .catch((error) => {
          console.error(error);
          // 로딩 off
          commit("SET_LOADING_STATUS", false);

          if (error.response.status !== 401) {
            if (error?.response?.data?.fail) {
              commit("SET_ERROR_MESSAGE", error.response.data.fail);
            } else {
              commit("SET_ERROR_MESSAGE", "잠시 후에 시도해주세요");
            }
            commit("SET_ALERT_STATUS", true);
          } else {
            // refreshToken 재발급
            const obj = {
              func: "customCocktail/updateSuperCustomCocktail",
              params,
            };
            dispatch("personalInfo/requestRefreshToken", obj, { root: true });
          }
        });
    },
  },
};
