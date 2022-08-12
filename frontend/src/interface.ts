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

// 홈 carousel 칵테일 정보
export interface CarouselCocktail {
  type: number;
  cocktailId: number;
  baseCocktailId: number;
  title: string;
  img: string;
  base: string;
  abv: number;
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
  postId: number;
}

// 구글 유저 정보
export interface GoogleUser {
  uid: number;
  email: string;
  name: string;
  nickname: string;
  profileImg: string;
  profileLink: string | null;
  introduce: string | null;
}
// 구글 토큰 정보
export interface GoogleToken {
  accessToken: string;
  refreshToken: string;
}

// 구글로그인 유저 상세정보
export interface GoogleUserInfo {
  userInfo: GoogleUser;
  token: GoogleToken;
}

// 콘테스트
export interface ContestCocktail {
  postId: number; // 게시물 id
  description?: string; // 설명
  imgLink: string; // 게시물 사진
  title: string; // 게시물 제목
  like?: number; // 해당 uid가 투표했는지 여부
  percentage?: string; // 투표 비율
}

// notice
export interface Notice {
  key: string; // id
  uid: number; // 발신인 uid
  from: string; // 발신인
  img: string; // 발신인 프로필
  isRead: boolean; // 읽음 여부
  postId: number; // 포스트 id
  timestamp: any; // 시간
  type: string; // 타입
}

// 급상승 검색어
export interface HotKeyword {
  keyword: string;
  hits: number;
}

// 급상승 검색어 목록
export interface HotKeywords {
  from: string;
  to: string;
  keywords: HotKeyword[];
  delta: number[];
}

// 프로필페이지 유저 정보
export interface userProfileData {
  uid: number
  email: string
  name: null
  nickname: string
  profileImg: string
  profileLink: null
  introduce: string
  followerCnt: number
  followingCnt: number
  postCnt: number
  isPublic: number
}

// 프로필 유저-게시물 정보(일반)
export interface userCommunityPostData {
  createTime: string
  nickname: string
  description: string
  postId: number
  iLike: number
  profileImg: string
  postImg: null
  likes: number
}

// 프로필 유저-게시물 정보(커칵/슈커칵)
export interface userCustomPostData {
  createTime: string
  baseCocktail: number | null
  nickname: string
  description: string
  postId: number
  iLike: number
  profileImg: string
  postImg: null
  likes: number
}

// 프로필 유저-후기 정보
export interface userPostReviewData {
  img: string
  cocktailId: number
  comment: string
  tag: string
  category: string
  nameKR: string
}

// 프로필 유저-북마크 정보
export interface userBookmarkData {
  img: string
  cocktailId: number
  tag: string
  category: string
  nameKR: string
}

