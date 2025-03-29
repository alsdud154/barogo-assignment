# api 문서
* 스웨거 : http://localhost:8080/swagger-ui/index.html
---
# api 테스트 
* *.http 활용
  * resources/http/account.http
  * resources/http/delivery.http
---
# 레이어드 아키텍처
* presentation -> application -> domain <- infrastructure
* domain 영역에서 예외로 JPA Entity 기술 사용
* domain에 Domain Repository 인터페이스를 만들고, infrastructure에서 Jpa Repository 생성
  * Jpa Repository는 Domain Repository를 상속받고, Repository를 사용하는 application 레이어는 DIP 준수
---
# 테스트 픽스처
* 네이버 오픈소스 Fixture Monkey 활용
---
# 코드 포맷터
* Spotless 활용
---
# 테스트 코드
* 컨트롤러, 서비스(비지니스) 단위 테스트 작성