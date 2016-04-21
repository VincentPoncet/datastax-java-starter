val orders = sc.cassandraTable[Order]("retail_ks", "orders").cache
val orderlines = orders.flatMap(order => order.OrderLines_).map(ol => (ol.sku, (ol.productName, ol.thumbnailImage, ol.quantity, ol.unitPrice, ol.totalPrice)))
val soldproducts = orderlines.reduceByKey((a, b) => (a._1, a._2, a._3 + b._3, a._4, a._5 + b._5)).
  map({ case (sku, (productName, thumbnailImage, count, unitPrice, value)) => (sku, productName, thumbnailImage, count, value) })
val Top50CountSellingProducts = soldproducts.sortBy({ case (sku, productName, thumbnailImage, count, value) => -value }).
  zipWithIndex.
  filter { case (_, idx) => idx < 50 }.
  keys
val ProductCoOccurance = orders.
  flatMap(order => order.OrderLines_.
    map(ol => (ol.sku, order.OrderLines_.
      map(ol => (ol.sku, (ol.productName, ol.thumbnailImage, ol.quantity, ol.unitPrice, ol.totalPrice))).filter(ol3 => ol3._1 != `ol`.sku)
      )
    )
  ).reduceByKey(_ ++ _).mapValues(
  _.groupBy({ case (sku, (pn, ti, q, up, tp)) => sku }).values.toList.
    map(listOfProduct => listOfProduct.reduce((a, b) => (a._1, (a._2._1, a._2._2, a._2._3 + b._2._3, a._2._4, a._2._5 + b._2._5)))).
    sortBy(-_._2._5).
    map(lop => RecommendedProduct(lop._1, lop._2._1, lop._2._4, lop._2._2))
).
  map(pco => ProductRecommendations(pco._1, "", pco._2))

case class OrderLine(
                      sku: String,
                      productName: String,
                      thumbnailImage: String,
                      quantity: Double,
                      unitPrice: Double,
                      totalPrice: Double
                    )

case class Order(
                  customerId: java.util.UUID,
                  orderId: java.util.UUID,
                  date: java.util.Date,
                  OrderLines_ : List[OrderLine],
                  totalPrice: Double
                )

case class RecommendedProduct(
                               sku: String,
                               productName: String,
                               regularPrice: Double,
                               thumbnailImage: String
                             )

/**
  * Created by vincentponcet on 18/04/2016.
  */



Top50CountSellingProducts.saveToCassandra("retail_ks", "top50_selling_products",
  SomeColumns("sku", "product_name", "thumbnail_image", "sale_count", "sale_value"))

case class ProductRecommendations(
                                   sku: String,
                                   productName: String,
                                   recommendedProducts: List[RecommendedProduct]
                                 )


ProductCoOccurance.saveToCassandra("retail_ks", "product_recommendations")

exit