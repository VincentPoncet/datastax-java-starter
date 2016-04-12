package com.datastax.retail.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.retail.model.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import com.datastax.demo.utils.SolrHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetailDao {

	private Session session;

	private String keyspace = "retail_ks";

	private MappingManager manager;

	private SellingProductAccessor sellingProductAccessor;

	private OrderAccessor orderAccessor;

	private Mapper<ProductRecommendation> productRecommendationMapper;

	private ProductCatalogAccessor prodAccessor;
	
	private ProductAccessoriesAccessor accessoriesAccessor;
	
	public RetailDao(String[] contactPoints) {

		Cluster cluster = Cluster.builder().addContactPoints(contactPoints).build();

		this.session = cluster.connect(keyspace);
		this.manager = new MappingManager(session);

		manager.mapper(SellingProduct.class);
		this.sellingProductAccessor = manager.createAccessor(SellingProductAccessor.class);

		manager.mapper(Order.class);
		this.orderAccessor = manager.createAccessor(OrderAccessor.class);

		this.productRecommendationMapper = manager.mapper(ProductRecommendation.class);

		manager.mapper(ProductCatalog.class);
		this.prodAccessor = manager.createAccessor(ProductCatalogAccessor.class);
		
		manager.mapper(ProductAccessories.class);
		this.accessoriesAccessor = manager.createAccessor(ProductAccessoriesAccessor.class);
	}

	public List<SellingProduct> getTop50SellingProducts() {

		return sellingProductAccessor.getAll().all();
	}

	public List<Order> getAllOrdersByCustomer(java.util.UUID customerId) {

		return orderAccessor.getAll(customerId).all();
	}

	public ProductRecommendation getProductRecommendation(String sku) {
		return productRecommendationMapper.get(sku);
	}

	public List<ProductCatalog> getProductsSolrQuery(java.lang.String search_term, java.lang.String filter_by) {

		String solr_query = SolrHelper.makeSolrQueryString(search_term, filter_by);
		solr_query = "{" + solr_query + "}";

		return prodAccessor.getAll(solr_query).all();
	}

	public Map<String, List<ProductCatalogFacet>> getProductSolrFacets(String search_term, List<String> facet_columns) {

		final Map<String, List<ProductCatalogFacet>> facetMap = new HashMap<>();

		String solr_query = SolrHelper.makeSolrFacetString(search_term, facet_columns);

		StringBuilder statement = new StringBuilder().append("SELECT * FROM product_catalog WHERE solr_query = ")
				.append(solr_query);

		ResultSet resultSet = session.execute(statement.toString());

		String facet_json = resultSet.one().getString("facet_fields");

		JSONObject jsonObj = (JSONObject) JSONValue.parse(facet_json);
		for (Object field : jsonObj.keySet()) {

			JSONObject facets = (JSONObject) jsonObj.get(field);

			List<ProductCatalogFacet> facetList = new ArrayList<>();

			for (Object entry : facets.entrySet()) {
				Map.Entry<String, Long> j = (Map.Entry<String, Long>) entry;
				facetList.add(new ProductCatalogFacet(j.getKey(), j.getValue()));
			}
			facetList.sort(new Comparator<ProductCatalogFacet>() {
				@Override
				public int compare(ProductCatalogFacet o1, ProductCatalogFacet o2) {
					// Note: opposite of normal for reverse sort
					return -o1.amount.compareTo(o2.amount);
				}
			});
			facetMap.put((String) field, facetList);
		}
		return facetMap;
	}

	public Map<String, List<ProductAccessoriesFacet>> getAccessoriesSolrFacets(String search_term,
			List<String> facet_columns) {
		
		final Map<String, List<ProductAccessoriesFacet>> facetMap = new HashMap<>();

		String solr_query = SolrHelper.makeSolrFacetString(search_term, facet_columns);

		StringBuilder statement = new StringBuilder().append("SELECT * FROM product_accessories WHERE solr_query = ")
				.append(solr_query);

		ResultSet resultSet = session.execute(statement.toString());

		String facet_json = resultSet.one().getString("facet_fields");

		JSONObject jsonObj = (JSONObject) JSONValue.parse(facet_json);
		for (Object field : jsonObj.keySet()) {

			JSONObject facets = (JSONObject) jsonObj.get(field);

			List<ProductAccessoriesFacet> facetList = new ArrayList<>();

			for (Object entry : facets.entrySet()) {
				Map.Entry<String, Long> j = (Map.Entry<String, Long>) entry;
				facetList.add(new ProductAccessoriesFacet(j.getKey(), j.getValue()));
			}
			facetList.sort(new Comparator<ProductAccessoriesFacet>() {
				@Override
				public int compare(ProductAccessoriesFacet o1, ProductAccessoriesFacet o2) {
					// Note: opposite of normal for reverse sort
					return -o1.amount.compareTo(o2.amount);
				}
			});
			facetMap.put((String) field, facetList);
		}
		return facetMap;
		
	}

	public List<ProductAccessories> getAccessoriesSolrQuery(String search_term, String filter_by) {
		String solr_query = SolrHelper.makeSolrQueryString(search_term, filter_by);
		solr_query = "{" + solr_query + "}";

		return accessoriesAccessor.getAll(solr_query).all();
	}

}
