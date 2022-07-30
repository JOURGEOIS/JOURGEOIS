const baseAlcohol: object[] = [
  {
    name: "Dark Rum",
    nameKr: "다크럼",
    img: "https://www.thecocktaildb.com/images/ingredients/Dark Rum.png",
  },
  {
    name: "Tequila",
    nameKr: "데킬라",
    img: "https://www.thecocktaildb.com/images/ingredients/Tequila.png",
  },
  {
    name: "Light Rum",
    nameKr: "라이트 럼",
    img: "https://www.thecocktaildb.com/images/ingredients/Light Rum.png",
  },
  {
    name: "Rum",
    nameKr: "럼",
    img: "https://www.thecocktaildb.com/images/ingredients/Rum.png",
  },
  {
    name: "Vodka",
    nameKr: "보드카",
    img: "https://www.thecocktaildb.com/images/ingredients/Vodka.png",
  },
  {
    name: "Bourbon",
    nameKr: "부르봉",
    img: "https://www.thecocktaildb.com/images/ingredients/Bourbon.png",
  },
  {
    name: "Brandy",
    nameKr: "브랜디",
    img: "https://www.thecocktaildb.com/images/ingredients/Brandy.png",
  },
  {
    name: "Champagne",
    nameKr: "샴페인",
    img: "https://www.thecocktaildb.com/images/ingredients/Champagne.png",
  },
  {
    name: "Scotch",
    nameKr: "스카치",
    img: "https://www.thecocktaildb.com/images/ingredients/Scotch.png",
  },
  {
    name: "Gin",
    nameKr: "진",
    img: "https://www.thecocktaildb.com/images/ingredients/Gin.png",
  },
  {
    name: "Cachaca",
    nameKr: "카샤샤",
    img: "https://www.thecocktaildb.com/images/ingredients/Cachaca.png",
  },
  {
    name: "Cognac",
    nameKr: "코냑",
    img: "https://www.thecocktaildb.com/images/ingredients/Cognac.png",
  },
  {
    name: "Prosecco",
    nameKr: "프로스코",
    img: "https://www.thecocktaildb.com/images/ingredients/Prosecco.png",
  },
  {
    name: "White Rum",
    nameKr: "화이트 럼",
    img: "https://www.thecocktaildb.com/images/ingredients/White Rum.png",
  },
  {
    name: "White Wine",
    nameKr: "화이트 와인",
    img: "https://www.thecocktaildb.com/images/ingredients/White Wine.png",
  },
];

