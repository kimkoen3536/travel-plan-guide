DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    account_name TEXT,
    password TEXT,
    name TEXT,
    birth_date DATE,
    email TEXT
);

DROP TABLE IF EXISTS plans;
CREATE TABLE plans (
    id INTEGER PRIMARY KEY,
    is_public BOOLEAN,
    title TEXT,
    start_date DATE,
    end_date DATE,
    location TEXT,
    num_likes INTEGER
);

DROP TABLE IF EXISTS places;
CREATE TABLE places (
    id INTEGER PRIMARY KEY,
    plan_id INTEGER,
    name TEXT,
    address TEXT,
    type TEXT,
    picture BLOB,
    memo TEXT
);

DROP TABLE IF EXISTS transports;
CREATE TABLE transports (
    id INTEGER PRIMARY KEY,
    plan_id INTEGER,
    type TEXT,
    source TEXT,
    destination TEXT,
    duration_min INTEGER,
    memo TEXT
);

DROP TABLE IF EXISTS likes;
CREATE TABLE likes (
    user_id TEXT,
    plan_id BIGINT
);
