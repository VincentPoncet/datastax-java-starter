package com.datastax.retail.webservice;

import com.datastax.retail.model.*;
import com.datastax.retail.service.Service;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Map;

@WebService
@Path("/")
public class RetailWS {

	// Service Layer.
	private Service service = new Service();

	@GET
	@Path("/getTop50CountSellingProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTop50CountSellingProducts() {

		List<SellingProduct> result = service.getTop50CountSellingProducts();

		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getTop50ValueSellingProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTop50ValueSellingProducts() {

		List<SellingProduct> result = service.getTop50ValueSellingProducts();

		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getAllOrdersByCustomer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOrdersByCustomer(@PathParam("customerId") java.util.UUID customerId) {

		List<Order> result = service.getAllOrdersByCustomer(customerId);

		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getMostSoldProductsCountByCustomer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMostSoldProductsCountByCustomer(@PathParam("customerId") java.util.UUID customerId) {

		List<SellingProduct> result = service.getMostSoldProductsCountByCustomer(customerId);

		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getMostSoldProductsValueByCustomer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMostSoldProductsValueByCustomer(@PathParam("customerId") java.util.UUID customerId) {

		List<SellingProduct> result = service.getMostSoldProductsValueByCustomer(customerId);

		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getRecommendedProductsBySku/{sku}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecommendedProductsBySku(@PathParam("sku") String sku) {

		List<RecommendedProduct> result = service.getRecommendedProductsBySku(sku);

		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getProductsSolrQuery/{search_term}/{filter_by}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductsSolrQuery(@PathParam("search_term") java.lang.String search_term,
			@PathParam("filter_by") java.lang.String filter_by) {

		List<ProductCatalog> result = service.getProductsSolrQuery(search_term, filter_by);

		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getProductsSolrQuery/{search_term}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductsSolrQuery(@PathParam("search_term") java.lang.String search_term) {

		List<ProductCatalog> result = service.getProductsSolrQuery(search_term, null);

		return Response.status(Status.OK).entity(result).build();
	}

	
	@GET
		@Path("/getProductSolrFacets/{search_term}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getProductSolrFacets(@PathParam("search_term") java.lang.String search_term,
				@QueryParam("fc") List<String> facet_columns) {
	
			Map<String, List<ProductCatalogFacet>> result = service.getProductSolrFacets(search_term, facet_columns);
	
			return Response.status(Status.OK).entity(result).build();
		}
	
	
	@GET
	@Path("/getAccessoriesSolrFacets/{search_term}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccessoriesSolrFacets(@PathParam("search_term") java.lang.String search_term,
			@QueryParam("fc") List<String> facet_columns) {

		Map<String, List<ProductAccessoriesFacet>> result = service.getAccessoriesSolrFacets(search_term, facet_columns);

		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getAccessoriesSolrQuery/{search_term}/{filter_by}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccessoriesSolrQuery(@PathParam("search_term") java.lang.String search_term,
			@PathParam("filter_by") java.lang.String filter_by) {

		List<ProductAccessories> result = service.getAccessoriesSolrQuery(search_term, filter_by);

		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getAccessoriesSolrQuery/{search_term}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccessoriesSolrQuery(@PathParam("search_term") java.lang.String search_term) {

		List<ProductAccessories> result = service.getAccessoriesSolrQuery(search_term, null);

		return Response.status(Status.OK).entity(result).build();
	}

	
}