const liqueur: object[] = [
  {
    name: "Galliano",
    nameKr: "갈리아노",
    img: "https://www.thecocktaildb.com/images/ingredients/Galliano.png",
  },
  {
    name: "Kahlua",
    nameKr: "깔루아",
    img: "https://www.thecocktaildb.com/images/ingredients/Kahlua.png",
  },
  {
    name: "Dry Vermouth",
    nameKr: "드라이 베르무트",
    img: "https://www.thecocktaildb.com/images/ingredients/Dry Vermouth.png",
  },
  {
    name: "Maraschino Liqueur",
    nameKr: "마라스키노 리큐르",
    img: "https://www.thecocktaildb.com/images/ingredients/Maraschino Liqueur.png",
  },
  {
    name: "Bitters",
    nameKr: "비터스",
    img: "https://www.thecocktaildb.com/images/ingredients/Bitters.png",
  },
  {
    name: "Apricot Brandy",
    nameKr: "살구 브랜디",
    img: "https://www.thecocktaildb.com/images/ingredients/Apricot Brandy.png",
  },
  {
    name: "Sweet and Sour",
    nameKr: "새콤달콤한 맛",
    img: "https://www.thecocktaildb.com/images/ingredients/Sweet and Sour.png",
  },
  {
    name: "Sweet Vermouth",
    nameKr: "스위트 베르무트",
    img: "https://www.thecocktaildb.com/images/ingredients/Sweet Vermouth.png",
  },
  {
    name: "Amaretto",
    nameKr: "아마레토",
    img: "https://www.thecocktaildb.com/images/ingredients/Amaretto.png",
  },
  {
    name: "Angostura Bitters",
    nameKr: "앙고스투라 비터스",
    img: "https://www.thecocktaildb.com/images/ingredients/Angostura Bitters.png",
  },
  {
    name: "Apple Brandy",
    nameKr: "애플 브랜디",
    img: "https://www.thecocktaildb.com/images/ingredients/Apple Brandy.png",
  },
  {
    name: "Elderflower cordial",
    nameKr: "엘더플라워 코디얼",
    img: "https://www.thecocktaildb.com/images/ingredients/Elderflower cordial.png",
  },
  {
    name: "Orange Bitters",
    nameKr: "오렌지 비터스",
    img: "https://www.thecocktaildb.com/images/ingredients/Orange Bitters.png",
  },
  {
    name: "Campari",
    nameKr: "캄파리",
    img: "https://www.thecocktaildb.com/images/ingredients/Campari.png",
  },
  {
    name: "Coconut Liqueur",
    nameKr: "코코넛 리큐어",
    img: "https://www.thecocktaildb.com/images/ingredients/Coconut Liqueur.png",
  },
  {
    name: "Cointreau",
    nameKr: "쿠앵트로",
    img: "https://www.thecocktaildb.com/images/ingredients/Cointreau.png",
  },
  {
    name: "Creme De Cassis",
    nameKr: "크레미 드 카시스",
    img: "https://www.thecocktaildb.com/images/ingredients/Creme De Cassis.png",
  },
  {
    name: "Creme De Cacao",
    nameKr: "크림 드 카카오",
    img: "https://www.thecocktaildb.com/images/ingredients/Creme De Cacao.png",
  },
  {
    name: "Triple Sec",
    nameKr: "트리플 섹",
    img: "https://www.thecocktaildb.com/images/ingredients/Triple Sec.png",
  },
  {
    name: "Passoa",
    nameKr: "파소아",
    img: "https://www.thecocktaildb.com/images/ingredients/Passoa.png",
  },
  {
    name: "Peach Bitters",
    nameKr: "피치 비터스",
    img: "https://www.thecocktaildb.com/images/ingredients/Peach Bitters.png",
  },
  {
    name: "Peach Schnapps",
    nameKr: "피치스냅스",
    img: "https://www.thecocktaildb.com/images/ingredients/Peach Schnapps.png",
  },
];

const materials: object[] = [
  {
    name: "Lime Juice",
    nameKr: "라임 즙",
    img: "https://www.thecocktaildb.com/images/ingredients/Lime Juice.png",
  },
  {
    name: "Lemon-lime soda",
    nameKr: "레몬 라임 소다",
    img: "https://www.thecocktaildb.com/images/ingredients/Lemon-lime soda.png",
  },
  {
    name: "Lemon Juice",
    nameKr: "레몬 즙",
    img: "https://www.thecocktaildb.com/images/ingredients/Lemon Juice.png",
  },
  {
    name: "Lemonade",
    nameKr: "레몬에이드",
    img: "https://www.thecocktaildb.com/images/ingredients/Lemonade.png",
  },
  {
    name: "Water",
    nameKr: "물",
    img: "https://www.thecocktaildb.com/images/ingredients/Water.png",
  },
  {
    name: "Soda Water",
    nameKr: "소다수",
    img: "https://www.thecocktaildb.com/images/ingredients/Soda Water.png",
  },
  {
    name: "Apple Juice",
    nameKr: "애플 주스",
    img: "https://www.thecocktaildb.com/images/ingredients/Apple Juice.png",
  },
  {
    name: "Orange Juice",
    nameKr: "오렌지 주스",
    img: "https://www.thecocktaildb.com/images/ingredients/Orange Juice.png",
  },
  {
    name: "Ginger Beer",
    nameKr: "진저 맥주",
    img: "https://www.thecocktaildb.com/images/ingredients/Ginger Beer.png",
  },
  {
    name: "Ginger Ale",
    nameKr: "진저 에일",
    img: "https://www.thecocktaildb.com/images/ingredients/Ginger Ale.png",
  },
  {
    name: "Coffee",
    nameKr: "커피",
    img: "https://www.thecocktaildb.com/images/ingredients/Coffee.png",
  },
  {
    name: "Coca-Cola",
    nameKr: "코카콜라",
    img: "https://www.thecocktaildb.com/images/ingredients/Coca-Cola.png",
  },
  {
    name: "Cranberry Juice",
    nameKr: "크랜베리 주스",
    img: "https://www.thecocktaildb.com/images/ingredients/Cranberry Juice.png",
  },
  {
    name: "Carbonated Water",
    nameKr: "탄산수",
    img: "https://www.thecocktaildb.com/images/ingredients/Carbonated Water.png",
  },
  {
    name: "Tomato Juice",
    nameKr: "토마토 주스",
    img: "https://www.thecocktaildb.com/images/ingredients/Tomato Juice.png",
  },
  {
    name: "Pineapple Juice",
    nameKr: "파인애플 주스",
    img: "https://www.thecocktaildb.com/images/ingredients/Pineapple Juice.png",
  },
  {
    name: "Passion fruit juice",
    nameKr: "패션 과일 주스",
    img: "https://www.thecocktaildb.com/images/ingredients/Passion fruit juice.png",
  },
  {
    name: "Grapefruit Juice",
    nameKr: "포도 주스",
    img: "https://www.thecocktaildb.com/images/ingredients/Grapefruit Juice.png",
  },
];

