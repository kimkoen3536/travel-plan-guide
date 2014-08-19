DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id TEXT,
    password TEXT,
    name TEXT,
    birth_date DATE,
    email TEXT,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS plans;
CREATE TABLE plans (
    id BIGINT,
    is_public BOOLEAN,
    title TEXT,
    start_date DATE,
    end_date DATE,
    location TEXT,
    num_likes INTEGER,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS places;
CREATE TABLE places (
    id BIGINT,
    plan_id BIGINT,
    name TEXT,
    address TEXT,
    type TEXT,
    picture BLOB,
    memo TEXT,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS transports;
CREATE TABLE transports (
    id BIGINT,
    plan_id BIGINT,
    type TEXT,
    source TEXT,
    destination TEXT,
    duration_min INTEGER,
    memo TEXT,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS likes;
CREATE TABLE likes (
    user_id TEXT,
    plan_id BIGINT
);
