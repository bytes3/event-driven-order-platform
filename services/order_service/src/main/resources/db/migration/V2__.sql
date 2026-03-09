DROP INDEX idx_orders_customer_id;
DROP INDEX idx_orders_status;
DROP INDEX idx_orders_correlation_id;

DROP TABLE orders;

CREATE TABLE orders
(
    order_id       VARCHAR(255) NOT NULL,
    status         VARCHAR(255),
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    customer_id    VARCHAR(255),
    total_amount   DECIMAL,
    currency       VARCHAR(255),
    correlation_id VARCHAR(255),
    CONSTRAINT pk_order PRIMARY KEY (order_id)
);

CREATE SEQUENCE IF NOT EXISTS order_item_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE order_item
(
    id         BIGINT       NOT NULL,
    sku        VARCHAR(255),
    quantity   INTEGER      NOT NULL,
    unit_price DECIMAL,
    order_id   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_order_item PRIMARY KEY (id)
);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (order_id);
