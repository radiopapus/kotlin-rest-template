create table example
(
    id         uuid        not null,
    text       text        not null,
    version    bigint      not null,
    created_at TIMESTAMPTZ not null,
    updated_at TIMESTAMPTZ null,
    constraint pk_example primary key (id)
);
