package com.datastax.retail.service;

import com.datastax.demo.utils.PropertyHelper;
import com.datastax.retail.dao.RetailDao;
import com.datastax.retail.model.Order;
import com.datastax.retail.model.Product;
import com.datastax.retail.model.ProductRecommendation;
import com.datastax.retail.model.SellingProduct;

import java.util.*;
import java.util.stream.Collectors;



public class Service {

	private RetailDao dao;

	public Service() {		
		String contactPointsStr = PropertyHelper.getProperty("contactPoints", "127.0.0.1");
		this.dao = new RetailDao(contactPointsStr.split(","));
    }


    public List<SellingProduct> getTop50CountSellingProducts() {

		List<SellingProduct> top50 = dao.getTop50SellingProducts();
        Collections.sort(top50, (p1, p2) -> {
            if (p1.getSaleCount() > p2.getSaleCount()) {
                return -1;
            } else {
                return 1;
            }
        });
        return top50;

	}

    public List<SellingProduct> getTop50ValueSellingProducts() {

        List<SellingProduct> top50 = dao.getTop50SellingProducts();
        Collections.sort(top50, (p1, p2) -> {
            if (p1.getSaleValue() > p2.getSaleValue()) {
                return -1;
            } else {
                return 1;
            }
        });
        return top50;

    }

	public List<Order> getAllOrdersByCustomer(java.util.UUID customerId) {
        return dao.getAllOrdersByCustomer(customerId);
    }


    private List<SellingProduct> getTotalSellingProductsList(UUID customerId) {
        List<Order> orders = dao.getAllOrdersByCustomer(customerId);

        Map<String, Double> totalSellByProductCountMap = orders.stream()
                .flatMap(o ->  o.getOrderLines().stream())
                .map( ol -> new SellingProduct(ol.getSku(), ol.getQuantity(), ol.getTotalPrice()) )
                .collect(Collectors.groupingBy(SellingProduct::getSku, Collectors.summingDouble(SellingProduct::getSaleCount)));


        Map<String, Double> totalSellByProductValueMap = orders.stream()
                .flatMap(o ->  o.getOrderLines().stream())
                .map( ol -> new SellingProduct(ol.getSku(), ol.getQuantity(), ol.getTotalPrice()) )
                .collect(Collectors.groupingBy(SellingProduct::getSku, Collectors.summingDouble(SellingProduct::getSaleValue)));

        List<SellingProduct> totalSellByProductList = new ArrayList<SellingProduct>();
        totalSellByProductCountMap.forEach( (k,v) -> totalSellByProductList.add( new SellingProduct(k,v, totalSellByProductValueMap.get(k)) ) );
        return totalSellByProductList;
    }

    public List<SellingProduct> getMostSoldProductsCountByCustomer(java.util.UUID customerId) {
        List<SellingProduct> totalSellByProductList = getTotalSellingProductsList(customerId);

        Collections.sort(totalSellByProductList, (p1, p2) -> {
            if (p1.getSaleCount() > p2.getSaleCount()) {
                return -1;
            } else {
                return 1;
            }
        });

        return totalSellByProductList;
    }


    public List<SellingProduct> getMostSoldProductsValueByCustomer(java.util.UUID customerId) {
        List<SellingProduct> totalSellByProductList = getTotalSellingProductsList(customerId);

        Collections.sort(totalSellByProductList, (p1, p2) -> {
            if (p1.getSaleValue() > p2.getSaleValue()) {
                return -1;
            } else {
                return 1;
            }
        });

        return totalSellByProductList;
    }


    public List<Product> getRecommendedProductsBySku(String sku) {

        ProductRecommendation productRecommendation = dao.getProductRecommendation(sku);

        return productRecommendation.getRecommendedProducts();
    }

}
