CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        customer_id VARCHAR(100) NOT NULL,
                        status VARCHAR(30) NOT NULL,
                        total_amount NUMERIC(12,2) NOT NULL CHECK (total_amount >= 0),
                        currency VARCHAR(3) NOT NULL,
                        correlation_id VARCHAR(100) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_orders_customer_id ON orders(customer_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_correlation_id ON orders(correlation_id);