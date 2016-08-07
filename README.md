# Simple Timeline

팔로우, 포스팅, 뉴스피드 정도만 간단히 구현한 프로젝트 입니다.

standalone 으로 구동하기 위해서 spring boot 기반으로 구현했으며 front-end 라이브러리는 webjar를 이용 하였습니다.



## Requirements

- JDK 1.7 이상
- Maven 3.2.2 이상
- Mysql 5.6 이상
``

## Getting started

### Build

```
$ cd timeline
$ mvn clean package
```

### Run

```
$ cd target
$ java -jar timeline-0.0.1.jar
```
- Mysql 기본 계정 설정
    - dbname: timeline
    - username: timeline
    - password: timeline
- 디비이름 및 유저 생성만 해주면 테스트용 데이터는 생성됨

## API

### timeline

- /api/timeline: 타임라인 가져오기
    - method: get
    - parameter:
        - userId: 유저 인덱스 번호
        - start: 타임라인 리스트 시작점
        - range: 타임라인 리스트 가져오는 크기
- /api/timeline/post: 타임라인 작성 (로그인 인증 세션 필요)
    - method: post
    - parameter:
        - timelindText: 작성할 내용
- /api/timeline/delete: 타임라인 삭제 (로그인 인증 세션 필요)
    - method: delete
    - parameter:
        - id: 타임라인 인덱스 번호

### user

- /api/user/countOfUsers: 유저 전체 카운트
    - mehtod: get
- /api/user/{userName}: 유저 생성
    - method: put
    - pathVariable:
        - userName: 유저 이름(비번도 동일하게 생성)
- /api/user/listAll: 유저 전체 리스트
    - method: get
- /api/user/folower: 유저 팔로워 리스트 가져오기
    - method: get
    - parameter:
        - userId: 유저 인덱스 번호
- /api/user/folowing: 특정 유저 팔로잉 하기 (로그인 인증 세션 필요)
    - method: put
    - parameter:
        - userId: 팔로잉 대상 유저 인덱스 번호
- /api/user/unFollowing: 특정 유저 언팔로잉 하기 (로그인 인증 세션 필요)
    - method: delete
    - parameter:
        - userId: 언팔로잉 대상 유저 인덱스 번호
