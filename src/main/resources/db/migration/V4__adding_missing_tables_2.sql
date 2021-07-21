create table bill_sum (
    id_bill int ,
    sum double ,
    foreign key (id_bill) references BILL(id_bill)
);