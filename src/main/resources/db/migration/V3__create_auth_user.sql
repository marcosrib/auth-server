create table auth_user (
    id char(36) not null,
    email varchar(255) default null,
    password varchar(255) default null,
    name varchar(255) default null,
    version bigint not null,
    enabled boolean not null,
    type varchar(20),
    created_at timestamp null default null,
    created_by_user_id char(36),
    last_modified_by_user_id char(36),
    last_modified_date timestamp null default null,

    constraint uq_auth_user_email unique (email),

    constraint chk_auth_user_type
        check (type in ('MANAGER', 'OPERATOR', 'CUSTOMER')),

    primary key (id)
) engine=InnoDB default charset=utf8mb4;