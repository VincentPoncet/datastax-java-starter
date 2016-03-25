package com.datastax.retail.dao;

import java.util.List;

import com.datastax.retail.model.OrderAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Mapper;

import com.datastax.retail.model.SellingProduct;
import com.datastax.retail.model.SellingProductAccessor;

import com.datastax.retail.model.Order;

public class RetailDao {

	private static Logger logger = LoggerFactory.getLogger(RetailDao.class);
	private Session session;

	private String keyspace ="retail_ks";

	private MappingManager manager;

	private Mapper<SellingProduct> sellingProductMapper;
	private SellingProductAccessor sellingProductAccessor;

	private Mapper<Order> orderMapper;
	private OrderAccessor orderAccessor;


	public RetailDao(String[] contactPoints) {

		Cluster cluster = Cluster.builder().addContactPoints(contactPoints).build();

		this.session = cluster.connect(keyspace);
		this.manager = new MappingManager(session);

		this.sellingProductMapper = manager.mapper(SellingProduct.class);
		this.sellingProductAccessor = manager.createAccessor(SellingProductAccessor.class);

		this.orderMapper = manager.mapper(Order.class);
		this.orderAccessor = manager.createAccessor(OrderAccessor.class);

	}


	public List<SellingProduct> getTop50SellingProducts() {

		return sellingProductAccessor.getAll().all();
	}

	public List<Order> getAllOrdersByCustomer(java.util.UUID customerId) {

		return orderAccessor.getAll(customerId).all();
	}


}
