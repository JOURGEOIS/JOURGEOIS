import{d as g,j as S,G as D,k as P,o as n,c as a,e,l as T,u as s,t as _,g as p,z as U,n as w,_ as x,h as c,V as B,R as L,a1 as V,F as f,i as $,x as E,y as R}from"./index.b8a0ac78.js";import{R as N}from"./RoundImage.d46a7b48.js";import{a as b}from"./date.4eb08d1d.js";const A={class:"item-header"},F={class:"user-part"},z={class:"user-info"},M={class:"user-nick-time"},j={class:"user-nickname"},G={class:"created-at"},q={class:"cocktail-liked"},H={key:0,class:"material-icons unliked"},J={key:1,class:"material-icons liked"},K={class:"item-content-container"},O={class:"item-text-part"},Q={class:"cocktail-description"},W=g({__name:"TheCommunityPostItem",props:{community:null},setup(i){const t=i,o=S();D(),P();const h=t.community.postId,y=t.community.profileImg,u=t.community.nickname,v=t.community.createTime,C=b(v),I=t.community.postImg,r=t.community.iLike,m=t.community.likes,l=t.community.description,d=()=>{o.push({name:"TheCommunityDescView",params:{feedId:h}})};return(k,ne)=>(n(),a("article",{class:"list-item-custom-cocktail",onClick:d},[e("div",A,[e("div",F,[e("div",z,[T(N,{"round-image":{image:s(y)}},null,8,["round-image"]),e("div",M,[e("div",j,_(s(u)),1),e("div",G,_(s(C)),1)])])]),e("div",q,[s(r)?p("",!0):(n(),a("span",H," favorite ")),s(r)?(n(),a("span",J," favorite ")):p("",!0),U(" "+_(s(m)),1)])]),e("div",K,[e("div",{class:"item-img-part",style:w({backgroundImage:`url(${s(I)})`})},null,4),e("div",O,[e("p",Q,_(s(l)),1)])])]))}});const X=x(W,[["__scopeId","data-v-d0184b9e"]]),Y=i=>(E("data-v-945ae9e3"),i=i(),R(),i),Z={key:0},ee={key:0,class:"community-post-none"},te=Y(()=>e("p",null,[e("span",{class:"material-icons-outlined"},"lock"),U("\uBE44\uACF5\uAC1C \uACC4\uC815\uC785\uB2C8\uB2E4.")],-1)),se=[te],oe=g({__name:"TheCommunityPostList",setup(i){const t=D(),o=P(),h=c(()=>o.getters["profileDesc/getCurrentUserPostCommunity"]),y=c(()=>o.getters["personalInfo/getUserInfoUserId"]),u=c(()=>o.getters["profileDesc/getCurrentUserData"]),v=c(()=>u.value.uid),C=c(()=>u.value.isPrivate),I=c(()=>v.value===y.value),r=l=>{const d={event:l,action:"profileDesc/getCurrentUserPostCommunityData",data:t.params.userId};o.dispatch("scroll/handleScroll",d)};B(()=>{window.addEventListener("scroll",r),o.dispatch("profileDesc/getCurrentUserPostCommunityData",t.params.userId)}),L(()=>{o.dispatch("profileDesc/resetCurrentUserPost"),window.removeEventListener("scroll",r)});const m=c(()=>t.params.userId);return V(m,()=>{o.dispatch("profileDesc/getCurrentUserPostCommunityData",m.value)}),(l,d)=>(n(),a(f,null,[(n(!0),a(f,null,$(s(h),k=>(n(),a("article",{key:k.postId},[T(X,{community:k},null,8,["community"])]))),128)),s(I)?p("",!0):(n(),a("section",Z,[s(C)?(n(),a("div",ee,se)):p("",!0)]))],64))}});const re=x(oe,[["__scopeId","data-v-945ae9e3"]]);export{re as default};