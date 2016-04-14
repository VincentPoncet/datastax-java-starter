package com.datastax.retail.model;


import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "retail_ks", name = "top50_selling_products")
public class SellingProduct {

	@PartitionKey
	@Column(name = "sku")
	private String sku;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "thumbnail_image")
	private String thumbnailImage;

	@Column(name = "sale_count")
	private double saleCount;

	@Column(name = "sale_value")
	private double saleValue;


	public SellingProduct() {
		super();
	}

	public SellingProduct(String sku, String productName, String thumbnailImage, double saleCount, double saleValue) {
		this.sku = sku;
		this.productName = productName;
		this.thumbnailImage = thumbnailImage;
		this.saleCount = saleCount;
		this.saleValue = saleValue;
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

	public String getThumbnailImage() {
		return thumbnailImage;
	}

	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

	public double getSaleCount() {

        return saleCount;
	}

	public void setSaleCount(double saleCount) {

        this.saleCount = saleCount;
	}

    public double getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(double saleValue) {
        this.saleValue = saleValue;
    }

	@Override
	public String toString() {
		return "SellingProduct [sku=" + sku + ", saleCount=" + saleCount + ", saleValue=" + saleValue+ "]";
	}


}
