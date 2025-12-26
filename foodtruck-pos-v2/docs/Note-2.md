### cart record 아닌 class인 이유
- cart는 상태가 변한다.
```
Entity (도메인 객체)
- 비즈니스 로직 (주문 취소, 금액 계산, 상태 검증 등)
- 데이터 무결성 보장
- 연관관계 관리
DTO
- API 요청/응답 데이터 전달
- Entity ↔ DTO 변환
- Validation 규칙 (도메인이 아닌 입력 검증)
Service
- 트랜잭션 관리
- 여러 Entity 조합
- Entity의 도메인 메서드 호출
```
```
Controller(UI) Layer: DTO 사용 //API 스펙 독립, 민감정보 보호, 유연성
↓ DTO 전달
Service Layer: DTO ↔ Entity 변환, 도메인 로직 실행 //계층 분리, 비즈니스 규칙
↓ Entity 전달
Repository Layer: Entity 사용 //영속성 관리
- UI(API)와의 통신에 DTO 사용
- Entity는 내부(Service, Repository)에서만 사용
- DTO로 도메인 객체를 보호하고 계층을 분리
```
```
Aggregate Root만 Repository/Service/Controller를 가짐
Aggregate 내부 Entity는 Root를 통해서만 접근
트랜잭션 경계 = Aggregate 경계
각 Aggregate Root는 독립적으로 관리
```