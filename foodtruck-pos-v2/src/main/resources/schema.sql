--   MENU DOMAIN
--   - 판매 가능한 메뉴 항목과 분류(카테고리) 관리
--   - 세금 정책(tax_policy)와 연결됨
-- 메뉴 카테고리 테이블
-- 예: 세트 / 단품 / 사이드 / 음료
CREATE TABLE menu_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,     -- 카테고리 이름
    display_order INT NOT NULL      -- 정렬 순서(작을수록 먼저 표시)
    admin_user_id BIGINT NOT NULL,
    FOREIGN KEY (admin_user_id) REFERENCES admin_user(id)
);
-- 메뉴 항목 테이블
-- 가격과 세금 정책을 참조하지만, 주문 시점의 값은 스냅샷으로 order_item에 따로 저장
CREATE TABLE menu_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,    -- 메뉴 항목이 속한 카테고리
    name VARCHAR(100) NOT NULL,     -- 메뉴 항목 이름
    price INT NOT NULL,             -- 현재 판매 단가(수정 가능)
    tax_policy_id BIGINT NOT NULL,  -- 현재 연결된 세금 정책
    is_active BOOLEAN NOT NULL DEFAULT TRUE,  -- 판매 여부(품절/숨김 처리 가능)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    admin_user_id BIGINT NOT NULL,
    FOREIGN KEY (admin_user_id) REFERENCES admin_user(id)
    FOREIGN KEY (category_id) REFERENCES menu_category(id),
    FOREIGN KEY (tax_policy_id) REFERENCES tax_policy(id)
);

--   ORDER DOMAIN
--   - 주문과 주문 항목
--   - cart = CREATED 상태의 주문
--   - 금액은 "스냅샷"으로 저장하여 사후 변경 영향 방지
-- 주문 테이블
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_number VARCHAR(30) UNIQUE NOT NULL, -- 고객에게 표시되는 주문/호출 번호
    status VARCHAR(20) NOT NULL DEFAULT 'CREATED', -- CREATED / PAID / COOKING / COMPLETED / CANCELED 등
    -- 금액 스냅샷(재계산 금지: 당시 기준 그대로 보관)
    total_amount INT NOT NULL DEFAULT 0,      -- 총 결제 금액(부가세 포함 총액)
    taxable_amount INT NOT NULL DEFAULT 0,    -- 과세 대상 공급가액 합계
    tax_amount INT NOT NULL DEFAULT 0,        -- 총 부가세 합계
    tax_free_amount INT NOT NULL DEFAULT 0,   -- 면세 금액 합계
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    admin_user_id BIGINT NOT NULL,
    FOREIGN KEY (admin_user_id) REFERENCES admin_user(id)
);
-- 주문 항목 테이블
-- 주문 시점의 메뉴 항목/가격/세율을 스냅샷으로 보존
CREATE TABLE order_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,                 -- 상위 주문 id
    menu_item_id BIGINT NULL,                 -- 원본 메뉴 항목 id (삭제되거나 수정되어도 무관)
                                              -- 이름·가격은 아래 스냅샷 컬럼 사용
    -- 주문 시점의 스냅샷 데이터
    menu_item_name VARCHAR(100) NOT NULL,     -- 주문 당시 메뉴 항목 이름
    unit_price INT NOT NULL,                  -- 주문 당시 단가
    tax_rate DECIMAL(5,2) NOT NULL,           -- 주문 당시 적용 세율
    quantity INT NOT NULL,                    -- 수량
    line_amount INT NOT NULL,                 -- 항목 총액 (단가 × 수량)
    tax_amount INT NOT NULL,                  -- 해당 항목의 부가세 금액
    admin_user_id BIGINT NOT NULL,
    FOREIGN KEY (admin_user_id) REFERENCES admin_user(id)
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_item(id)
);

