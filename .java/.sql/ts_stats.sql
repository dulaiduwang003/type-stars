create table if not exists ts_stars.ts_user
(
    user_id      bigint auto_increment
        primary key,
    open_id      varchar(200)           not null,
    role         enum ('ADMIN', 'USER') not null,
    created_time datetime               not null,
    update_time  datetime               not null
);

