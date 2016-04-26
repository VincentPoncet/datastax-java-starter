PackaThon Retail
========================

To get started download this repository. Choose a location to download the project and run the following
```
git clone https://github.com/VincentPoncet/datastax-java-starter
cd datastax-java-starter
rm -rf .git
```
Note : We run the remove so that any git files are deleted.


Note : DSE contactpoints can be added at src/main/resources/myProperties.txt
	   Location of source files for DataGenerator are added to src/main/resources/myProperties.txt



To create the schema, run the following

	mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaSetup" -DcontactPoints=localhost

To populate the schema with test data, run the following

	mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaPopulate" -DcontactPoints=localhost
	
	
To load data with the DataGenerator
	
	mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.DataGenerator"
	
	Make sure you have the path in myProperties.txt file eg src/main/resources/product.json.
	Location of the file can be changed in src/main/resources/myProperties.txt

To create the Solr Core 

	dsetool create_core retail_ks.product_catalog generateResources=true reindex=true
	dsetool create_core retail_ks.product_accessories generateResources=true reindex=true


	
To start the web server run 

	mvn jetty:run


To remove the tables and the schema, run the following.

    mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaTeardown"

To see all orders of one customer

	http://localhost:8080/datastax-starter/rest/getAllOrdersByCustomer/a4a70900-24e1-11df-8924-001ff3591711

To see most sold products of one customer

    http://localhost:8080/datastax-starter/rest/getMostSoldProductsCountByCustomer/a4a70900-24e1-11df-8924-001ff3591711
    http://localhost:8080/datastax-starter/rest/getMostSoldProductsValueByCustomer/a4a70900-24e1-11df-8924-001ff3591711

To see Top50 Selling Products

    http://localhost:8080/datastax-starter/rest/getTop50CountSellingProducts
    http://localhost:8080/datastax-starter/rest/getTop50ValueSellingProducts
    
To see Recommendations for one Product

    http://localhost:8080/datastax-starter/rest/getRecommendedProductsBySku/a4a70900-24e1-11df-8924-001ff3591712

To run Spark Top50MostSoldProduct and ProductRecommendations

     dse spark -i src/main/scala/datastaxretailspark.spark-shell



#### Solr Queries 

*getProductsSolrQuery*   
*getAccessoriesSolrQuery*

**Search for product/accessories with Solr Query w/o facet**

*Parameter1: query (q)*

	http://localhost:8080/datastax-starter/rest/getProductsSolrQuery/name:Samsung
	
	http://localhost:8080/datastax-starter/rest/getAccessoriesSolrQuery/sku:1232778
	
*Output:*

	   [{"sku":"9481034","color":"Black","description":null,"image":"http://images.bestbuy.com/BestBuy_US/images/pac/products/1313/1313487731/1313487731_sa.jpg","in_store_availability":true,"manufacturer":"Samsung","model_number":"UN48J5000AFXZA","name":"Samsung - 48\" Class (47.6\" Diag.) - LED - 1080p - HDTV - Black","regular_price":499.99,"short_description":"1080p resolutionMotion Rate 60","thumbnail_image":"http://images.bestbuy.com/BestBuy_US/images/pac/products/1313/1313487731/1313487731_s.gif","upc":"887276069173"}]
	
  * Wild Cards are possible
  
	http://localhost:8080/datastax-starter/rest/getProductsSolrQuery/name:*ha*
	
	http://localhost:8080/datastax-starter/rest/getAccessoriesSolrQuery/sku:12*
	
*Output:*

	[{"sku":"2351018","color":"Black","description":null,"image":"http://images.bestbuy.com/BestBuy_US/images/products/2351/2351018_sa.jpg","in_store_availability":true,"manufacturer":"Sharp","model_number":"LC-32LB370U","name":"Sharp - 32\" Class (31.5\" Diag.) - LED - 1080p - HDTV - Black","regular_price":229.99,"short_description":"Only at Best Buy1080p resolution60Hz refresh rate","thumbnail_image":"http://images.bestbuy.com/BestBuy_US/images/products/2351/2351018_s.gif","upc":"600603185625"}]
	

*Parameter2: filter_query (fq)*
	
	http://localhost:8080/datastax-starter/rest/getProductsSolrQuery/name:Samsung/in_store_availability:true
	
	http://localhost:8080/datastax-starter/rest/getAccessoriesSolrQuery/sku:12*/in_store_availability:true

*Output:*

	[{"sku":"9481034","color":"Black","description":null,"image":"http://images.bestbuy.com/BestBuy_US/images/pac/products/1313/1313487731/1313487731_sa.jpg","in_store_availability":true,"manufacturer":"Samsung","model_number":"UN48J5000AFXZA","name":"Samsung - 48\" Class (47.6\" Diag.) - LED - 1080p - HDTV - Black","regular_price":499.99,"short_description":"1080p resolutionMotion Rate 60","thumbnail_image":"http://images.bestbuy.com/BestBuy_US/images/pac/products/1313/1313487731/1313487731_s.gif","upc":"887276069173"}


###### Examples for advanced filter_query

1. filter_query AND filter_query
2. filter_query OR filter_query
3. filter_query%20OR%20filter_query
4. filter_query%20AND%20filter_query

	http://localhost:8080/datastax-starter/rest/getProductsSolrQuery/*:*/name:Sa*%20OR%20in_store_availability:true
	
	http://localhost:8080/datastax-starter/rest/getAccessoriesSolrQuery/*:*/sku:12*%20AND%20in_store_availability:true


*Output*

	[{"sku":"1729549","color":"Black","description":null,"image":"http://images.bestbuy.com/BestBuy_US/images/products/1729/1729549_sa.jpg","in_store_availability":true,"manufacturer":"Insigniaâ„¢","model_number":"NS-40D420NA16","name":"Insigniaâ„¢ - 40\" Class (40\" Diag.) - LED - 1080p - HDTV - Black","regular_price":279.99,"short_description":"1080p resolution60Hz refresh rate","thumbnail_image":"http://images.bestbuy.com/BestBuy_US/images/products/1729/1729549_s.gif","upc":"600603185021"}


** Example for Solr Facet Query **

Parameter1: query (q)   
parameter2: facet_query   
Concatenation with "&"

	
	http://localhost:8080/datastax-starter/rest/getProductSolrFacets/*:*?fc=name&fc=manufacturer
	
	http://localhost:8080/datastax-starter/rest/getAccessoriesSolrFacets/*:*?fc=name&fc=manufacturer

*Output*
	
	{"name":[{"name":"diag","amount":100},{"name":"class","amount":100},{"name":"black","amount":100},{"name":"led","amount":97},{"name":"smart","amount":72}],"manufacturer":[{"name":"samsung","amount":43},{"name":"lg","amount":23},{"name":"insignia","amount":21},{"name":"sharp","amount":8},{"name":"sony","amount":3}]}
  

In front end you need to call both queries in order to get results and facets together.
There is no singleton call for all the data in one JSON.
    
    
