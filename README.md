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
	
This will print out a description of all the keyspaces in the cluster using a the sample service. 
	
To start the web server run 

	mvn jetty:run

To use the webservice, go to the folowing url

	http://localhost:8080/datastax-starter/rest/get/keyspaces
	
This will return a json representation of the keyspaces in the cluster using a the sample service.	

To remove the tables and the schema, run the following.

    mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaTeardown"

Use /resources/cql/test_data.cql to populate some tables.
then play
http://localhost:8080/datastax-starter/rest/getAllOrdersByCustomer/a4a70900-24e1-11df-8924-001ff3591711

http://localhost:8080/datastax-starter/rest/getTop50SellingProducts

warning : com.datastax.retail.service has IP in the code, change it to localhost.





    
    
