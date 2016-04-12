package com.datastax.retail.model;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.google.common.util.concurrent.ListenableFuture;

@Accessor
public interface ProductAccessoriesAccessor {

	 @Query("SELECT * FROM product_accessories WHERE solr_query= :solr_query limit 100")
	    public Result<ProductAccessories> getAll(@Param("solr_query") java.lang.String solr_query);
	 
	 @Query("SELECT * FROM product_accessories WHERE solr_query= :solr_query limit 100")
	 public ListenableFuture<Result<ProductAccessories>> getAllAsync(@Param("solr_query") java.lang.String solr_query);

}