--   PAYMENT DOMAIN
--   - 결제 승인/실패/환불 상태 관리
--   - 실제 "지불 행위"와 연결된 도메인
-- 결제 테이블
-- 주문당 단일 결제(분할결제 미지원) 가정
CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,                 -- 어느 주문에 대한 결제인지
    method VARCHAR(20) NOT NULL,              -- CASH / CARD / EASY_PAY
    status VARCHAR(20) NOT NULL,              -- REQUESTED / PAID / FAILED / REFUNDED
    amount INT NOT NULL,                      -- 결제 금액 스냅샷 (orders.total_amount 복사)
    approved_at TIMESTAMP NULL,               -- 결제 승인 완료 시각
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    admin_user_id BIGINT NOT NULL,
    FOREIGN KEY (admin_user_id) REFERENCES admin_user(id)
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

--   SETTLEMENT DOMAIN
--   - 수수료 계산 및 실입금액 관리
--   - PG사 수수료/간편결제 수수료 등을 반영
-- 결제 수수료 정책 테이블
-- 결제 수단별 수수료율이 변경될 수 있으므로 정책으로 관리
CREATE TABLE payment_fee_policy (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    method VARCHAR(20) NOT NULL,             -- CARD / EASY_PAY
    fee_rate DECIMAL(5,3) NOT NULL,          -- 3.500 = 3.5%
    is_active BOOLEAN DEFAULT TRUE,          -- 현재 사용 여부
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    admin_user_id BIGINT NOT NULL,
    FOREIGN KEY (admin_user_id) REFERENCES admin_user(id)
);
-- 실제 결제 건에 적용된 수수료 스냅샷 테이블
-- 정책이 바뀌어도 과거 기록이 변하지 않도록 별도 저장
CREATE TABLE payment_fee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_id BIGINT NOT NULL,              -- 대상 결제 id
    fee_rate DECIMAL(5,3) NOT NULL,          -- 실제 적용된 수수료율
    fee_amount INT NOT NULL,                 -- 수수료 금액 (amount × rate)
    net_amount INT NOT NULL,                 -- 실입금액(정산액) = 결제금액 - 수수료
    admin_user_id BIGINT NOT NULL,
    FOREIGN KEY (admin_user_id) REFERENCES admin_user(id)
    FOREIGN KEY (payment_id) REFERENCES payment(id)
);

--   TAXATION DOMAIN
--   - 세금 정책 및 세금 계산 결과 기록
--   - 세무사 제출용 데이터 근간
-- 세금 정책 테이블
-- 세율 변경 이력 관리를 위해 기간 컬럼 포함
CREATE TABLE tax_policy (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,             -- 정책 이름 (ex: 부가세 10%)
    tax_type VARCHAR(20) NOT NULL,         -- TAXABLE / ZERO / EXEMPT
    tax_rate DECIMAL(5,2) NOT NULL,        -- 10.00 = 10%
    is_active BOOLEAN DEFAULT TRUE,        -- 현재 사용 여부
    applied_from DATE NOT NULL,            -- 적용 시작일
    applied_to DATE NULL                   -- 적용 종료일(null이면 현재 유효)
    admin_user_id BIGINT NOT NULL,
    FOREIGN KEY (admin_user_id) REFERENCES admin_user(id)
);
-- 세금 기록 스냅샷
-- 주문 시점 기준으로 확정된 과세 정보를 남김
CREATE TABLE tax_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,                -- 어떤 주문에 대한 세금 기록인지
    supply_amount INT NOT NULL,              -- 공급가액(부가세 제외 금액)
    tax_amount INT NOT NULL,                 -- 부가세 금액
    tax_rate DECIMAL(5,2) NOT NULL,          -- 적용 세율 스냅샷
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    admin_user_id BIGINT NOT NULL,
    FOREIGN KEY (admin_user_id) REFERENCES admin_user(id)
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

--   IAM DOMAIN
--   - 관리자 로그인 계정 및 권한
--   - 사용자 모드는 비로그인, 관리자 전용 로그인
CREATE TABLE admin_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,     -- 로그인 ID
    password_hash VARCHAR(255) NOT NULL,      -- 암호화된 비밀번호
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
