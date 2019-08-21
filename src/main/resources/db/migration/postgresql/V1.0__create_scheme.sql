drop table if exists item cascade;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start 1000 increment 1;

create table item (
   id int8 not null,
    code varchar(255) not null,
    description varchar(2000),
    name varchar(255) not null,
    price float8 not null,
    version int4,
    primary key (id)
);