const drinks: object[] = [
  {
    name: "Cinnamon",
    nameKr: "계피",
    img: "https://www.thecocktaildb.com/images/ingredients/Cinnamon.png",
  },
  {
    name: "Powdered Sugar",
    nameKr: "고운 설탕",
    img: "https://www.thecocktaildb.com/images/ingredients/Powdered Sugar.png",
  },
  {
    name: "Grenadine",
    nameKr: "그레나딘",
    img: "https://www.thecocktaildb.com/images/ingredients/Grenadine.png",
  },
  {
    name: "Honey",
    nameKr: "꿀",
    img: "https://www.thecocktaildb.com/images/ingredients/Honey.png",
  },
  {
    name: "Strawberries",
    nameKr: "딸기",
    img: "https://www.thecocktaildb.com/images/ingredients/Strawberries.png",
  },
  {
    name: "Light Cream",
    nameKr: "라이트 크림",
    img: "https://www.thecocktaildb.com/images/ingredients/Light Cream.png",
  },
  {
    name: "Lime",
    nameKr: "라임",
    img: "https://www.thecocktaildb.com/images/ingredients/Lime.png",
  },
  {
    name: "lemon",
    nameKr: "레몬",
    img: "https://www.thecocktaildb.com/images/ingredients/lemon.png",
  },
  {
    name: "Lemon Peel",
    nameKr: "레몬 껍질",
    img: "https://www.thecocktaildb.com/images/ingredients/Lemon Peel.png",
  },
  {
    name: "Maraschino Cherry",
    nameKr: "마라스키노 체리",
    img: "https://www.thecocktaildb.com/images/ingredients/Maraschino Cherry.png",
  },
  {
    name: "Mango",
    nameKr: "망고",
    img: "https://www.thecocktaildb.com/images/ingredients/Mango.png",
  },
  {
    name: "Mint",
    nameKr: "민트",
    img: "https://www.thecocktaildb.com/images/ingredients/Mint.png",
  },
  {
    name: "Brown Sugar",
    nameKr: "브라운 슈가",
    img: "https://www.thecocktaildb.com/images/ingredients/Brown Sugar.png",
  },
  {
    name: "Blackberries",
    nameKr: "블랙베리",
    img: "https://www.thecocktaildb.com/images/ingredients/Blackberries.png",
  },
  {
    name: "Blue Curacao",
    nameKr: "블루 큐라소",
    img: "https://www.thecocktaildb.com/images/ingredients/Blue Curacao.png",
  },
  {
    name: "Blueberries",
    nameKr: "블루베리",
    img: "https://www.thecocktaildb.com/images/ingredients/Blueberries.png",
  },
  {
    name: "Apple",
    nameKr: "사과",
    img: "https://www.thecocktaildb.com/images/ingredients/Apple.png",
  },
  {
    name: "Sugar",
    nameKr: "설탕",
    img: "https://www.thecocktaildb.com/images/ingredients/Sugar.png",
  },
  {
    name: "Celery Salt",
    nameKr: "셀러리 소금",
    img: "https://www.thecocktaildb.com/images/ingredients/Celery Salt.png",
  },
  {
    name: "Sherbet",
    nameKr: "셔벗",
    img: "https://www.thecocktaildb.com/images/ingredients/Sherbet.png",
  },
  {
    name: "Salt",
    nameKr: "소금",
    img: "https://www.thecocktaildb.com/images/ingredients/Salt.png",
  },
  {
    name: "Sugar Syrup",
    nameKr: "시럽",
    img: "https://www.thecocktaildb.com/images/ingredients/Sugar Syrup.png",
  },
  {
    name: "Agave Syrup",
    nameKr: "아가베 시럽",
    img: "https://www.thecocktaildb.com/images/ingredients/Agave Syrup.png",
  },
  {
    name: "Ice",
    nameKr: "얼음",
    img: "https://www.thecocktaildb.com/images/ingredients/Ice.png",
  },
  {
    name: "Egg White",
    nameKr: "에그 화이트",
    img: "https://www.thecocktaildb.com/images/ingredients/Egg White.png",
  },
  {
    name: "Orange",
    nameKr: "오렌지",
    img: "https://www.thecocktaildb.com/images/ingredients/Orange.png",
  },
  {
    name: "Orange Peel",
    nameKr: "오렌지 껍질",
    img: "https://www.thecocktaildb.com/images/ingredients/Orange Peel.png",
  },
  {
    name: "Orgeat Syrup",
    nameKr: "오르간 시럽",
    img: "https://www.thecocktaildb.com/images/ingredients/Orgeat Syrup.png",
  },
  {
    name: "Olive",
    nameKr: "올리브",
    img: "https://www.thecocktaildb.com/images/ingredients/Olive.png",
  },
  {
    name: "Yoghurt",
    nameKr: "요거트",
    img: "https://www.thecocktaildb.com/images/ingredients/Yoghurt.png",
  },
  {
    name: "Worcestershire Sauce",
    nameKr: "우스터 소스",
    img: "https://www.thecocktaildb.com/images/ingredients/Worcestershire Sauce.png",
  },
  {
    name: "Nutmeg",
    nameKr: "육두구",
    img: "https://www.thecocktaildb.com/images/ingredients/Nutmeg.png",
  },
  {
    name: "Cloves",
    nameKr: "정향",
    img: "https://www.thecocktaildb.com/images/ingredients/Cloves.png",
  },
  {
    name: "Ginger",
    nameKr: "진저",
    img: "https://www.thecocktaildb.com/images/ingredients/Ginger.png",
  },
  {
    name: "Cherry",
    nameKr: "체리",
    img: "https://www.thecocktaildb.com/images/ingredients/Cherry.png",
  },
  {
    name: "Cream",
    nameKr: "크림",
    img: "https://www.thecocktaildb.com/images/ingredients/Cream.png",
  },
  {
    name: "Kiwi",
    nameKr: "키위",
    img: "https://www.thecocktaildb.com/images/ingredients/Kiwi.png",
  },
  {
    name: "Tabasco Sauce",
    nameKr: "타바스코 소스",
    img: "https://www.thecocktaildb.com/images/ingredients/Tabasco Sauce.png",
  },
  {
    name: "Pineapple",
    nameKr: "파인애플",
    img: "https://www.thecocktaildb.com/images/ingredients/Pineapple.png",
  },
];
export { baseAlcohol, liqueur, drinks, materials };
