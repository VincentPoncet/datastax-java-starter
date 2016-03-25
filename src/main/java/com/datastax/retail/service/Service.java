package com.datastax.retail.service;

import java.util.Collections;
import java.util.List;

import com.datastax.demo.utils.PropertyHelper;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.retail.dao.RetailDao;
import com.datastax.retail.model.Order;
import com.datastax.retail.model.SellingProduct;

public class Service {

	private RetailDao dao;

	public Service() {		
		String contactPointsStr = PropertyHelper.getProperty("contactPoints", "172.16.123.1");
		this.dao = new RetailDao(contactPointsStr.split(","));
	}	


	public List<SellingProduct> getTop50SellingProducts() {

		List<SellingProduct> top50 = dao.getTop50SellingProducts();
		Collections.sort(top50, Collections.reverseOrder());
		return top50;

	}

	public List<Order> getAllOrdersByCustomer(java.util.UUID customerId) { return dao.getAllOrdersByCustomer(customerId); }
}