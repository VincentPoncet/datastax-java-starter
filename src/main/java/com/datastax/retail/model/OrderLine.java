package com.datastax.retail.model;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

import java.util.UUID;

/**
 * Created by vincentponcet on 23/03/2016.
 * create type if not exists order_line (
 * product_id uuid,
 * quantity double,
 * unit_price double,
 * total_price double
 * );
 */

@UDT(name = "order_line", keyspace = "retail_ks")
public class OrderLine {

    @Field(name = "product_id")
    private java.util.UUID productId;

    @Field(name = "quantity")
    private Double quantity;

    @Field(name = "unit_price")
    private Double unitPrice;

    @Field(name = "total_price")
    private Double totalPrice;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
