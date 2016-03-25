package com.datastax.retail.model;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by vincentponcet on 25/03/2016.
 */

@Accessor
public interface OrderAccessor {

    @Query("SELECT * FROM orders where customer_id = :customerId")
    public Result<Order> getAll(@Param("customerId") java.util.UUID customerId);

}
