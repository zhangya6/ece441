drop database if exists `test`;

create database `test`;

use `test`;

create table `plate`
(
    id int primary key not null auto_increment,
    number varchar(200),
    url varchar (200),
    count int 
);

create table `picture`
(
    id int primary key not null auto_increment,
    url varchar (200)
);

create table `user`
(
    id int primary key not null auto_increment,
    name varchar(200) unique,
    password varchar (200)
)