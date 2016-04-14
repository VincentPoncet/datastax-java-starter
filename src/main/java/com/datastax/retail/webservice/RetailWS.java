package com.datastax.retail.webservice;

import com.datastax.retail.model.Order;
import com.datastax.retail.model.RecommendedProduct;
import com.datastax.retail.model.SellingProduct;
import com.datastax.retail.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@WebService
@Path("/")
public class RetailWS {

	private Logger logger = LoggerFactory.getLogger(RetailWS.class);

	//Service Layer.
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


	
}
