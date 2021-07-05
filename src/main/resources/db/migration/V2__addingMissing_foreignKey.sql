alter table BILL add column id_account int;
alter table BILL add foreign key (id_account) references ACCOUNT(id_account);

alter table PRODUCT add column id_bill int ;
alter table PRODUCT add foreign key (id_bill) references BILL(ID_BILL);