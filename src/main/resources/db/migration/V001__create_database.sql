create table if not exists clients(
    id      varchar(36) primary key,
    login   varchar(32) not null unique,
    balance bigint      not null
);