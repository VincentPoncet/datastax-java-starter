/**
  * Created by vincentponcet on 14/04/2016.
  */
package com.datastax.retail

import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}


object Spark_Retail_Top50_Recommendations {

  def main(args: Array[String]) {


    val conf = new SparkConf(true)
      .setMaster("spark://172.16.123.1:7077")
      .setAppName("cassandra-demo")
      .set("cassandra.connection.host", "172.16.123.1") // initial contact
      .set("cassandra.username", "cassandra")
      .set("cassandra.password", "cassandra")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.hive.HiveContext(sc)
    import org.apache.spark.storage.StorageLevel._
    import sqlContext.implicits._

    val orders = sc.cassandraTable[Order]("retail_ks", "orders").persist(MEMORY_AND_DISK)
    val orderlines = orders.flatMap(order => order.OrderLines_).map(ol => (ol.sku, (ol.productName, ol.thumbnailImage, ol.quantity, ol.unitPrice, ol.totalPrice)))
    val soldproducts = orderlines.reduceByKey((a, b) => (a._1, a._2, a._3 + b._3, a._4, a._5 + b._5)).
      map({ case (sku, (productName, thumbnailImage, count, unitPrice, value)) => (sku, productName, thumbnailImage, count, value) })
    val Top50CountSellingProducts = soldproducts.sortBy({ case (sku, productName, thumbnailImage, count, value) => -value }).
      zipWithIndex.
      filter { case (_, idx) => idx < 50 }.
      keys

    Top50CountSellingProducts.saveToCassandra("retail_ks", "top50_selling_products",
      SomeColumns("sku", "product_name", "thumbnail_image", "sale_count", "sale_value"))


    val ProductCoOccurance = orders.
      flatMap(order => order.OrderLines_.
        map(ol => (ol.sku, order.OrderLines_.
          map(ol => (ol.sku, (ol.productName, ol.thumbnailImage, ol.quantity, ol.unitPrice, ol.totalPrice))).filter(ol3 => ol3._1 != `ol`.sku)
          ) // for each order, make a list of product cooccurance (product1, product2)
        )
      ).reduceByKey { (a, b) =>
      // merge the list per product1
      val mergedBySku = (a ++ b)
      // for each product1, merge list on key product2
      val groupedBySku = mergedBySku.groupBy({ case (sku, (pn, ti, q, up, tp)) => sku }).values.toList
      // aggregate value sum of product2
      groupedBySku.map(listOfProduct => listOfProduct.reduce((a, b) => (a._1, (a._2._1, a._2._2, a._2._3 + b._2._3, a._2._4, a._2._5 + b._2._5))))
    }.
      mapValues { TotalSumBySku =>
        // take top50 product2 sorted on summed value
        val Top50Value = TotalSumBySku.sortBy(-_._2._5).slice(0, 50)
        // create a RecommendedProduct item to fit table structure
        Top50Value.map(lop => RecommendedProduct(lop._1, lop._2._1, lop._2._4, lop._2._2))
      }.
      // create a ProductRecommendation to fit table structure
      map(pco => ProductRecommendations(pco._1, "", pco._2))


    ProductCoOccurance.toDF.write.format("org.apache.spark.sql.cassandra").
      options(Map("table" -> "product_recommendations", "keyspace" -> "retail_ks")).
      mode("overwrite").
      save()
  }

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
                                 product_name: String,
                                 regular_price: Double,
                                 thumbnail_image: String
                               )

  case class ProductRecommendations(
                                     sku: String,
                                     product_name: String,
                                     recommended_products: List[RecommendedProduct]
                                   )
}
