PackaThon Retail
========================

To get started download this repository. Choose a location to download the project and run the following
```
git clone https://github.com/VincentPoncet/datastax-java-starter
cd datastax-java-starter
rm -rf .git
```
Note : We run the remove so that any git files are deleted.



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


DSE contactpoints can be added at src/main/resources/myProperties.txt





    
    
