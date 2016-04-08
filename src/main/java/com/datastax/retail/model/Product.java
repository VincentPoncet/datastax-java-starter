package com.datastax.retail.model;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

@UDT(keyspace = "retail_ks", name = "product")
public class Product {

    @Field(name = "sku")
    private String sku;
    @Field(name = "product_name")
    private String productName;

    public Product() {
        super();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
