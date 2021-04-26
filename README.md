# NonoGrams-Android

***

## 프로젝트 구성 & 사용 언어
- 안드로이드 스튜디오 (Android Studio)
- JAVA

***

## 프로젝트 소개

- Nonograms(네모네모로직) 게임을 구현하는 프로젝트 입니다.
- 해당 프로젝트는 안드로이드 스튜디오에서 작성하였고, Minimum sdk 는 API 16입니다.
- 게임에 사용할 이미지 검색을 위해 [__네이버 이미지 검색 API__](https://developers.naver.com/docs/search/image/) 를 사용하였습니다.


***

## 실행화면 보기

- Pixel 3a API 29 기기에서 테스트되었습니다.

### 1. 초기 화면

![image](https://user-images.githubusercontent.com/71871348/116051249-575cfa00-a6b3-11eb-9c71-b3dc98382ffc.png)


### 2. 보드판 생성 (검색)

![image](https://user-images.githubusercontent.com/71871348/116056671-0223e700-a6b9-11eb-991a-a5392507c745.png)
- __cat__ 을 입력하고 __SEARCH__ 버튼을 클릭하면, 검색한 이미지를 정사각형 형태로 크기를 조절하고 흑백 이미지로 변경한다.

![image](https://user-images.githubusercontent.com/71871348/116051560-b15dbf80-a6b3-11eb-92cc-70bd6ddc1f46.png)
- 변경한 이미지를 이용해서 20×20 짜리 Nono Board를 생성한다.

### 3. Click nonogram item

![nono1](https://user-images.githubusercontent.com/71871348/116052756-e61e4680-a6b4-11eb-9ceb-3519e974273e.gif)

- 보드의 규칙에 맞게 BLACK으로 표시될 올바른 부분을 클릭하면, 클릭한 부분이 검은색으로 바뀐다.

![nono2](https://user-images.githubusercontent.com/71871348/116052876-09e18c80-a6b5-11eb-8103-c76c71966f39.gif)

- WHITE로 표시될 부분을 클릭하면, WRONG이라는 Toast 메시지가 뜨고 보드판이 초기화된다.

### 4. Complete puzzle

![image](https://user-images.githubusercontent.com/71871348/116053562-cb000680-a6b5-11eb-8cc7-61a648eb53ee.png)

- 모든 BLACK 부분을 맞추면, FINISH! 메시지가 뜬다.

***

## Check

- 이미지 검색을 통해 사용되는 이미지는 검색 결과 가장 첫번째에 나오는 이미지로 고정되어 있다.
- 이미지의 원본 크기에 상관없이 고정된 정사각형 크기로 이미지를 축소/확대 시킨다.








