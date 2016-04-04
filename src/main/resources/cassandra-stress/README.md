

### start cassandra-stress for top50_selling_products table
cassandra-stress user profile=top50SellProdStress.yaml n=100000 ops\(insert=20\) -node 10.10.11.10 -log file=top50SellProdStress.log

### start cassandra-stress fro product_catalog table
cassandra-stress user profile=prodCatStress.yaml n=100000 ops\(insert=20\) -node 10.10.11.10 -log file=prodCatStress.log
