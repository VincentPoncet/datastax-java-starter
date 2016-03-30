use retail_ks;

/*
* create type if not exists order_line (
*    product_id uuid,
*    quantity double,
*    unit_price double,
*    total_price double
$/ );


insert into orders(customer_id, order_id, date, order_lines_, total_price)  values (a4a70900-24e1-11df-8924-001ff3591711,now(),1,
[ { product_id:a4a70900-24e1-11df-8924-001ff3591712, quantity:10, unit_price:10.0, total_price:100.0} ,
{ product_id:a4a70900-24e1-11df-8924-001ff3591713, quantity:1, unit_price:1.0, total_price:1.0} ], 101.0 );

insert into orders(customer_id, order_id, date, order_lines_, total_price)  values (a4a70900-24e1-11df-8924-001ff3591711,now(),1,
[ { product_id:a4a70900-24e1-11df-8924-001ff3591714, quantity:100, unit_price:5.0, total_price:500.0} ,
 { product_id:a4a70900-24e1-11df-8924-001ff3591715, quantity:1, unit_price:1.0, total_price:1.0} ], 501.0 );

insert into top50_selling_products(product_id, sale_count) values ('1',100);
insert into top50_selling_products(product_id, sale_count) values ('2',50);
insert into top50_selling_products(product_id, sale_count) values ('3',500);