create table tier_events (
    id bigint auto_increment primary key,
    level varchar(50) unique not null,

    payload json not null,
    occurred_at timestamp default current_timestamp
);