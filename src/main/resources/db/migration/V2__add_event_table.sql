create table tier_events (
    id bigint auto_increment primary key,
    level varchar(50) not null,
    event_type varchar(50) not null,

    payload json not null,
    performed_by varchar(100) not null,
    occurred_at timestamp default current_timestamp
);