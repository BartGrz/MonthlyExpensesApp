CREATE table shop
(

    id_shop   int primary key auto_increment,
    shop_name varchar(20)
);

CREATE table account
(
    id_account   int primary key auto_increment,
    account_name varchar(20)
);

create table if not exists category
(

    id_category   int auto_increment primary key,
    category_name varchar(20)

);

create table Bill
(

    id_bill   int auto_increment primary key,
    id_shop    int,
    foreign key (id_shop) references SHOP (id_shop),
    group_date date

);


create table product
(

    id_product     int auto_increment primary key,
    product_name   varchar(20) not null,
    product_price  double      not null,
    product_note   varchar(40) null,
    id_category    int         not null,
    foreign key (id_category) references CATEGORY (id_category),
    product_common boolean     not null

);


