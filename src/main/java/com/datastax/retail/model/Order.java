package com.datastax.retail.model;

import java.time.Instant;
import java.util.List;

import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * Created by vincentponcet on 22/03/2016.
 */

@Table(keyspace = "retail_ks", name = "orders")
public class Order {

    @PartitionKey
    @Column(name = "customer_id")
    private java.util.UUID  customerId;

    @ClusteringColumn
    @Column(name = "order_id")
    private java.util.UUID orderId;

    @Column(name = "date", codec = InstantCodec.class)
    private Instant timestamp;

    @Column(name = "order_lines_")
    private List<OrderLine> orderLines;

    @Column(name = "total_price")
    private Double totalPrice;

    public Order() {
        super();
    }

    public java.util.UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(java.util.UUID orderId) {
        this.orderId = orderId;
    }

    public java.util.UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(java.util.UUID customerId) {
        this.customerId = customerId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", customerId=" + customerId + ", orderLines=" + "]";
    }


}
