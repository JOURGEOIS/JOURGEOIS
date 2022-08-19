<br>

![icon-bold-line-nobackground](https://user-images.githubusercontent.com/26267376/185445173-385ec22d-5987-4efc-bbd4-9be4d0b2042c.png)
<br>

- [Notion](https://www.notion.so/JOURGEOIS-7e834c817aac447a93f05d1b8573c52e)
- [Figma](https://www.figma.com/file/B7asQUxDc0TBGqNyaGr0fW/%EA%B3%B5%ED%86%B5%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-JJUA?node-id=83%3A571)
- [UCC](https://youtu.be/rWbYcB-sBJ0)

<br>

# 프로젝트 **소개**

### 칵테일 정보를 얻고 나만의 칵테일을 만들어 유저들과 공유해보세요.

> 주류주아는 칵테일이 부자들의 전유물이라고 생각하는 이들을 위해 탄생했습니다. 
>
> 이제는 많은 사람들이 즐기는 칵테일을 더 쉽게, 더 많이 즐길 수 있게 하고자 합니다.

주류주아에서 얻은 칵테일 정보를 바탕으로 응용한 나만의 특별한 칵테일을 만들고, 피드를 통해 다른 유저들에게 자랑해 보세요!

<br>

# 일정 및 팀원 소개 (대전 1반 2팀)

### 📌 일정 : **2022.07.11 ~ 2022.08.19 (6주)**

<br>

### 👨‍💻 팀원 소개 : 총 6명

- 박승훈[FE] : 팀장, FE 파트장
- 고승효[FE] : 팀원
- 박재경[FE] : 팀원, 부팀장
- 전승준[BE] : 팀원, BE 파트장
- 김낙현[BE] : 팀원
- 송선아[BE] : 팀원

<br>

![hontail_team](https://raw.githubusercontent.com/JaeKP/image_repo/main/img/hontail_team.png)

<br>

# ⚡ **기술 스택**

### 1. **Frontend**

- Vue : 3.2.37
- Vue-router : 4.1.2
- Vuex : 4.0.2
- Sass : 1.53.0
- Node-sass : 7.0.1
- Ssas-loader: 7.1.0
- Typescript : 4.6.4
- Vite : 3.0.0
- Vue-tsc : 0.38.4

<br>

### 2. **Backend**

- Java : OpenJDK 11
- Spring Boot 2.7.1
- Spring Data JPA 2.7.1
- Spring Security 5.7.2
- JWT
- OAuth 2.0

<br>

### 3. **DB**

- MariaDB 10.8
- Redis
- Cloud Firestore

<br>

### 4. **CI/CD**

- AWS EC2
- Docker
- Jenkins
- NGINX
- SSL

<br>

### 5. IDE

- IntelliJ IDEA 2022.1.3
- Vscode 1.70.0

<br>

# 🏗️ **아키텍쳐**

![hontail_architecture](https://user-images.githubusercontent.com/77388214/185464327-9c95b84e-3bd9-42e9-aa0d-deed2521f13b.png)

<br>

# 🎨 디자인 시스템

![Untitled](https://user-images.githubusercontent.com/77388214/185464332-18170e6f-1416-4890-a747-22a9efb924ed.png)

<br>

# 🔖 **주요 기능**

### **1. 칵테일 정보**

<br>

![Frame 219](https://raw.githubusercontent.com/JaeKP/image_repo/main/img/Frame%20219.png)

<br>

- `정보`: 다양한 칵테일에 대한 정보 확인 (재료, 도수, 레시피 등)
- `후기`: 유저들의 칵테일에 대한 솔직한 후기
- `북마크`: 북마크를 통한 칵테일 기록

<br>

![Frame 220](https://raw.githubusercontent.com/JaeKP/image_repo/main/img/Frame%20220.png)

<br>

- `검색`: 칵테일 이름, 재료 키워드로 검색 & 자동완성을 기능을 통해 편리하게 검색 가능
- `필터`: 도수, 재료 필터를 통한 맞춤 칵테일 검색

<br>

### 2. 게시글

<br>

![Frame 221](https://raw.githubusercontent.com/JaeKP/image_repo/main/img/Frame%20221.png)

<br>

- **`커스텀 칵테일`: 나만의 칵테일을 만들어 재료, 레시피를 유저들과 공유**
- `일반 게시글`: 칵테일 바 후기, 일상 이야기 등 일반 게시글도 업로드 가능

<br>

![Frame 218](https://raw.githubusercontent.com/JaeKP/image_repo/main/img/Frame%20218.png)

<br>

- `댓글과 좋아요`: 댓글과 좋아요를 통한 유저들 간의 소통
- `공유하기`: 페이스북, 트위터, 카카오톡 공유하기 기능을 활용한 게시글 공유

<br>

### **3. 뉴스피드**

<br>

![Frame 222](https://raw.githubusercontent.com/JaeKP/image_repo/main/img/Frame%20222.png)

<br>

- `팔로우, 팔로잉`: 유저를 팔로우하여, 팔로우한 유저들의 게시글을 일괄적으로 확인

<br>

### **4. 칵테일 추천**

<br>

![Frame 206](https://raw.githubusercontent.com/JaeKP/image_repo/main/img/Frame%20206.png)<br>

- `상황별 추천`: 친구들과 함께, 연인과 함께 등 상황별 어울리는 칵테일 추천
- `취향 분석`: 취향 분석을 통한 비슷한 칵테일 추천
- `게시글 추천`: 유저들이 만든 칵테일 게시물 중 인기 있는 게시물 추천
- `영상 추천`: 칵테일 관련 영상 랜덤 추천
- `인기 검색어`: 지난 한 시간 동안의 인기 검색어를 통한 트랜드 체크

<br>

### 5. 채팅 & 알림

![Frame 213](https://raw.githubusercontent.com/JaeKP/image_repo/main/img/Frame%20213.png)

- `실시간 채팅`: 1:1 채팅을 통한 실시간 소통
- `알림`: 다른 유저가 나를 팔로우하거나, 내가 작성한 게시글에 반응하면 생성되는 알림
- `알림 명세`: 알림 상세 명세를 클릭하여, 어디서 알림이 발생했는지 확인 가능

<br>

### 6. 칵테일 어워즈

<br>

![Frame 225](https://raw.githubusercontent.com/JaeKP/image_repo/main/img/Frame%20225.png)

<br>

- `참여`: 일정 기간동안 열리는 칵테일 관련 어워즈에 참가자로서 참여 가능
- `투표`: 참가작들을 한 눈에 보고 마음에 드는 칵테일에 투표

<br>

### **7. 프로필**

<br>

![Frame 226](https://raw.githubusercontent.com/JaeKP/image_repo/main/img/Frame%20226.png)

<br>

- `채팅`: 채팅을 통한 즉각적인 소통
- `프로필 공개 설정`: 다른 유저에게 프로필 노출 여부 선택
- `기록 관리`: 게시글, 후기, 북마크한 칵테일 등 사이트에서 활동한 내용을 관리

<br>

# 📖 문서

| Docs               | Link                                                         |
| ------------------ | ------------------------------------------------------------ |
| **Wiki**           | **[링크](https://github.com/piacu/jourgeois-repo/wiki)**     |
| FE 요구사항 정의서 | [링크](https://docs.google.com/spreadsheets/d/1zRahvEscoiUu_VIQf6rIBG2hMWhvO97oRn21FOwlSLQ/edit?usp=sharing) |
| BE 요구사항 정의서 | [링크](https://docs.google.com/spreadsheets/d/1pf_7axAoF9G9fQhXGwSEn_ehM93N6FQ2jPbWGZi1NVA/edit?usp=sharing) |
| QA                 | [링크](https://docs.google.com/spreadsheets/d/1w5iTH2fNnUKf4BgKUhDzwl-UsSLsurpaS8ZLt6HL7PA/edit?usp=sharing) |
| WBS                | [링크](https://docs.google.com/spreadsheets/d/1gVU32Xnl-toC6h20yMdjTmaKZh2UeZDDjoCkMPYtOb0/edit?usp=sharing) |

<br>