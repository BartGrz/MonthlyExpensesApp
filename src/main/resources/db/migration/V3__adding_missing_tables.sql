create table account_debt (
    id_account int,
    debt double ,
    balance double,
    foreign key (id_account) references ACCOUNT(id_account)
);