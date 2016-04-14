package com.datastax.retail.model;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface ProductCatalogAccessor {

	 @Query("SELECT * FROM product_catalog WHERE solr_query= :solr_query limit 100")
	    public Result<ProductCatalog> getAll(@Param("solr_query") java.lang.String solr_query);

}
