create table user
(
    id                 bigint auto_increment
        primary key,
    deleted            bit          not null,
    user_account       varchar(255) null,
    user_name          varchar(255) null,
    user_password      varchar(255) null,
    user_register_time varchar(255) null
);

