alter table PRODUCT drop column PRODUCT_COMMON;
alter table PRODUCT add id_account int ;
alter table PRODUCT add foreign key (id_account) references ACCOUNT(id_account);