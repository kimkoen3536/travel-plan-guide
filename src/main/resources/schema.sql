CREATE TABLE users (
    id TEXT,
    password TEXT,
    name TEXT,
    birth_date DATE,
    email TEXT,
    PRIMARY KEY (id)
);

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

CREATE TABLE likes (
    user_id TEXT,
    plan_id BIGINT
);
