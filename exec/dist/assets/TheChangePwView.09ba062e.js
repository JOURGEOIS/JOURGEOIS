import{d as m,h as o,X as a,V as C,c as l,e as n,l as r,q as h,s as v,Y as w,u as g,F as f,k as B,o as c,z as P,Z as _,_ as V}from"./index.b8a0ac78.js";import{H as T}from"./HeaderBasic.ac1a7e60.js";import{N as x}from"./NavBar.f319fd8d.js";const E={class:"change-pw-view"},N=P(" \uBE44\uBC00\uBC88\uD638 \uC7AC\uC124\uC815 "),k={class:"change-pw-section top-view"},y=m({__name:"TheChangePwView",setup(A){const e=B(),u=o(()=>e.getters["password/getChangePwCurrentTab"]);e.dispatch("navbar/setNavIconStatus",4);const d=[a(()=>_(()=>import("./ThePwCheckForm.b62e2479.js"),["assets/ThePwCheckForm.b62e2479.js","assets/ThePwCheckForm.e6763679.css","assets/index.b8a0ac78.js","assets/index.4d945b7d.css","assets/InputBasic.22711aa7.js","assets/InputBasic.dd269d4b.css","assets/ButtonBasic.vue_vue_type_script_setup_true_lang.200855d0.js"])),a(()=>_(()=>import("./ThePwChangeForm.991be5b3.js"),["assets/ThePwChangeForm.991be5b3.js","assets/ThePwChangeForm.e352a329.css","assets/index.b8a0ac78.js","assets/index.4d945b7d.css","assets/InputBasic.22711aa7.js","assets/InputBasic.dd269d4b.css","assets/ButtonBasic.vue_vue_type_script_setup_true_lang.200855d0.js"]))],i=o(()=>d[u.value]),p=t=>e.dispatch("password/changePwCurrentTab",t);return C(()=>{p(0)}),(t,s)=>(c(),l(f,null,[n("div",E,[r(T,{prev:!0,success:!1,onPrevClicked:s[0]||(s[0]=D=>t.$router.go(-1))},{default:h(()=>[N]),_:1}),n("section",k,[(c(),v(w(g(i))))])]),r(x)],64))}});const H=V(y,[["__scopeId","data-v-4580cd7d"]]);export{H as default};
