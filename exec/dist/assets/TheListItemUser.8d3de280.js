import{R as I}from"./RoundImage.d46a7b48.js";import{d as k,j as C,G as x,k as B,h as u,r as T,o as l,c as n,e as s,l as y,t as c,u as _,p as L,g as p,_ as R}from"./index.b8a0ac78.js";const U={class:"the-list-item-user"},V={class:"user-info-text"},D={class:"user-nickname"},F={class:"user-introduce"},N={class:"part-right"},P={key:0,class:"material-icons-outlined follow-icon"},S={class:"follow-text"},b=k({__name:"TheListItemUser",props:{data:null},setup(t){const o=t,f=C();x();const a=B(),m=u(()=>a.getters["personalInfo/isLoggedIn"]),e=T(o.data.isFollowed),h=u(()=>e?"\uD314\uB85C\uC789":"\uD314\uB85C\uC6B0"),i=o.data.uid,g=()=>{e.value?(a.dispatch("follow/unfollow",{uid:i}),e.value=0):(a.dispatch("follow/follow",{uid:i}),e.value=1)},w=()=>{f.push({name:"TheUserProfileView",params:{userId:o.data.uid}})},v={image:o.data.profileImg,width:"50px"};return(j,z)=>{var r,d;return l(),n("div",U,[s("div",{class:"part-left",onClick:w},[y(I,{"round-image":v}),s("div",V,[s("h1",D,c((r=t.data)==null?void 0:r.nickname),1),s("p",F,c((d=t.data)==null?void 0:d.introduce),1)])]),s("div",N,[e.value!==-1&&_(m)?(l(),n("span",{key:0,class:L(["follow-btn",{following:e.value,follow:!e.value}]),onClick:g},[e.value?p("",!0):(l(),n("span",P," person_add ")),s("span",S,c(_(h)),1)],2)):p("",!0)])])}}});const q=R(b,[["__scopeId","data-v-f4827c60"]]);export{q as T};
