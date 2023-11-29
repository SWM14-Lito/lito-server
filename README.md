<div align="center">
  <img src="https://github.com/SWM14-Lito/lito-server/assets/88089316/1659c2ba-07a0-4c74-ba4f-40f60f2291a2">
</div>

&nbsp;
> IT 기술면접 대비 CS 학습 서비스

&nbsp;
## 📝 Introduce
- 개발직군 면접을 준비하는데 필요한 CS 학습을 도와주는 서비스 입니다.
- 핵심 키워드를 중심으로 답변 연습을 해야하는 면접자의 학습시간을 줄여주고, 답변을 더 잘 할 수 있도록 도와줍니다.

<div align="center">
  <img width="1200" alt="learningit-screen" src="https://github.com/SWM14-Lito/lito-server/assets/88089316/3ed85cb2-092a-4c3b-957e-ef65a3fa775e">
</div>

&nbsp;
&nbsp;

## ⚒  Tech Stack
- Java 17
- Spring Boot 3.1.1, Spring Data JPA, Spring Batch, Spring Rest Docs, QueryDSL
- H2, Mysql, Redis, MongoDB
- Amazon ECS, RDS, Elastic Cache, DocumentDB, S3, ELB, Route53, OpenSearch, CloudWatch, Lambda, SQS
- Github Actions, Docker, Jacoco, jMeter, Junit 5

&nbsp;
&nbsp;

## 🏢 Architecture
<div align="center">
  <img width="1162" alt="learningit-architecture" src="https://github.com/SWM14-Lito/lito-server/assets/88089316/56ec8297-58a6-4e2d-b11a-f476bbc131ad">
</div>

## 🕋 Multi-Module
<div align="center">
  <img width="1204" alt="multi-module" src="https://github.com/SWM14-Lito/lito-server/assets/88089316/9e7e6274-a70d-41dc-9916-92e542a34cf9">
</div>

### [api](https://github.com/SWM14-Lito/lito-server/tree/develop/api)
- 사용자 웹 모듈
- 사용자가 사용해야하는 api 정의

### [admin](https://github.com/SWM14-Lito/lito-server/tree/develop/admin)
- 관리자 웹 모듈
- 관리자가 사용해야하는 api 정의

### [batch](https://github.com/SWM14-Lito/lito-server/tree/develop/batch)
- 배치 작업 모듈

### [core](https://github.com/SWM14-Lito/lito-server/tree/develop/core)
- 비즈니스, 도메인 모듈
- 시스템 비즈니스 로직, 도메인 정의

### [support](https://github.com/SWM14-Lito/lito-server/tree/develop/support)
- 공통 모듈
- 웹 모듈이 공통으로 사용하는 로깅 정의
