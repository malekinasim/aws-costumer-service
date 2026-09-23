create table customer
( id serial primary key ,
name varchar(255) not null ,
favorite_genre varchar(100) not null );

insert into customer(name, favorite_genre)
values
    ('sam','ACTION'),
    ('sara','CRIME'),
    ('Nasim','DRAM');
