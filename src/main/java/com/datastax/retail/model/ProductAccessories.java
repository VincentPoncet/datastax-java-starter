package com.datastax.retail.model;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "retail_ks", name = "product_accessories")
public class ProductAccessories {


    @PartitionKey
    private String  sku;
  	private String 	color;
    private String 	description;
    private String 	image;
    private Boolean in_store_availability;
    private String 	manufacturer;
    private String 	model_number;
    private String 	name;
    private Double 	regular_price;
    private String 	short_description;
    private String 	thumbnail_image;
    private String 	upc;


	public ProductAccessories() {
		super();

	}

	public ProductAccessories(String sku, String color, String description,
							  String image, Boolean in_store_availability, String manufacturer,
    		String model_number,String name,Double regular_price, String short_description, 
    		String thumbnail_image, String upc) 
    {
    	this.sku = sku;
    	this.color = color;
    	this.description = description;
    	this.image = image;
    	this.in_store_availability = in_store_availability;
    	this.manufacturer = manufacturer;
    	this.model_number = model_number;
    	this.name = name;
    	this.regular_price = regular_price;
    	this.short_description = short_description;
    	this.thumbnail_image = thumbnail_image;
    	this.upc = upc;
    }

    public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getIn_store_availability() {
		return in_store_availability;
	}

	public void setIn_store_availability(Boolean in_store_availability) {
		this.in_store_availability = in_store_availability;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel_number() {
		return model_number;
	}

	public void setModel_number(String model_number) {
		this.model_number = model_number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRegular_price() {
		return regular_price;
	}

	public void setRegular_price(Double regular_price) {
		this.regular_price = regular_price;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public String getThumbnail_image() {
		return thumbnail_image;
	}

	public void setThumbnail_image(String thumbnail_image) {
		this.thumbnail_image = thumbnail_image;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}


}
