// 커스텀 칵테일만의 정보
export interface CustomCocktailOnly {
  postId: number; // 포스트 uid
  imgLink: string; // 칵테일 이미지
  description: string; // 칵테일 설명
  createTime: number[]; // 작성시간
  lastUpdateTime: number[]; // 수정 시간
  isUpdated: number; // 수정되었는지 여부[0,1]
  like: number; // 칵테일 좋아요 개수
  title: string; // 칵테일 이름
  baseCocktail: number; // 베이스 칵테일 번호
  baseCocktailName: string; // 베이스 칵테일 이름
  ingredients: string[]; // 칵테일 설명
  recipe: string; // 칵테일 제작
}

// 커스텀 칵테일 작성자
export interface CustomCocktailHost {
  uid: number; // 작성자 uid
  nickname: string; // 작성자 닉네임
  profileImg: string; // 작성자 프로필 이미지
  isFollowed: number; // 본인과 팔로우 여부 [0,1,-1] 0: 팔로우 x, 1: 팔로우, -1: 본인
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
  followerDTO: CustomCocktailHost;
}
