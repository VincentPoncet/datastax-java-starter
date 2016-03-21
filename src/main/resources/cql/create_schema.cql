create keyspace if not exists retail_ks WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1' };

use retail_ks;

create type if not exists order_line (
    product_id uuid,
    quantity double,
    unit_price double,
    total_price double
);

create table if not exists orders (
    order_id timeuuid,
    customer_id uuid,
    date timestamp,
    order_lines_ list<frozen<order_line>>,
    total_price double,
    PRIMARY KEY (customer_id, order_id)
    )
    WITH CLUSTERING ORDER BY (order_id DESC);

create table if not exists top50_selling_products (
    product_id text,
    nb_sale int,
    PRIMARY KEY (product_id, nb_sale)
    )
    WITH CLUSTERING ORDER BY (nb_sale DESC);


create table if not exists top_selling_products_by_customer (
    customer_id uuid,
    product_id int,
    nb_bought int,
    PRIMARY KEY (customer_id, nb_bought)
    )
    WITH CLUSTERING ORDER BY (nb_bought DESC);




