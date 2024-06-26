CREATE TABLE IF NOT EXISTS endpoint_hits
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app       VARCHAR(64)  NOT NULL,
    uri       VARCHAR(255) NOT NULL,
    ip        VARCHAR(64)  NOT NULL,
    timestamp TIMESTAMP    WITHOUT TIME ZONE NOT NULL
);