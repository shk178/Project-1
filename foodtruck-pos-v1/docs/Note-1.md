# 손님 모드
## 메뉴 선택 화면에서 동일한 메뉴 표시 (동적 추가x)
- 정적인 테스트 데이터를 가져오는 방법
  - 테스트 데이터를 프로퍼티에 저장해서 읽어 오는 방법
  - 테스트 데이터를 DB에 미리 저장해서 불러오는 방법
  - 메뉴 종류가 변경될 수 있으므로 DB 관리를 추천
## 결제 정보를 DB에 저장
- 결제용 키는 카드 회사에서 발급해준다.
## 주문 대기 번호
- 1부터 시작, 날짜가 바뀌면 1부터 다시 시작
---
# 관리자 모드
## 주문 관리 메뉴
- 인메모리 DB 사용이라서 관리자 모드에서 손님 모드 데이터가 안 보인다.
- 테스트 데이터를 사용하거나 손님 모드 데이터를 MySQL에 저장해서 불러온다.
- 주문 상태: 주문 접수, 주문 처리 완료
- 결제 상태: 결제 승인 성공
## 매출 관리 메뉴
- 월별 매출 현황, 당해년도
## 도메인 규칙
- 동일한 주문 상태로 변경할 수 없어야 한다.
- 주문 번호는 날짜/시간을 포함한 24자리의 문자열로 구성한다.
- 59분 59초까지
---
# JDBC
## java->jdbc api->jdbc 벤더별 드라이버->db
## jdbc api 처리 흐름
- 드라이버 인터페이스의 구현 클래스 로딩
- connection 인터페이스의 구현 클래스 객체 생성
- statement 인터페이스의 구현 클래스 객체 생성
- query 실행

```java
import java.sql.DriverManager;
import java.sql.PreparedStatement;

class className {
    MessageJdbc methodName() {
        try {
            String.DRIVER_NAME = "org.h2.Driver";
            Class.forName(DRIVER_NAME);
            Connection connection = DriverManager.getConnection(URL, username, password);
            String sql = "INSERT INTO MESSAGE (message) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, message.getMessage());
            statement.executeUpdate();
            return new MessageJdbc(null, getMessage());
        } catch (SQLException e) {
            log.error("SQLException happened: ", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
```

## Spring 프레임워크의 데이터 액세스 접근 방식
- Data Access with JDBC: JdbcTemplate, SimpleJdbcInsert, SqlUpdate, JdbcClient 등
- Object Relational Mapping(ORM) Data Access: JPA
- https://docs.spring.io/spring-framework/reference/data-access/jdbc/core.html
- JdbcTemplate, JdbcClient 코드 차이를 본다: JdbcClient가 모던 자바 스타일을(메서드 체이닝, 람다 표현식) 추구한다.

```java
#v1 추가
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    hibernate:
      ddl-auto: create
    show-sql: true
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
runtimeOnly 'com.h2database:h2'
```

## 샘플 프로젝트
```sql
CREATE TABLE IF NOT EXISTS MESSAGES (
    MESSAGE_ID bigint NOT NULL AUTO_INCREMENT,
    MESSAGE varchar(100) NOT NULL,
    PRIMARY KEY (MESSAGE_ID)
);
```
```java
@Service
public class MessageService {
    public final MessageRepository messageRepository;
    //생성자
}
```
```java
@Repository
public class MessageRepository {
    private final JdbcClient jdbcClient;
    //생성자
    public long save(String message) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("insert into MESSAGES (MESSAGE) values (:message)")
                .param("message", message)
                .update(keyHolder, "MESSAGE_ID");
        return keyHolder.getKeyAs(Long.class);
    }
    public Message findById(long messageId) {
        return jdbcClient.sql("select * from MESSAGES where MESSAGE_ID=:messageId")
                .param("messageId", messageId)
                .query(Message.class)
                .single();
    }
}
```
```java
@Component
public class MessageHandler {
    private final MessageService messageService;
    //생성자
    public long registerMessage(String message) {
        return messageService.createMessage(message);
    }
    public Message getMessage(long messageId) {
        return messageService.findById(messageId);
    }
}
```

