# 프로젝트 소개

해당 프로젝트는 아래의 5가지 기능을 구현하였다.

1. MariaDB에 기반한 회원 정보 관리 및 로그인 기능 구현
2. JWT를 활용하여 소셜 로그인 기능 구현
3. Naver Clova OCR를 활용한 Image-to-Text 기능 구현
4. ChatGPT를 활용한 API 통신 및 내용 요약 기능을 제공하는 챗봇 기능 구현
5. 코드 네이밍 문법 변환 기능

# 기능 소개

## 🔑 1. MariaDB에 기반한 회원 정보 관리 및 로그인 기능 구현 & 2. JWT를 활용하여 소셜 로그인 기능 구현

로그인은 일상생활에서 익숙한 기능이다. Spring Security는 이러한 기능을 구현에 도움을 주었다.

간단하게, 회원가입 요청이 들어온다면 AuthenticationFilter를 통과하여 UsernamePasswordAuthenticationToken을 만들어 UserDetails객체를 만들어 SecurityContext에 저장하게 된다.

로그인을 할 때는 SecurityContext에서 조회하여 로그인을 수행한다.

각 코드의 자세한 설명은 아래의 Velog에 시리즈로 정리하였다.

[로그인 기능 구현 소개 정리](https://velog.io/@gwj0421/series/OAuth2-Login)

## 🍀 3. Naver Clova OCR를 활용한 Image-to-Text 기능 구현 & ChatGPT를 활용한 API 통신 및 내용 요약 기능을 제공하는 챗봇 기능 구현

대학 서적 및 논문을 공부하는 과정에서 관련 내용을 학습하는 과정에서 해당 내용이 생소하여 어려움을 겪었다.

서론을 통해 전달하고자 하는 내용 파악에 부족함을 느껴 내용을 요약하는 기능 구현을 목표로 프로젝트를 진행하였다.

우선, 방대한 서적의 내용을 이미지를 통해 텍스트로 추출하기 위해 네이버에서 제공하는 Clova OCR 기능을 사용하였다.

ChatGPT를 활용하여 내용 요약을 목적으로 하는 챗봇을 코딩하여 앞서 추출한 텍스트를 ChatGPT모델을 통해 내용을 요약하는 기능을 구현하였다.

특히, Clova OCR과 ChatGPT 기능을 Webflux를 활용하여 비동기방식으로 구현하였다.

## 📲 5. 코드 네이밍 문법 변환 기능

Snake 혹은 Camel과 같은 여러 케이스가 존재한다.

프로젝트 이름이나 데이터베이스 이름에도 보편적으로 각기 다른 케이스를 사용한다.

해당 기능은 사용자가 입력한 문자열이 어떤 케이스이고 다른 케이스로 변경할 경우 어떻게 작성해야 하는지 알려주는 기능을 구현하였다.

해당 기능 구현의 목적은 물론 네이밍 변환에도 있지만 여러 케이스로 인한 변수 처리를 하기 위해 테스트 코드 작성 연습을 목적으로 두고 진행하였다.

테스트 코드를 세분하지 않고 본 코드와 같이 커밋하여 커밋 트리가 아쉽지만 테스트 코드를 작성할 때, 최대한 여러 변수를 커버하도록 하였다.

그래서 디버그 과정에서 코드를 픽스할 수 있었다.

추후에 해당 함수 혹은 클래스의 기능을 입력하면 LLM모델과 연계하여 이름과 연계하여 보편적인 네이밍 문법에 맞는 이름 추천 기능을 추가할 것이다.