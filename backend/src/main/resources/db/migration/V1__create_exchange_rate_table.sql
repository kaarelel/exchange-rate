-- Flyway migration for creating exchange_rate table
CREATE TABLE IF NOT EXISTS exchange_rate (
                                             id SERIAL PRIMARY KEY,
                                             date DATE NOT NULL,
                                             currency_code VARCHAR(10) NOT NULL,
                                             rate DOUBLE PRECISION NOT NULL
);