```java
@Slf4j
@Configuration
public class HelloJdbcRunner {
    private final MessageHandler messageHandler;
    //생성자
    @Bean
    public ApplicationRunner run() {
        return args -> {
            String message = "Hello JdbcClient";
            long messageId = messageHandler.registerMessage(message);
            Message foundMessage = messageHandler.getMessage(messageId);
            log.info("# response: {}({})", foundMessage.getMessage(), foundMessage.getMessageId());
        };
    }
}
```
```java
@Getter
public class Message {
    private long messageId;
    private String message;
}
```

---
## 인텔리제이 H2 확인

```java
@Slf4j
@Configuration
public class H2ServerConfig {
    @PostConstruct
    public void init() {
        // 인텔리제이 설정 시 FULL ULR: jdbc:h2:tcp://localhost:9092/mem:testdb
        System.setProperty("spring.datasource.url", "jdbc:h2:mem:testdb");
        System.setProperty("spring.datasource.driver-class-name", "org.h2.Driver");
        System.setProperty("spring.datasource.username", "sa");
        System.setProperty("spring.datasource.password", "");
    }
    @Bean
    public CommandLineRunner startH2Server() {
        log.info("# h2 server started");
        return args -> Server
                .createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092")
                .start();
    }
}
```

- 인텔리제이에서 datasource에 h2 추가해서 (mem, 주소 등 입력해서) 연결해서 확인
- Config 또는 yml에서 datasource를 하면 되는데 yml에 설정 property 입력할 경우 Server.createTcpServer 이 부분 코드로 실행해야 한다. (서버 모드로 실행해서 확인하려면)
- DB 직접 확인 보다는 단위 테스트로 코드 동작을 확인하는 방법을 추천
---
# 애플리케이션 설계
## 도메인별 패키지 구성
- 주문 시스템과 관련된 도메인
```
회원, 구매자, 판매자, 주문, 상품
, 배송, 상품즐겨찾기, 포인트, 장바구니, 상품 카테고리
, 구매내역, 구매후기, 결제, 공지사항, 반품/교환
, 판매내역, Q&A
```
- 구매자, 판매자, 주문, 상품, 장바구니, 결제
## 도메인 클래스 관계
- 메뉴와 메뉴아이템: 하나의 메뉴에 여러 메뉴 아이템 (1:N)
- 데이터베이스 테이블 스키마에서, 테이블과 테이블 간의 관계는 1:N일 경우에 1에 해당하는 기본키를 N에 해당하는 테이블에서 외래키 형태로 가진다.
- 자바 같은 객체지향에서는 1:N일 경우에 1에 해당하는 객체라 N에 해당하는 객체를 가진다고 표현한다. (컬렉션-리스트)
- 카트와 카트아이템도 1:N이다. 카트아이템은 데이터만 표현하고 비즈니스 로직을 직접 처리하지 않아 record로 구현했다.
- totalAmount: 주문 총 금액
- OrderNo나 Money처럼 구체적인 의미를 담는 객체를 값 객체라고(Value Object) 한다.
- Money price: 주문 음식 가격
- int quantity: 주문 음식 수량
- Money amount: 가격*수량 (주문 금액)
- Order:OrderItem=1:N
- OrderItem:MenuItem=N:1
- Order:MenuItem=N:N
- 오더와 오더 아이템은 강한 결합: 주문이 있으면 주문한 아이템도 있다.
- 메뉴 아이템은 음식을 표현하는 클래스: 오더 아이템과 1:N의 관계이기는 하지만 서로 독립적으로 움직인다.
- 메뉴 아이템의 식별자 역할을 하는 멤버 변수(menuItemId)를 오더 아이템의 필드로 한다. 데이터베이스 테이블 관점에서 1에 해당하는 테이블의 기본 키를 n에 해당되는 테이블의 외래키 형태로 포함할 수 있는 구조가 만들어진다.
- 즉, N:N의 관계일 경우에 중간에 1:N, N:1을 위치시켜서 강하게 결합 시 리스트로, 독립적 관계 시 식별자를 외래키로 하도록 설계한다. (DDD 설계 기법)
- 오더:결제=대체로 1:1 // 식별자의 역할을 하는 멤버 변수를 필드로 한다. 이때 주 테이블의 역할을 하는 쪽의 식별자를 (지금은 오더의 orderNo) 다른 테이블의 외래키로 하도록 한다.
---
## dbdiagram.io
### PAYMENT
- PAYMENT_ID(PK) bigint
- PAYMENT_KEY varchar(50)
- AMOUNT int
- PAYMENT_STATE varchar(50)
- ORDER_NO(FK) char(24)
### ORDERS
- ORDER_NO(PK) char(24)
- TOTAL_AMOUNT int
- ORDER_STATE varchar(50)
- ORDER_DATE datetime
- WAITING_NO int
### ORDER_ITEM
- ORDER_ITEM_ID(PK) bigint
- PRICE int
- QUANTITY smallint
- AMOUNT int
- ORDER_NO(FK) char(24)
- MENU_ITEM_NO(FK) bigint
### MENU_ITEM
- MENU_ITEM_ID(PK) int
- MENU_ITEM_NAME varchar(50)
- PRICE int
- MENU_ITEM_STATE varchar(50)
- MENU_ID(FK) bigint
### MENU
- MENU_ID(PK) int
- MENU_NAME varchar(50)
- MENU_TYPE varchar(20)
- MENU_STATE varchar(50)
- 결제:주문=1:1(1:0)
- 주문:주문아이템=1:N(0:N)
- 주문아이템:메뉴아이템=N:1(N:0)
- 메뉴아이템:메뉴=N:1(N:0)
- 인텔리제이 데이터베이스가 안 된다. (버전)
---
## public Menu getMenuBy(Menu.MenuType menuType)
- 하나의 메뉴 타입에 해당하는 음식 메뉴 목록으로만 메뉴를 구성하도록 했다.
- 메뉴, 디저트 메뉴 처럼 다중 메뉴 타입으로는 구현을 하지 않았다. (난이도)
## interface 도메인 폴더에 위치
- 도메인 위주 개발 방식과 관련이 있다. DIP 원칙과 관련이 있다.
- 인프라스트럭처 레이어에는 구체 기능 구현 클래스만 위치, 도메인 레이어에 덜 구체적인 인터페이스 위치
## cart 서비스 여러 개 만드는 이유
- 도메인 위주 개발 방식에서 서비스 클래스를 기능 단위로 나눈다.
- 코드가 심플해진다. 필드가 줄어든다.
## public CartResult getCartBy(int cartId)
- Cart가 아닌 dto를 리턴하는 이유: Cart 안에 cartItemList + 도메인 기능 메서드들이 있는데 리턴할 때 dto를 리턴해서 메서드 사용을 제한할 수 있다.
## CQRS (Command Query Resposibility Segregation) 패턴
- order.다른 패키지들: 주문이라는 데이터를 db에 추가하고 주문의 상태를 변경하는 작업
- order.query 패키지: db에서 주문 데이터 조회하는 작업
- db 변경과 조회 작업을 나눠서 구현하는 게 CQRS다.
## port 폴더
- 데이터 조회 로직은 도메인 로직을 포함하지 않는 경우가 많다.
- infrastructure 폴더에 repository 구현 클래스들이 있는데, 인터페이스의 위치를 dto 대신 port에 위치시킨다.
- 인터넷에서 애플리케이션 통신에는 IP 주소와 포트 번호가 필요하다. (주소 역할)
- 주소만 알면 된다 = (구현 클래스가 아니라) 인터페이스만 알면 된다는 의미에서 포트 폴더라고 한다.
## query (조회 로직)
- 도메인 로직을 포함하지 않아서 Service 클래스를 안 만들어도 된다.
## 서비스 계층
- 비즈니스 로직을 처리하고, 트랜잭션을 관리하며, 핸들러와 리포지토리를 연결하는 핵심 역할을 합니다. 애플리케이션의 주요 기능을 담당하는 곳
- 리포지토리 인터페이스는 도메인 계층에, 구현체는 인프라 계층에 두어 상위 모듈이 하위 모듈에 의존하지 않도록 합니다. 이것이 바로 의존성 역전 원칙(DIP)의 적용 예시
- DTO를 사용하면 도메인 객체의 내부 로직이나 민감한 정보를 외부에 노출하지 않고 필요한 데이터만 전달할 수 있습니다. 이는 시스템의 안정성과 보안을 높이는 데 도움이 된답니다.
- CQRS는 명령(쓰기)과 쿼리(읽기)를 분리하여 시스템의 복잡성을 줄이고 각 부분을 독립적으로 최적화할 수 있는 아키텍처 패턴
- 쿼리 리포지토리 인터페이스를 Port 패키지에 두는 것은 클린 아키텍처 원칙을 따르기 위함입니다. 이는 의존성의 방향을 외부(인프라)에서 내부(도메인/포트)로 향하게 하여 유연성을 확보하는 방법
---
# UI
- 키오스크 단말
- 키오스크 스크린
- 스크린 내에 메뉴 컨텐츠: 메뉴 스크린
- 스크린 내에 카트 컨텐츠: 카트 스크린
## 메인 화면 영역 컨텐츠
```java
🏠 // 메인 Header
[주문 시작하기] 주문을 시작하려면 엔터 키를 누르세요. // 메인 Body
```
## 메뉴 컨텐츠
```java
🏠
처음부터 다시 시작하시려면 :q 입력 후 엔터 // 여기까지 메뉴 헤더
=== 메뉴 === // 메뉴 바디 타이틀
1. SWEET STEAK(10000 원)
2. SIZE UP(15000 원)
3. FRIED RICE(2000 원) // 메뉴 바디 컨텐트
****************************
결제 예정 음식
---------------------------- // 여기까지 카트 헤더
선택하신 메뉴가 없습니다. // 여기까지 카트 바디
----------------------------
결제 예정 금액: 0원
**************************** // 여기까지 카트 푸터
```

