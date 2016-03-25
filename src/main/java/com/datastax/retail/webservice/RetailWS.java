package com.datastax.retail.webservice;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.datastax.retail.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.retail.model.SellingProduct;
import com.datastax.retail.service.Service;

@WebService
@Path("/")
public class RetailWS {

	private Logger logger = LoggerFactory.getLogger(RetailWS.class);

	//Service Layer.
	private Service service = new Service();
	
	@GET
	@Path("/getTop50SellingProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTop50SellingProducts() {
				
		List<SellingProduct> result = service.getTop50SellingProducts();
		
		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("/getAllOrdersByCustomer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrder(@PathParam("customerId") java.util.UUID customerId) {

		List<Order> result = service.getAllOrdersByCustomer(customerId);

		return Response.status(Status.OK).entity(result).build();
	}
	
}
