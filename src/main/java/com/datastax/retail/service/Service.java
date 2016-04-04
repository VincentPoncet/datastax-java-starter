package com.datastax.retail.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.List;

import com.datastax.demo.utils.PropertyHelper;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.retail.dao.RetailDao;
import com.datastax.retail.model.Order;
import com.datastax.retail.model.OrderLine;
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

	public List<Order> getAllOrdersByCustomer(java.util.UUID customerId) {
        return dao.getAllOrdersByCustomer(customerId);
    }

    public List<SellingProduct> getMostSoldProductsByCustomer(java.util.UUID customerId) {
        List<Order> orders = dao.getAllOrdersByCustomer(customerId);

        Map<UUID, Double> totalSellByProductMap = orders.stream()
                .flatMap(o ->  o.getOrderLines().stream())
                .map( ol -> new SellingProduct(ol.getProductId(), ol.getQuantity(), ol.getTotalPrice()) )
                .collect(Collectors.groupingBy(SellingProduct::getProductId, Collectors.summingDouble(o2 -> o2.getSaleCount())));

        List<SellingProduct> totalSellByProductList = new ArrayList<SellingProduct>();
        totalSellByProductMap.forEach( (k,v) -> totalSellByProductList.add( new SellingProduct(k,v) ) );

        Collections.sort(totalSellByProductList, Collections.reverseOrder());

        return totalSellByProductList;
    }

}
