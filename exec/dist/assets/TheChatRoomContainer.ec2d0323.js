import{R}from"./RoundImage.d46a7b48.js";import{a as C}from"./date.4eb08d1d.js";import{d,j as _,G as l,k as u,h as p,o,c as r,e as t,l as f,t as h,g as k,u as g,_ as v,F as I,i as x,s as L}from"./index.b8a0ac78.js";const w={class:"the-list-item-chat-room"},y={class:"part-left"},T={class:"chat-room-info-text"},b={class:"chat-room-nickname"},B={class:"chat-room-message"},D={key:0,class:"chat-room-new"},N={class:"part-right"},V={class:"chat-room-time"},$=d({__name:"TheListItemChatRoom",props:{data:null},setup(e){const s=e;_(),l(),u();const a={image:s.data.opponent.img,width:"50px"},i=p(()=>{const c=new Date(s.data.lastMessage.timestamp.seconds*1e3).toString();return C(c)});return(c,n)=>(o(),r("div",w,[t("div",y,[f(R,{"round-image":a,class:"chat-room-profile-image"}),t("div",T,[t("h1",b,h(e.data.opponent.nickname),1),t("p",B,h(e.data.lastMessage.message),1)]),e.data.hasNewMessage?(o(),r("div",D)):k("",!0)]),t("div",N,[t("div",V,h(g(i)),1)])]))}});const M=v($,[["__scopeId","data-v-eb6126b3"]]),S={class:"the-item-container"},z=d({__name:"TheChatRoomContainer",setup(e){const s=_();l();const a=u(),i=n=>{s.push({name:"TheChatRoomView",params:{userId:n.opponent.uid}}),a.dispatch("chatRoom/setCurrentChatRoom",n)},c=p(()=>a.getters["chatRoom/getChatRoomList"]);return(n,F)=>(o(),r("div",S,[(o(!0),r(I,null,x(g(c),m=>(o(),L(M,{key:`chatRoom-${m.chatRoomId}`,data:m,onClick:j=>i(m)},null,8,["data","onClick"]))),128))]))}});export{z as default};
