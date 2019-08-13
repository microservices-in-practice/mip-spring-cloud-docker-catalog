drop table item if exists;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 100 increment by 1;

create table item (
  id bigint not null,
  version integer not null,
  code varchar(20) not null,
  name varchar(255) not null,
  price double not null,
  description varchar(2000),
  primary key (id)
);
