CREATE TABLE agreement
(
    `agreement_id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `article_id`    BIGINT NOT NULL,
    `agreement_num` BIGINT NOT NULL
);
create table agreement_history
(
    id                bigint auto_increment
        primary key,
    agreement_time    varchar(255) null,
    agreement_user_id bigint       not null,
    article_id        bigint       not null,
    deleted           bit          not null
);

create index index_tb_agreementUserId
    on agreement_history (agreement_user_id);