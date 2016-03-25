package com.datastax.retail.model;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by vincentponcet on 25/03/2016.
 */

@Accessor
public interface SellingProductAccessor {

    @Query("SELECT * FROM top50_selling_products")
    public Result<SellingProduct> getAll();

    @Query("SELECT * FROM top50_selling_products")
    public ListenableFuture<Result<SellingProduct>> getAllAsync();
}
