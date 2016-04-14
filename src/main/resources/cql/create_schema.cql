create keyspace if not exists retail_ks WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1' };

use retail_ks;


create type if not exists order_line (
    sku ascii,
    product_name text,
    thumbnail_image text,
    quantity double,
    unit_price double,
    total_price double
);

create table if not exists orders (
    customer_id uuid,
    order_id timeuuid,
    date timestamp,
    order_lines_ list<frozen<order_line>>,
    total_price double,
    PRIMARY KEY (customer_id, order_id)
    )
    WITH CLUSTERING ORDER BY (order_id DESC);


create table if not exists top50_selling_products (
    sku ascii,
    product_name text,
    thumbnail_image text,
    sale_count double,
    sale_value double,
    PRIMARY KEY (sku)
    );


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

create table if not exists product_accessories (
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

create type if not exists recommended_product (
    sku ascii,
    product_name text,
    regular_price double,
    thumbnail_image text
);

create table if not exists product_recommendations (
    sku ascii,
    product_name text,
    recommended_products list<frozen<recommended_product>>,
    PRIMARY KEY (sku)
);










