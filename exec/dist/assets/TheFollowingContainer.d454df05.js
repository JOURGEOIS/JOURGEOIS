import{R as C}from"./RoundImage.d46a7b48.js";import{d as h,j as g,G as v,k as I,h as c,r as L,o as r,c as d,e as a,l as x,t as m,u,_ as F,V as B,R as T,F as y,i as R,s as U}from"./index.b8a0ac78.js";const D={class:"the-following-item"},S={class:"part-left"},V={class:"user-info-text"},$={class:"user-nickname"},E={class:"user-introduce"},N=h({__name:"TheListItemFollowing",props:{followee:null},setup(_){const e=_;g(),v();const o=I(),i=e.followee.nickname,t=e.followee.introduce;c(()=>o.getters["personalInfo/isLoggedIn"]);const n=L(e.followee.isFollowed);c(()=>{if(n.value===1)return"\uD314\uB85C\uC789";if(n.value===0)return"\uD314\uB85C\uC6B0"}),e.followee.uid;const l={image:e.followee.profileImg,width:"50px"};return(f,s)=>(r(),d("div",D,[a("div",S,[x(C,{"round-image":l,class:"the-following-profile"}),a("div",V,[a("h1",$,m(u(i)),1),a("p",E,m(u(t)),1)])])]))}});const j=F(N,[["__scopeId","data-v-5297136d"]]),G={class:"following-container"},M=h({__name:"TheFollowingContainer",setup(_){const e=g();v();const o=I(),i=c(()=>o.getters["follow/getFolloweeUsers"]),t=c(()=>o.getters["personalInfo/getUserInfoUserId"]),n=s=>{e.push({name:"TheChatRoomView",params:{userId:s.uid}})},l=s=>{const p={event:s,action:"follow/setFolloweeList",data:{userId:t.value}};o.dispatch("scroll/handleScroll",p)},f=s=>{o.dispatch("follow/setFolloweeList",s)};return B(()=>{window.addEventListener("scroll",l),f({userId:t.value})}),T(()=>{o.dispatch("follow/resetFollowUserData"),window.removeEventListener("scroll",l)}),(s,p)=>(r(),d("div",G,[(r(!0),d(y,null,R(u(i),(w,k)=>(r(),U(j,{key:`followee-${k}`,followee:w,onClick:b=>n(w)},null,8,["followee","onClick"]))),128))]))}});const A=F(M,[["__scopeId","data-v-037289f4"]]);export{A as default};
