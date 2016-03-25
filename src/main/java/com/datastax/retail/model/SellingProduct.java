package com.datastax.retail.model;


import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "retail_ks", name = "top50_selling_products")
public class SellingProduct implements Comparable<SellingProduct> {

	@PartitionKey(0)
	@Column(name = "product_id")
	private String productId;

	@PartitionKey(1)
	@Column(name = "sale_count")
	private int saleCount;


	public SellingProduct() {
		super();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}



	@Override
	public String toString() {
		return "SellingProduct [productId=" + productId + ", saleCount=" + saleCount + "]";
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