## UserScreenContent Object content;
- 각각 컨텐츠에 해당하는 레코드 타입이 달라서 Object로 필드 선언
- 화면 컨텐츠들을 구조화하는 연습을 한다.
---
## UserKiosk
- processMainScreen: 메인
- processMenuScreen: 메뉴 (카트 포함)
- 메인에서 엔터 키 누르면 메뉴가 나온다.
- while로 반복한다.
## intValue()
- 숫자도 nextLine (String)으로 입력 받는 이유는 유효성 검증 같은 로직 처리가 유연해서다.
---
## 메뉴, 메뉴 아이템
- 메뉴 도메인이 메뉴 아이템 도메인을 책임지고 있다.
- 메뉴 핸들러에 요청하면 메뉴 핸들러가 메뉴 아이템들까지 가져온다.
- 루트가 메뉴인 것 같다. 루트가 아닌 Aggregate가 메뉴 아이템
```java
DDD(도메인 주도 설계)에서 Aggregate는 일관성 경계를 이루는 도메인 객체의 집합으로, 한 번에 변경되어야 하는 엔티티와 값 객체의 그룹을 의미합니다.
Aggregate의 주요 특징
일관성 경계: 여러 엔티티와 값 객체가 함께 변경될 때, 그 변경이 모두 일관성을 유지하도록 묶어주는 단위입니다.
트랜잭션 단위: 한 번에 하나의 Aggregate만 트랜잭션으로 처리하여, 데이터 무결성을 보장합니다.
Aggregate Root(AR): 외부에서 접근할 수 있는 진입점으로, 내부 엔티티는 AR을 통해서만 상태 변경이 가능합니다.
설계 및 활용 원칙
비즈니스 로직 중심: 엔티티, 값 객체, 도메인 서비스 등 도메인 개념을 명확히 구분하여 설계합니다.
마이크로서비스 연계: 각 Aggregate는 독립적으로 관리되어, 마이크로서비스 구조로의 확장이 용이합니다.
```

