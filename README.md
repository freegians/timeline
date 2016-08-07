Simple Timeline 프로젝트 입니다


## 필요사항

- JDK 1.7 이상
- Maven 3.2.2 이상

## 시작

### 빌드

```
$ cd timeline
$ mvn clean package
$ cd target
$ java -jar timeline.jar
```

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
-