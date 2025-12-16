## API 목록
### 판매자 회원가입
요청
- 메서드 : POST
- 경로: /seller/signUp
- header
```
Content-Type: application/json
```
- 본문
```
CreateSellerCommand {
    email: String,  
    username: string,
    password: string
}

```
- curl 명령 예시
```bash
curl -i -X Post 'http://localhost:8080/seller/signUp' \
-H 'Content-Type: application/json' \
-d '{
  "email": "seller1@ex.com",
  "username": "seller1",
  "password": "seller1-password"
}' 
```
성공 응답
- code: 204 No Content

정책
- 이메일 주소는 유일해야한다.
- 사용자 이름은 유일
- 사용자이름은 3자 이상의 영문자, 숫자, 하이픈, 밑줄로 구성되어야한다.
- 비밀번호는 8자 이상의 문자로 구성되어야한다.

테스트
- [x] 올바르게 요청하면 204 No Content status 코드를 반환한다.
- [x] email 속성이 지정되지 않으면 400 Bad Request 상태코드를 반환한다.
- [x] email 속성이 email 주소 형식이 아니면 400 Bad Request 상태코드를 반환한다.
- [x] username 속성이 지정되지 않으면 400 Bad Request 상태코드를 반환한다.
- [x] username 속성이 올바른 형식을 따르지 않으면 400 Bad Request 상태코드를 반환한다.
- [x] username 속성이 올바른 형식을 따르면 204 No Content 상태코드를 반환한다.
- [x] password 속성이 지정되지 않으면 400 Bad Request 상태코드를 반환한다.
- [x] password 속성이 올바른 형식을 따르지 않으면 400 Bad Request 상태코드를 반환한다.
- [x] email 속성에 이미 존재하는 이메일 주소가 지정되면 400 Bad Request 상태코드 반환
- [x] username 속성에 이미 존재하는 사용자 이름이 지정되면 400 Bad Request 상태코드를 반환한다.
- [ ] username 속성에 3자 미만의 사용자 이름이 지정되면 400 Bad Request 상태코드를 반환한다.
- [ ] password 속성에 올바르게 암호화를 지정한다.

### 현재 상태 메모
- username은 `^[a-zA-z0-9-_]{3,}$` 정규식으로 3자 미만/허용되지 않는 문자를 400으로 차단하고 있음(체크리스트는 미반영).
- Seller 엔티티에 password 컬럼이 없어 현재 암호화 저장 로직이 전혀 없음 → 컬럼 추가 후 암호화(예: BCrypt) 저장과 검증 테스트 보강 필요.
 
