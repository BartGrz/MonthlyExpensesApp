alter table ACCOUNT_DEBT drop column BALANCE;
alter table ACCOUNT_DEBT add column id_account_owed int;

create table ACCOUNT_BALANCE (

     id_account int , foreign key (id_account) references ACCOUNT (id_account),
     balance double

);