## 메뉴 타입에 해당하는 메뉴 아이템들이 가져와진다.
```java
String query = """
                SELECT * FROM menu m
                INNER JOIN menu_item mi ON m.MENU_ID = mi.MENU_ID
                WHERE m.MENU_TYPE = :menuType
                """;
```
- 조인 테이블이라서 칼럼 일부가 menuItem을 가리킨다.
```java
return jdbcClient
                .sql(query)
                .param("menuType", menuType.name())
                .query(new MenuWithItemsRowMapper())
                .optional().orElseThrow(() -> new NotFoundMenuException());
```
- new MenuWithItemsRowMapper() 대신 Menu.class로 해보았다. menuItems라는 멤버 변수가 맵핑이 안 된다.
- Menu가 필드로 가지는 menuItems는 리스트.. rowMapper가 필요하다.
---
## CartItem를 class 아닌 record
- 메뉴 아이템 객체 참조, 음식 수량만 필드로 가진다.
- 별도의 도메인 로직을 수행하지 않는다. Value Object 값 객체로서만 사용된다.
## SingletonCartFactory
- 카트 객체는 하나만 있으면 되니까 싱글톤으로 구현
- CART_ID를 고정
## existingItem.equals(newItem)
```java
CartItem updatedCartItem = updateCartItemQuantity(newItem);
            cartItemList.remove(newItem);
            cartItemList.add(updatedCartItem);
```
- 같기 때문에 newItem을 remove하면 existingItem이 remove된다.
- 같다는 건 필드 중에 menuItemId만 같다는 걸 말한다. (직접 equals 작성 필요, record여도 작성 가능)
## menuItemId == menuItemId 대신 Objects.equals(,)
- null-safe한 방법이라서 그렇다.
## cart save
```java
    @Override
    public void save(Cart cart) {
        // 싱글톤이어서 저장 안 해도 된다.
    }
```
- foundCart.addCartItem(cartItem)에서 이미 추가되었다.
## Cart foundCart = cartRepository.findById(cartId);
- repo로 바로 조회해도 되지만 CartFindService가 repo.findById를 이미 쓰고 있다.
- CartItemAddService에서는 CartFindService로 조회하도록 바꾸자.
## orderItemName OrderItem에 추가, 빌더 재정의
- menuItemId로 테이블에서 메뉴 아이템 정보 조회해서 주문 아이템 만들어야 된다.
## Order 생성 시 유효성 검증이 필요한 이유
- OrderPlaceService가 아니더라도 Order 객체를 생성할 수 있어서 검증한다.
- 도메인 객체에서 생성할 때 유효성을 검증하는 것 (도메인 객체에 규칙이 있기 때문에): 도메인 위주 개발 방식 스타일 (해당 도메인 객체를 안심하고 쓸 수 있다.)
- orderNo는 생성할 때 체크하도록 생성자를 만들었다. orderNo 형식에 맞는지까지 체크를 하는 것이 권장된다. (컴팩트 생성자라고 해서 검증하고 나서 파라미터 값을 필드에 저장한다.)
## CollectionUtils.isEmpty(orderItems)
- 여기서 null 확인을 하지만 필수임을 드러내려고 Objects.requireNotNull을 썼다.
## OrderRepoImpl에서 Service.save -> Repo.save(여기서 update 또는 insert)
- 도메인 관점에서 설계 진행, 구현하기 때문이다.
- 도메인 관점에서는 도메인 레이어에서 어떻게 로직을 작성할 것인가만 보고 인프라스트럭처 레이어에서 어떤 식으로 데이터를 처리하는지는 안 본다.
- 도메인을 데이터베이스에 저장해달라고 요청을 하지, 새로운 주문이니까 인서트/새로운 주문이 아니니까 업데이트 이런 구체적인 요청이 아니다.
- 즉 도메인과 인프라스트럭처 레이어의 관심사를 분리한다. 도메인은 save라는 추상화만 보고 구체 동작은 repo에 위임한다.
- db에는 인서트+업데이트 한 번에 처리하는 업서트라는 게 있다. 이걸 세이브 메서드에서 업서트 쿼리로 바로 써도 되는데 인서트, 업데이트로 나누어 둔 이유는 모든 db의 업서트 쿼리문이 동일하지는 않기 때문이다.
- db 종속을 줄이려고 인서트, 업데이트를 나눠서 처리했다.
## Repo.save에서 return order;하는 이유
- 파라미터로 넘어온 order에는 UI 출력 정보들이 모두 포함돼있다.
- db에서 오더 정보를 가져와서 UI로 전달하는 것보다 효율적이다.
## orderItems.forEach 대신 배치도 가능
- jdbcTemplate에서는 지원된다.
## printOrderDetails를 템플릿으로 작성해도 된다.