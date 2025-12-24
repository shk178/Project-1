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