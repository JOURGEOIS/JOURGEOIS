import axios from "axios";
import api from "../../api/api";
import { Module } from "vuex";
import { RootState } from "../index";
import { ingredients } from "../../assets/filter";

export interface CocktailFilterIngredientsStatus {
  [key: string]: object;
}

export const cocktailFilterIngredients: Module<
  CocktailFilterIngredientsStatus,
  RootState
> = {};
