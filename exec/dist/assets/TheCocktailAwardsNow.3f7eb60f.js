import{d as _,j as h,h as d,o as e,c as r,e as i,n as u,u as w,t as k,_ as m,k as p,F as A,i as N,g as f,l as C,V as g,R as v,s as I}from"./index.b8a0ac78.js";const L=_({__name:"TheCocktailAwardsNowItem",props:{cocktailAwardsNowItem:null,cocktailAwardsNowRank:null},setup(c){const a=c,t=h(),o=d(()=>`url(${a.cocktailAwardsNowItem.imgLink})`),s=d(()=>a.cocktailAwardsNowRank<=4?"#a2a2fc":"#c8cfdc"),n=()=>{t.push({name:"TheCocktailAwardsDescView",params:{feedId:a.cocktailAwardsNowItem.postId}})};return(l,V)=>(e(),r("div",{class:"cocktail-awards-now-item-card",onClick:n},[i("div",{class:"cocktail-awards-now-item-image",style:u({backgroundImage:w(o)})},null,4),i("p",null,k(c.cocktailAwardsNowItem.title),1),i("div",{class:"cocktail-awards-now-item-percentage",style:u({backgroundColor:w(s)})},k(c.cocktailAwardsNowItem.percentage),5)]))}});const y=m(L,[["__scopeId","data-v-fc1204c2"]]),T={class:"the-cocktail-awards-now-list"},B={key:0,class:"the-cocktail-awards-now-rank"},x=_({__name:"TheCocktailAwardsNowList",setup(c){const a=p(),t=d(()=>a.getters["cocktailAwards/getCocktailAwardsNowList"]);return(o,s)=>(e(),r("div",T,[(e(!0),r(A,null,N(w(t),(n,l)=>(e(),r("article",{class:"the-cocktail-awards-now-item",key:n.postId},[l<=4?(e(),r("div",B,k(`${l+1}\uB4F1`),1)):f("",!0),C(y,{"cocktail-awards-now-item":n,"cocktail-awards-now-rank":l},null,8,["cocktail-awards-now-item","cocktail-awards-now-rank"])]))),128))]))}});const S=m(x,[["__scopeId","data-v-6ef6daad"]]),$=_({__name:"TheCocktailAwardsNow",setup(c){const a=p(),t=o=>{const s={event:o,action:"cocktailAwards/fetchCocktailAwardsNowList",data:{}};a.dispatch("scroll/handleScroll",s)};return g(()=>{window.addEventListener("scroll",t),a.dispatch("cocktailAwards/fetchCocktailAwardsNowList")}),v(()=>{a.dispatch("cocktailAwards/resetCocktailAwardsList"),window.removeEventListener("scroll",t)}),(o,s)=>(e(),I(S))}});export{$ as default};