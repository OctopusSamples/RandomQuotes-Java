create schema if not exists randomquotes;

-- alter schema data owner to postgres;

create table if not exists randomquotes.author
(
    id serial not null
        constraint author_pk
            primary key,
    author text not null
);

-- alter table author owner to postgres;

create unique index if not exists author_id_uindex
    on randomquotes.author (id);

create table if not exists randomquotes.quote
(
    id serial not null
        constraint quote_pk
            primary key,
    author_id integer not null
        constraint quote_author_id_fk
            references randomquotes.author,
    quote text not null
);

-- alter table quote owner to postgres;

create unique index if not exists quote_id_uindex
    on randomquotes.quote (id);

