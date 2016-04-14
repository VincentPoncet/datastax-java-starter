package com.datastax.retail.model;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

@UDT(keyspace = "retail_ks", name = "recommended_product")
public class RecommendedProduct {

    @Field(name = "sku")
    private String sku;
    @Field(name = "product_name")
    private String productName;
    @Field(name = "regular_price")
    private Double regularPrice;
    @Field(name = "thumbnail_image")
    private String thumbnailImage;

    public RecommendedProduct() {
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

    public Double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(Double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

}
