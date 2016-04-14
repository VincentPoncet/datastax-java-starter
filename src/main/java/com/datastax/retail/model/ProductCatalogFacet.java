package com.datastax.retail.model;

public class ProductCatalogFacet {

	public String name;
	public Long amount;

	public ProductCatalogFacet() {
		super();
	}

	public ProductCatalogFacet(String name, Long number) {
		this.setAmount(number);
		this.setName(name);

	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
