create keyspace if not exists retail_ks WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1' };

use retail_ks;

create type if not exists order_line (
    product_id uuid,
    product_name text,
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
    product_id uuid,
    sale_count double,
    sale_value double,
    PRIMARY KEY (product_id)
    );






