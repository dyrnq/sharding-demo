-- 先创建两个库
-- create database course_db;

create database IF NOT EXISTS course_db_1;
create database IF NOT EXISTS course_db_2;
-- 在两个库中分别创建四个结构相同的表
use course_db_1;
create table course_0
(
    id     bigint(20) primary key,
    name   varchar(50),
    status TINYINT
);

create table course_1
(
    id     bigint(20) primary key,
    name   varchar(50),
    status TINYINT
);

create table course_2
(
    id     bigint(20) primary key,
    name   varchar(50),
    status TINYINT
);

create table course_3
(
    id     bigint(20) primary key,
    name   varchar(50),
    status TINYINT
);

create table course_4
(
    id     bigint(20) primary key,
    name   varchar(50),
    status TINYINT
);

use course_db_2;

create table course_0
(
    id     bigint(20) primary key,
    name   varchar(50),
    status TINYINT
);

create table course_1
(
    id     bigint(20) primary key,
    name   varchar(50),
    status TINYINT
);

create table course_2
(
    id     bigint(20) primary key,
    name   varchar(50),
    status TINYINT
);

create table course_3
(
    id     bigint(20) primary key,
    name   varchar(50),
    status TINYINT
);

create table course_4
(
    id     bigint(20) primary key,
    name   varchar(50),
    status TINYINT
);