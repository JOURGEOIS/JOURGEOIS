// 검색 유저 정보
export interface User {
  uid: number;
  nickname: string;
  profileImg: string;
  introduce: string | null;
  isFollowed: number | null; // 1 : 팔로우중 / 0 : 팔로우X / -1 : me
}

// 커스텀 칵테일만의 정보
export interface CustomCocktailOnly {
  uid: number | null; // 유저 id
  img: string | null; //
  postId: number; // 포스트 uid
  imgLink: string; // 칵테일 이미지
  description: string; // 칵테일 설명
  createTime: number[]; // 작성시간
  lastUpdateTime: number[]; // 수정 시간
  isUpdated: number; // 수정되었는지 여부[0,1]
  like: number; // 칵테일 좋아요 개수
  ilike: boolean; // 내가 좋아요 했는지 여부 [T/F]
  title: string; // 칵테일 이름
  baseCocktail: number; // 베이스 칵테일 번호
  baseCocktailName: string; // 베이스 칵테일 이름
  ingredients: string[]; // 칵테일 설명
  recipe: string; // 칵테일 제작
  type: string | null; // 칵테일인지 커스텀칵테일인지
  reviewCount: number; // 댓글 개수
}

// 댓글 작성자
export interface Comment {
  createTime: number[]; // 댓글 작성 시간
  updateTime: number[]; // 댓글 수정 시간
  isUpdated: number; // 댓글 수정이 되었는지 아닌지 [0,1]
  review: string; // 댓글 내용
  uid: number; // 댓글 작성자 uid
  nickname: string; // 댓글 작성자 닉네임
  profileImg: string; // 댓글 작성자 이미지
  reviewCount: number; // 댓글 갯수
  isMine: number; // 댓글 본인이 썼는지 안 썻는지 [0,1]
}

// 커스텀칵테일 상세정보
export interface CustomCocktail {
  customCocktail: CustomCocktailOnly;
  followerDTO: User;
}

export interface NewsFeed {
  createTime: number[]; // 생성 시간
  updateTime: number[]; // 수정 시간
  type: string; // 타입
  writer: number; // 작성자
  nickname: string; // 닉네임
  profileImg: string; // 프로필 이미지
  postImg: string; // 게시한 사진
  description: string; // 글 내용
  reviewCount: number; // 댓글 수
  likeCount: number; // 좋아요 수
  baseCocktailId: number | null; // 베이스 칵테일
  baseCocktailName: string | null; // 베이스 칵테일 이름
  isLiked: number; // 좋아요
  isSuperCustomCocktail: number;
  cocktailTitle: string;
}
