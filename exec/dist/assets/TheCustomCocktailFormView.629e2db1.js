import{H as y}from"./HeaderSuccess.2fffcfe4.js";import{d as v,r as b,h as l,m as x,o as d,c as T,l as s,u as r,$ as k,K as D,k as V,_ as I,a1 as w,V as B,R as M,e as _,q as g,s as f,C as E,g as h,F as U,G as $,z as S,t as A}from"./index.b8a0ac78.js";import{I as L}from"./InputBasic.22711aa7.js";import{T as N,a as R}from"./TheCustomCocktailTextarea.b47c6fc2.js";import{T as P,a as q}from"./TheCustomCocktailRecipeInput.2d6ef3fb.js";import{L as H}from"./LoadingBasic.6a5a02f4.js";import"./image.3fc243e7.js";import"./TheSearchInput.2ee0a461.js";const O=["onSubmit"],z=v({__name:"TheCustomCocktailForm",setup(F){const o=V(),t={button:!0,required:!0,id:"custom-cocktail-title-input",label:"\uCE75\uD14C\uC77C \uC774\uB984",placeholder:"",type:"text",maxlength:20},i=b("normal"),a=l({get:()=>o.getters["customCocktail/getTitle"],set:e=>o.dispatch("customCocktail/setTitle",e)}),c=l({get:()=>o.getters["customCocktail/getDescription"],set:e=>o.dispatch("customCocktail/setDescription",e)});let u=x({});const C=e=>{u=e},m=()=>{const e={title:a.value,description:c.value,img:u};o.dispatch("customCocktail/submitCustomCocktailForm",e)};return(e,n)=>(d(),T("form",{onSubmit:D(m,["prevent"]),class:"custom-cocktail-form"},[s(L,{data:t,"input-style":i.value,modelValue:r(a),"onUpdate:modelValue":n[0]||(n[0]=p=>k(a)?a.value=p:null),modelModifiers:{trim:!0}},null,8,["input-style","modelValue"]),s(N,{onChangeImage:C}),s(P),s(q),s(R,{modelValue:r(c),"onUpdate:modelValue":n[1]||(n[1]=p=>k(c)?c.value=p:null),modelModifiers:{trim:!0}},null,8,["modelValue"])],40,O))}});const G=I(z,[["__scopeId","data-v-146b5df3"]]),K={class:"the-custom-cocktail-form-view"},j=S(" \uCEE4\uC2A4\uD140 \uCE75\uD14C\uC77C \uC81C\uC791 "),J={class:"top-view"},Q=v({__name:"TheCustomCocktailFormView",setup(F){const o=$(),t=V(),i=l(()=>t.getters["customCocktail/getAlertStatus"]),a=l(()=>t.getters["customCocktail/getErrorMessage"]),c=l(()=>t.getters["customCocktail/getLoadingStatus"]);w(i,()=>{i.value&&setTimeout(()=>u(),2e3)});const u=()=>{t.dispatch("customCocktail/changeAlertStatus",!1)};return B(()=>{t.dispatch("customCocktail/getOriginalCocktailData",o.params.cocktailId),t.dispatch("customCocktail/changeAlertStatus",!1),t.dispatch("customCocktail/toggleLoadingStatus",!1)}),M(()=>{t.dispatch("customCocktail/resetCocktailData")}),(C,m)=>(d(),T(U,null,[_("div",K,[s(y,{formId:"custom-cocktail-form",onPrevClicked:m[0]||(m[0]=e=>C.$router.go(-1))},{default:g(()=>[j]),_:1}),_("section",J,[s(G,{id:"custom-cocktail-form"})])]),r(i)?(d(),f(E,{key:0,onOffModal:u},{default:g(()=>[S(A(r(a)),1)]),_:1})):h("",!0),r(c)?(d(),f(H,{key:1})):h("",!0)],64))}});const at=I(Q,[["__scopeId","data-v-93ec7d8b"]]);export{at as default};
