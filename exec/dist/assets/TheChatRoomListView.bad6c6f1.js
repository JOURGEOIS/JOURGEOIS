import{d as f,h as s,V as T,R as g,X as r,c as k,e,l as _,q as b,p as d,u as c,s as u,Y as w,a5 as x,F as L,k as V,o as n,z as y,j as B,G as I,Z as l,x as A,y as D,_ as E}from"./index.b8a0ac78.js";import{H as N}from"./HeaderBasic.ac1a7e60.js";import{N as S}from"./NavBar.f319fd8d.js";const m=o=>(A("data-v-07c981e9"),o=o(),D(),o),P={class:"chat-room-list-view"},$=y(" \uCC44\uD305 "),F={class:"tab-section top-view"},z=m(()=>e("p",null,"\uCC44\uD305",-1)),H=[z],O=m(()=>e("p",null,"\uCE5C\uAD6C",-1)),j=[O],q={class:"dynamic-component-section"},G=f({__name:"TheChatRoomListView",setup(o){B(),I();const t=V();t.dispatch("navbar/setNavIconStatus",0),s(()=>t.getters["chatRoom/getChatRoomList"]),s(()=>t.getters["chatRoom/getCurrentTab"]),T(async()=>{t.dispatch("chatRoom/setChatRoomList")}),g(()=>{t.dispatch("chatRoom/resetChatRoomList"),t.dispatch("chatRoom/checkChatRoomList")});const p=()=>t.dispatch("chatRoom/setCurrentTab",0),h=()=>t.dispatch("chatRoom/setCurrentTab",1),C=[r(()=>l(()=>import("./TheChatRoomContainer.ec2d0323.js"),["assets/TheChatRoomContainer.ec2d0323.js","assets/TheChatRoomContainer.e5374eef.css","assets/RoundImage.d46a7b48.js","assets/RoundImage.7dc7be59.css","assets/index.b8a0ac78.js","assets/index.4d945b7d.css","assets/date.4eb08d1d.js"])),r(()=>l(()=>import("./TheFollowingContainer.d454df05.js"),["assets/TheFollowingContainer.d454df05.js","assets/TheFollowingContainer.be041e76.css","assets/RoundImage.d46a7b48.js","assets/RoundImage.7dc7be59.css","assets/index.b8a0ac78.js","assets/index.4d945b7d.css"]))],a=s(()=>t.getters["chatRoom/getCurrentTab"]),v=s(()=>C[a.value]);return(R,i)=>(n(),k(L,null,[e("div",P,[_(N,{prev:!0,success:!1,onPrevClicked:i[0]||(i[0]=K=>R.$router.go(-1))},{default:b(()=>[$]),_:1}),e("section",F,[e("div",{class:d(["tab-chat-room",`index-${c(a)}`]),onClick:p},H,2),e("div",{class:d(["tab-following",`index-${c(a)}`]),onClick:h},j,2)]),e("section",q,[(n(),u(x,null,[(n(),u(w(c(v))))],1024))])]),_(S)],64))}});const Y=E(G,[["__scopeId","data-v-07c981e9"]]);export{Y as default};