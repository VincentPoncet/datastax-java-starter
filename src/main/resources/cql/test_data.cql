use retail_ks;

insert into orders(customer_id, order_id, date, order_lines_, total_price)  values (a4a70900-24e1-11df-8924-001ff3591711,now(),1,
[ { sku:'a4a70900-24e1-11df-8924-001ff3591712', product_name: 'Product1', thumbnail_image: 'http', quantity:10, unit_price:10.0, total_price:100.0} ,
{ sku:'a4a70900-24e1-11df-8924-001ff3591713', product_name: 'Product2', thumbnail_image: 'http', quantity:1, unit_price:1000.0, total_price:1000.0} ,
{ sku:'a4a70900-24e1-11df-8924-001ff3591714', product_name: 'Product3', thumbnail_image: 'http', quantity:1, unit_price:5.0, total_price:5.0}], 1105.0 );

insert into orders(customer_id, order_id, date, order_lines_, total_price)  values (a4a70900-24e1-11df-8924-001ff3591711,now(),1,
[ { sku:'a4a70900-24e1-11df-8924-001ff3591714', product_name: 'Product3', thumbnail_image: 'http', quantity:100, unit_price:5.0, total_price:500.0} ,
 { sku:'a4a70900-24e1-11df-8924-001ff3591715', product_name: 'Product4', thumbnail_image: 'http', quantity:1, unit_price:1.0, total_price:1.0} ,
  { sku:'a4a70900-24e1-11df-8924-001ff3591713', product_name: 'Product2', thumbnail_image: 'http', quantity:1, unit_price:1000.0, total_price:1.0} ], 1501.0 );

 insert into orders(customer_id, order_id, date, order_lines_, total_price)  values (a4a70900-24e1-11df-8924-001ff3591711,now(),1,
 [ { sku:'a4a70900-24e1-11df-8924-001ff3591712', product_name: 'Product1',thumbnail_image: 'http',  quantity:100, unit_price:5.0, total_price:500.0} ,
  { sku:'a4a70900-24e1-11df-8924-001ff3591714', product_name: 'Product3', thumbnail_image: 'http', quantity:10, unit_price:5.0, total_price:50.0} ], 550.0 );

insert into top50_selling_products(sku, sale_count, sale_value) values ('a4a70900-24e1-11df-8924-001ff3591712',111.0, 600.0);
insert into top50_selling_products(sku, sale_count, sale_value) values ('a4a70900-24e1-11df-8924-001ff3591713',1.0, 1000.0);
insert into top50_selling_products(sku, sale_count, sale_value) values ('a4a70900-24e1-11df-8924-001ff3591714',110.0, 550.0);
insert into top50_selling_products(sku, sale_count, sale_value) values ('a4a70900-24e1-11df-8924-001ff3591715',1.0, 1.0);

insert into product_recommendations(sku, product_name, recommended_products) values ( 'a4a70900-24e1-11df-8924-001ff3591712', 'Product1',
[ { sku:'a4a70900-24e1-11df-8924-001ff3591713', product_name: 'Product2', regular_price:100.0, thumbnail_image : 'http' } ,
{ sku:'a4a70900-24e1-11df-8924-001ff3591714', product_name: 'Product3', regular_price:100.0, thumbnail_image : 'http' } ]  );