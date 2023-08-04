
- 성명
  정진성
- 실행방법
    ```
    sudo su
    git clone https://github.com/jinseong205/wanted-pre-onboarding-backend.git
    cd wanted-pre-onboarding-backend/
    [docker demon 실행]
    docker system prune -a      
    docker-compose up -d
    ```
- 호출 방법
  ex) localhost:8080/api/boards

실행 명령어
docker-compose up -d
- 추가 기능
  - 테스트 코드 추가
  - docker compose를 이용하여 환경 구성
  - 클라우드 환경 배포 완료 - http://프리온보딩.웹.한국:8080/

- 데이터베이스 테이블 구조
  ```
  user - id, username, password, roles, crt_id, updt_id, crt_dt, updt_dt
  board - id, title, content, crt_id, updt_id, crt_dt, updt_dt
  ```
- API 데모영상 링크
  -
- API 명세
  [POST] /api/join - 회원가입
    ```
    (req) body - username , password
    (res) body - message
    ```
  [POST] /api/login - 로그인
    ```
    (req) body - username , password
    (res) header - Authorization : token
          body - message
    ```
  [POST] /api/board - 게시물 생성
    ```
    (req) header - Authorization : token
          body - title , content
    (res) body - message
    ```
  [GET] /api/boards?page={page} - 게시물 페이징 조회
     ```
    (req) param - page
    (res) body - pageable -> content (= List<board>)
     ```
  [GET] /api/board/{id} - 게시물 단일 조회
    ```
    (req) param - id
    (res) body - board
    ```
  [PUT] /api/board/{id} - 게시물 수정
    ```
    (req) header - Authorization : token
          param - id
          body - title , content
    (res) body - message
    ```
  [DELETE] /api/board/{id} - 게시물 삭제
    ```
    (req) header - Authorization : token
          param - id
    (res) body - message
    ```
