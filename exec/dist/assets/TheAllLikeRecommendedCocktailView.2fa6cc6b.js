import{T as C}from"./TheListItemCarouselCocktail.4772947b.js";import{H as h}from"./HeaderBasic.ac1a7e60.js";import{N as v}from"./NavBar.f319fd8d.js";import{d as f,h as w,V as L,R,c as n,l as r,q as A,e as a,F as d,i as B,u as S,k as b,o as l,s as I,j as T,x as V,y as g,z as m,_ as x}from"./index.b8a0ac78.js";import"./RoundImage.d46a7b48.js";const N=t=>(V("data-v-ebbf8f10"),t=t(),g(),t),y=N(()=>a("h1",{class:"title"},[m(" \uC8FC\uB958\uC8FC\uC544 "),a("span",{class:"important"},"HOT"),m(" \uCE75\uD14C\uC77C ")],-1)),E={class:"cocktail-list-view top-view-no-margin"},F={class:"the-item-container"},H=f({__name:"TheAllLikeRecommendedCocktailView",setup(t){T();const e=b();e.dispatch("navbar/setNavIconStatus",0);const u=w(()=>e.getters["carousel/getAllLikeRecommendedCocktails"]),k=o=>{e.dispatch("carousel/clickShowMoreItem",o)},i=o=>{const s={event:o,action:"carousel/setAllLikeRecommendedCocktails"};e.dispatch("scroll/handleScroll",s)},p=()=>{e.dispatch("carousel/setAllLikeRecommendedCocktails")};return L(()=>{window.addEventListener("scroll",i),p()}),R(()=>{e.dispatch("carousel/removeAllLikeRecommendedCocktails"),window.removeEventListener("scroll",i)}),(o,s)=>(l(),n(d,null,[r(h,{prev:!0,success:!1,onPrevClicked:s[0]||(s[0]=c=>o.$router.go(-1))},{default:A(()=>[y]),_:1}),a("div",E,[a("div",F,[(l(!0),n(d,null,B(S(u),(c,_)=>(l(),I(C,{key:_,data:c,onClick:$=>k(c)},null,8,["data","onClick"]))),128))])]),r(v)],64))}});const O=x(H,[["__scopeId","data-v-ebbf8f10"]]);export{O as default};