create table account
(
        id         bigint generated by default as identity,
        name       varchar(255),
        password   varchar(255),
        username   varchar(255),
        created_at timestamp(6),
        updated_at timestamp(6),
        primary key (id)
);


create table delivery
(
        id         bigint generated by default as identity,
        account_id bigint,
        address    varchar(255),
        status     varchar(255),
        created_at timestamp(6),
        updated_at timestamp(6),
        primary key (id)
);
