# V2P Based Smombie Recognition Backend

![Generic badge](https://img.shields.io/badge/version-1.0.1-brightgreen.svg)
<img src="https://img.shields.io/badge/java-007396?style=flat-square&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/>
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
<img src="https://img.shields.io/badge/Firebase-FFCA28?style=flat-square&logo=firebase&logoColor=black"/>

> This repository is part of the project [V2P-Based-Smombie-Recognition-Alarm-Application](https://github.com/Capston-Design-Team1)

# Getting Started(Local)

![image](https://github.com/Capston-Design-Team1/V2P-Based-Smombie-Recognition-Backend/assets/80497144/e1b14d82-0d78-4e45-974f-8bac5490631a)
## Docker를 이용한 로컬 환경 구성  
  해당 프로젝트의 개발 환경의 일관성과 배포를 용이하게 해주기 위해 **Docker** 를 사용한다.  
  1. Spring Boot 서버 이미지 만들기 **(생략 가능)**
      **Gradle Clean 후 Gradle BootJar 빌드를 통해 프로젝트 Jar 파일 생성 후 진행**  
      ```  
      docker build -t v2psmombie-server-image .  
      ```
      ⚠️ 프로젝트의 docker-compose.yml 파일 수정(현재 DockerHub에 배포된 이미지(fight0105/v2psmombie-image) 사용 중)      
  2.  Docker-Compose를 통한 Spring Boot 서버 및 Mysql 컨테이너화
      ```
      docker-compose up -d
      ```
  3. 실행
     ```
     docker-compose start
     ```  
      * *컨테이너의 실행 종료*
        ```
        docker-compose stop
        ```
      * *생성한 컨테이너 삭제 및 초기화*
        ```
        docker-compose down
        ```
        + mysql 폴더 내의 db 폴더 내부를 삭제해야 완전 초기화 가능(⚠️ 숨김 파일 포함 전체 삭제 요망 &rarr; db 폴더 삭제 후 재생성 추천 ) 
