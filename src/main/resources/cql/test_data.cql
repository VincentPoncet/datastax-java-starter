use retail_ks;

insert into orders(customer_id, order_id, date, order_lines_, total_price)  values (a4a70900-24e1-11df-8924-001ff3591711,now(),1,
[ { sku:'a4a70900-24e1-11df-8924-001ff3591712', quantity:10, unit_price:10.0, total_price:100.0} ,
{ sku:'a4a70900-24e1-11df-8924-001ff3591713', quantity:1, unit_price:1.0, total_price:1.0} ], 101.0 );

insert into orders(customer_id, order_id, date, order_lines_, total_price)  values (a4a70900-24e1-11df-8924-001ff3591711,now(),1,
[ { sku:'a4a70900-24e1-11df-8924-001ff3591714', quantity:100, unit_price:5.0, total_price:500.0} ,
 { sku:'a4a70900-24e1-11df-8924-001ff3591715', quantity:1, unit_price:1.0, total_price:1.0} ], 501.0 );

 insert into orders(customer_id, order_id, date, order_lines_, total_price)  values (a4a70900-24e1-11df-8924-001ff3591711,now(),1,
 [ { sku:'a4a70900-24e1-11df-8924-001ff3591712', quantity:101, unit_price:5.0, total_price:500.0} ,
  { sku:'a4a70900-24e1-11df-8924-001ff3591714', quantity:10, unit_price:1.0, total_price:1.0} ], 501.0 );

insert into top50_selling_products(sku, sale_count) values ('a4a70900-24e1-11df-8924-001ff3591712',100);
insert into top50_selling_products(sku, sale_count) values ('a4a70900-24e1-11df-8924-001ff3591713',50);
insert into top50_selling_products(sku, sale_count) values ('a4a70900-24e1-11df-8924-001ff3591714',500);