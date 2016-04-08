package com.datastax.retail.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.List;


@Table(keyspace = "retail_ks", name = "product_recommendations")
public class ProductRecommendation {


    @PartitionKey
    @Column(name = "sku")
    private String sku;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "recommended_products")
    private List<Product> recommendedProducts;

    public ProductRecommendation() {
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

    public List<Product> getRecommendedProducts() {
        return recommendedProducts;
    }

    public void setRecommendedProducts(List<Product> recommendedProducts) {
        this.recommendedProducts = recommendedProducts;
    }

}
