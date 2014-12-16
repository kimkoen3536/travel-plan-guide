DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    email TEXT,
    password TEXT,
    account_name TEXT,
    name TEXT,
    birth_date DATE

);

DROP TABLE IF EXISTS plans;
CREATE TABLE plans (
    id INTEGER PRIMARY KEY,
    user_id int,
    account_name TEXT,
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
    plan_date DATE,
    name TEXT,
    address TEXT,
    road_address TEXT,
    map_x INTEGER,
    map_y INTEGER,
    type TEXT,
    picture BLOB,
    memo TEXT
);

DROP TABLE IF EXISTS transports;
CREATE TABLE transports (
    id INTEGER PRIMARY KEY,
    plan_id INTEGER,
    plan_date DATE,
    type TEXT,
    departure TEXT,
    destination TEXT,
    duration INTEGER,
    memo TEXT
);

DROP TABLE IF EXISTS likes;
CREATE TABLE likes (
    user_id TEXT,
    plan_id BIGINT
);
