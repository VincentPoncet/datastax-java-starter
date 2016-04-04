package com.datastax.retail.model;


import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(keyspace = "retail_ks", name = "top50_selling_products")
public class SellingProduct implements Comparable<SellingProduct> {

	@PartitionKey
	@Column(name = "product_id")
	private String sku;

	@Column(name = "sale_count")
	private double saleCount;

    @Column(name = "sale_count")
    private double saleValue;


	public SellingProduct() {
		super();
	}

	public String getSku() {

        return sku;
	}

	public void setSku(String sku) {

        this.sku = sku;
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

    public  SellingProduct( String sku, double saleCount, double saleValue ){
        this.sku = sku;
        this.saleCount = saleCount;
        this.saleValue = saleValue;
    }

	@Override
	public String toString() {
		return "SellingProduct [sku=" + sku + ", saleCount=" + saleCount + ", saleValue=" + saleValue+ "]";
	}

    @Override
    public int compareTo(SellingProduct s2) {
        if (this.getSaleCount()<s2.getSaleCount()){
            return -1;
        }else{
            return 1;
        }
    }




}
