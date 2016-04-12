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





#### Solr Queries 

**Search for product with Solr Query w/o facet**
*Parameter1: query (q)*
   


	http://localhost:8080/datastax-starter/rest/getProductsSolrQuery/name:Samsung
	
  * Wild Cards are possible
	http://localhost:8080/datastax-starter/rest/getProductsSolrQuery/name:*ha*
	
*Parameter2: filter_query (fq)*
	
	http://localhost:8080/datastax-starter/rest/getProductsSolrQuery/name:Samsung/in_store_availability:true


###### Examples for advanced filter_query

1. filter_query AND filter_query
2. filter_query OR filter_query
3. filter_query%20OR%20filter_query
4. filter_query%20AND%20filter_query

	http://localhost:8080/datastax-starter/rest/getProductsSolrQuery/*:*/name:Sa*%20OR%20in_store_availability:true


** Example for Solr Facet Query **

Parameter1: query (q)
Parameter2: facet_query
Concatenation with "&"
	
	http://localhost:8080/datastax-starter/rest/getProductSolrFacets/*:*?fc=name&fc=manufacturer


In front end you need to call both queries in order to get results and facets together.
There is no singleton call for all the data in one JSON.
    
    
