create keyspace if not exists retail_ks WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1' };

use retail_ks;

create type if not exists order_line (
    product_id uuid,
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
    product_id text,
    sale_count int,
    PRIMARY KEY ((product_id, sale_count))
    );


create table if not exists top_selling_products_by_customer (
    customer_id uuid,
    product_id int,
    sale_count int,
    PRIMARY KEY (customer_id, sale_count)
    )
    WITH CLUSTERING ORDER BY (sale_count DESC);




