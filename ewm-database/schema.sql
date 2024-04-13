CREATE TABLE IF NOT EXISTS users
(
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name    VARCHAR(250)         NOT NULL,
    email   VARCHAR(254) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name        VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    event_id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title              VARCHAR(120)                                  NOT NULL,
    annotation         VARCHAR(2000)                                 NOT NULL,
    description        VARCHAR(7000)                                 NOT NULL,
    initiator_id       BIGINT REFERENCES users (user_id),
    category_id        BIGINT REFERENCES categories (category_id) ON DELETE RESTRICT,
    event_date         TIMESTAMP WITHOUT TIME ZONE,
    paid               BOOLEAN                     DEFAULT FALSE,
    lat                FLOAT                                         NOT NULL,
    lon                FLOAT                                         NOT NULL,
    participant_limit  INTEGER                     DEFAULT 0         NOT NULL,
    state              VARCHAR(120)                DEFAULT 'PENDING' NOT NULL,
    request_moderation BOOLEAN                     DEFAULT TRUE      NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()     NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    CHECK (event_date > NOW())
);

CREATE TABLE IF NOT EXISTS participation_requests
(
    request_id   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    requester_id BIGINT REFERENCES users (user_id),
    event_id     BIGINT REFERENCES events (event_id),
    created_on   TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()     NOT NULL,
    status       VARCHAR(120)                DEFAULT 'PENDING' NOT NULL,
    UNIQUE (requester_id, event_id)
);

CREATE TABLE IF NOT EXISTS compilations
(
    compilation_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    pinned         BOOLEAN DEFAULT FALSE NOT NULL,
    title          VARCHAR(50)          NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilation_id BIGINT REFERENCES compilations (compilation_id),
    event_id       BIGINT REFERENCES events (event_id),
    PRIMARY KEY (compilation_id, event_id)
);