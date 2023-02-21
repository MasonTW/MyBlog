create table article
(
    article_id             bigint auto_increment
        primary key,
    article_add_time       varchar(255) null,
    article_collection_num int          not null,
    article_content        varchar(255) null,
    article_look_times     int          not null,
    article_star           int          not null,
    article_title          varchar(255) null,
    article_update_time    varchar(255) null,
    article_user_id        bigint       not null,
    deleted                bit          not null,
    article_agreement_id   bigint       null,
    agreement_agreement_id bigint       null,
    constraint FKosl32461yw29riajqdg02r01k
        foreign key (agreement_agreement_id) references agreement (agreement_id),
    constraint fk_article_agreement
        foreign key (article_agreement_id) references agreement (agreement_id)
);

create table article_collection
(
    id            bigint auto_increment
        primary key,
    article_id    bigint not null,
    collection_id bigint not null
);

create table collection
(
    collection_id bigint auto_increment
        primary key,
    article_num   bigint       not null,
    name          varchar(255) null,
    user_id       bigint       not null
);


create table comment
(
    comment_id      bigint auto_increment
        primary key,
    article_id      bigint       not null,
    comment_content varchar(255) null,
    comment_star    int          not null,
    comment_time    varchar(255) null,
    user_id         bigint       not null
);


