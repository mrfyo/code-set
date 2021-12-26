create table error_message
(
    id      int auto_increment
        primary key,
    code    varchar(10)  not null,
    title   varchar(100) not null,
    content varchar(255) not null,
    constraint error_message_code_uindex
        unique (code),
    constraint error_message_title_uindex
        unique (title)
)
    comment '错误消息表' charset = utf8mb4;

create table question
(
    id              int auto_increment
        primary key,
    question_number int               not null,
    title           varchar(255)      not null comment '标题',
    difficulty      tinyint default 0 not null
)
    comment '问题集' charset = utf8mb4;

create table question_detail
(
    id      int  not null comment 'question 同步主键',
    content text null,
    constraint error_message_code_uindex
        unique (id)
)
    comment '题目内容' charset = utf8mb4;

create table question_like
(
    id          int auto_increment
        primary key,
    question_id int not null,
    user_id     int not null
)
    comment '问题点赞记录' charset = utf8mb4;

create table question_statistic
(
    id                 int              not null comment 'question 同步主键',
    solution           bigint default 0 not null,
    success_submission bigint default 0 not null,
    fail_submission    bigint default 0 not null,
    constraint error_message_code_uindex
        unique (id)
)
    comment '问题统计' charset = utf8mb4;

create table question_tag
(
    id          int auto_increment
        primary key,
    tag_id      int not null,
    question_id int not null
)
    comment '问题与标签' charset = utf8mb4;

create table role
(
    id      int auto_increment
        primary key,
    name_en varchar(50)  not null,
    name_cn varchar(255) not null comment '角色名'
)
    comment '角色表' charset = utf8mb4;

create table solution
(
    id          int auto_increment
        primary key,
    title       varchar(255)            not null comment '标题',
    summary     varchar(128) default '' not null,
    question_id int                     not null,
    user_id     int                     not null,
    create_at   timestamp               not null
)
    comment '解题集' charset = utf8mb4;

create table solution_detail
(
    id      int  not null comment 'solution 同步主键',
    content text null,
    constraint error_message_code_uindex
        unique (id)
)
    comment '题解内容' charset = utf8mb4;

create table solution_like
(
    id          int auto_increment
        primary key,
    solution_id int not null,
    user_id     int not null
)
    comment '题解点赞记录' charset = utf8mb4;

create table solution_tag
(
    id          int auto_increment
        primary key,
    tag_id      int not null,
    solution_id int not null
)
    comment '题解与标签' charset = utf8mb4;

create table submission
(
    id          int auto_increment
        primary key,
    result      tinyint default 0 not null comment '提交结果 1 失败; 2 成功',
    question_id int               not null,
    user_id     int               not null,
    time_cost   int               not null comment '时间消耗',
    memory_cost int               not null comment '内存消耗',
    language_id int               not null comment '语言',
    create_at   timestamp         null comment '提交时间'
)
    comment '提交记录' charset = utf8mb4;

create table submission_detail
(
    id            int auto_increment
        primary key,
    submission_id int  not null,
    content       text null
)
    comment '提交内容' charset = utf8mb4;

create table submission_language
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table tag
(
    id   int auto_increment
        primary key,
    name varchar(255) not null comment '标题'
)
    comment '问题的标签' charset = utf8mb4;

create table user
(
    id       int auto_increment
        primary key,
    username varchar(255) not null,
    password varchar(255) not null
)
    comment '用户表' charset = utf8mb4;

create table user_profile
(
    id    int          not null comment 'user id 同步主键',
    alias varchar(255) not null,
    constraint error_message_code_uindex
        unique (id)
)
    comment '用户配置表' charset = utf8mb4;

create table user_question
(
    id          int auto_increment
        primary key,
    user_id     int               not null,
    question_id int               not null,
    status      tinyint default 0 not null comment '0 未尝试 1 尝试中 2 通过 3 未通过'
);

create table user_role
(
    id      int auto_increment
        primary key,
    user_id int not null,
    role_id int not null
)
    comment '用户与角色中间表' charset = utf8mb4;


