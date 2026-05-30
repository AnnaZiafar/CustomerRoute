create table tier (
    id bigint auto_increment primary key,
    level varchar(50) unique not null,
    discount_percentage smallint not null
);

insert into tier values
(null, 'base', 0),
(null, 'gold', 30),
(null, 'silver', 10)
