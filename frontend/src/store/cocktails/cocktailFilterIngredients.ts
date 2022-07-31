import axios from "axios";
import api from "../../api/api";
import { Module } from "vuex";
import { RootState } from "../index";
import { ingredients } from "../../assets/filter";

export interface CocktailFilterIngredientsStatus {
  [key: number]: object
}

export const cocktailFilterIngredients: Module<CocktailFilterIngredientsStatus, RootState> = {
  namespaced: true,
  state: {
    0: {
      name: "술",
      ingredients: {

      }

    },
    1: {
      name: "리큐르",
      ingredients: {

      }
    },
    2: {
      name: "음료수 ",
      ingredients: {

      }

    },
    3: {
      name: "추가 재료",
      ingredients: {
        
      }

    },
  }
}