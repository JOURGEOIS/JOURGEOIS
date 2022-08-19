import{d as b,h as a,V as f,R as g,X as h,c as y,e as t,l as d,p,u as r,s as _,Y as A,a5 as B,F as E,G as x,k as N,o as n,j as U,Z as m,_ as D}from"./index.b8a0ac78.js";import{T as L}from"./TheCocktailSearchHeader.71d543d2.js";import{N as P}from"./NavBar.f319fd8d.js";import"./TheSearchInput.2ee0a461.js";const F={class:"search-result-view"},O={class:"cocktail-search-container"},$={class:"tab-section"},j={class:"dynamic-component-section"},z=b({__name:"TheSearchResultView",setup(G){const c=U(),C=x(),e=N();e.dispatch("navbar/setNavIconStatus",3);const l=a(()=>e.getters["cocktailSearch/getSearchInputValue"]),k=a(()=>e.getters["searchResult/getCurrentTab"]),i=s=>{const u=k.value?"searchResult/setSearchUser":"searchResult/setSearchCocktailAll";e.dispatch("scroll/handleScroll",{event:s,action:u,data:{keyword:l.value}})};f(async()=>{e.dispatch("searchResult/removeSearchResult");const s=C.params.searchValue;await e.dispatch("cocktailSearch/setSearchInputValue",s),window.addEventListener("scroll",i),e.dispatch("searchResult/setSearchCocktailAll",{keyword:s}),setTimeout(()=>e.dispatch("searchResult/setSearchCocktailAll",{keyword:s}),300),e.dispatch("searchResult/setSearchUser",{keyword:s}),setTimeout(()=>e.dispatch("searchResult/setSearchUser",{keyword:s}),300)}),g(()=>{e.dispatch("searchResult/removeSearchResult"),window.removeEventListener("scroll",i)});const v=()=>{c.push({name:"TheCocktailSearchView",params:{searchValue:l.value}})},R=()=>{c.go(-1)},S=()=>{c.go(-1)},T=()=>e.dispatch("searchResult/setCurrentTab",0),V=()=>e.dispatch("searchResult/setCurrentTab",1),I=[h(()=>m(()=>import("./TheSearchResultCocktail.3bdde46f.js"),["assets/TheSearchResultCocktail.3bdde46f.js","assets/TheSearchResultCocktail.dc05ecf1.css","assets/TheListItemCocktail.33b29a19.js","assets/TheListItemCocktail.b815021a.css","assets/RoundImage.d46a7b48.js","assets/RoundImage.7dc7be59.css","assets/index.b8a0ac78.js","assets/index.4d945b7d.css"])),h(()=>m(()=>import("./TheSearchResultUser.7ecf3e9c.js"),["assets/TheSearchResultUser.7ecf3e9c.js","assets/TheSearchResultUser.45024bbe.css","assets/TheListItemUser.8d3de280.js","assets/TheListItemUser.7706ae17.css","assets/RoundImage.d46a7b48.js","assets/RoundImage.7dc7be59.css","assets/index.b8a0ac78.js","assets/index.4d945b7d.css"]))],o=a(()=>e.getters["searchResult/getCurrentTab"]),w=a(()=>I[o.value]);return(s,u)=>(n(),y(E,null,[t("div",F,[t("div",O,[d(L,{onClickInput:v,onClickBackIcon:R,onClickCloseIcon:S}),t("section",$,[t("div",{class:p(["tab-cocktail",`index-${r(o)}`])},[t("p",{onClick:T},"\uCE75\uD14C\uC77C")],2),t("div",{class:p(["tab-user",`index-${r(o)}`])},[t("p",{onClick:V},"\uACC4\uC815")],2)]),t("section",j,[(n(),_(B,null,[(n(),_(A(r(w))))],1024))])])]),d(P)],64))}});const Y=D(z,[["__scopeId","data-v-3d6425ee"]]);export{Y as default};
