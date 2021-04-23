create table if not exists products_group(

    id_products_group int auto_increment primary key ,
    id_shop int ,foreign key (id_shop) references SHOP(id_shop),
    products_group_date date

);


create table if not exists product (

  id_product int auto_increment primary key ,
  product_name varchar(20) not null ,
  product_price double not null ,
  product_note varchar(40) null,
  id_category int not null , foreign key (id_category) references CATEGORY(id_category),
  product_common boolean not null ,
  id_products_group int null ,foreign key (id_products_group) references PRODUCTS_GROUP(id_products_group)

);

