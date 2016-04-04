drop keyspace if exists retail_ks;

create keyspace if not exists retail_ks WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1' };

use retail_ks;

/* cassandra-stress user profile=prodCatStress.yaml n=100000 ops\(insert=1\) */

create table if not exists product_catalog (
	color text,
    description text,
    image ascii,
    in_store_availability Boolean,
    manufacturer text,
    model_number text,
    name text,
    regular_price double,
    short_description text,
    sku ascii,
    thumbnail_image text,
    upc text,
    PRIMARY KEY (sku)
);
        
create type if not exists order_line (
    sku text,
    product_name text,
    quantity double,
    unit_price double,
    total_price double
);


create table if not exists orders (
	customer_id uuid,
	order_id timeuuid,
	date timestamp,
	order_lines list<frozen<order_line>>,
	PRIMARY KEY (customer_id, order_id)
) WITH CLUSTERING ORDER BY (order_id DESC);

create table if not exists top50_selling_products (
	sku ascii,
	sale_count int,
	test int,
	PRIMARY KEY (sku,sale_count)
)WITH CLUSTERING ORDER BY (sale_count ASC);


                
create table if not exists top_selling_products_by_customer (
	customer_id uuid,
	sku ascii,
	sale_count int,
	PRIMARY KEY (customer_id, sale_count)
)WITH CLUSTERING ORDER BY (sale_count DESC);



