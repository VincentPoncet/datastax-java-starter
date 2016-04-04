package com.datastax.demo;

import com.datastax.demo.utils.PropertyHelper;

// This is a data generator for Packathon.
// This program accepts a json file generated from the BestBuy API.
// http://bestbuyapis.github.io/bby-query-builder/#/productSearch
// Get a free API key to access the API query builder.
// Match the columns in the product_catalog in the generated json file.
// This program will:
//      load the json. 100 products.
//      generate 100 client UUIDs in a 0-100 range.
//      randomly submit product orders on behalf of the clients.
//      the quantity of products ordered at any one time is between 1-100.
//      this program accepts as arguments: host timeToSleepInMilliseconds pathToJsonFile

import com.datastax.driver.core.*;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.retail.model.ProductCatalog;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;

public class DataGenerator extends RunCQLFile {

	public Integer loadJson(String fileName) {
    
		Session session = getSession();
		
        JSONParser parser = new JSONParser();
        int rowCounter = 0; // Track number of products

        try {
            Object obj = parser.parse(new FileReader(fileName));
            System.out.println("Input file =" + fileName);

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray itemList = (JSONArray) jsonObject.get("products");
            Iterator<String> item = itemList.iterator();
            while (item.hasNext()) {
                Object itemObject = item.next();
                JSONObject itemJson = (JSONObject) itemObject;

                
                Mapper<ProductCatalog> mapper = new MappingManager(session).mapper(ProductCatalog.class);
             
                ProductCatalog pc = new ProductCatalog(
                		(String) itemJson.get("sku").toString(),
                		(String) itemJson.get("color"),
                		(String) itemJson.get("description"),
                		(String) itemJson.get("image"),
                		(Boolean) itemJson.get("inStoreAvailability"),
                		(String) itemJson.get("manufacturer"),
                		(String) itemJson.get("modelNumber"),
                		(String) itemJson.get("name"),
                		(Double) itemJson.get("regularPrice"),
                		(String) itemJson.get("shortDescription"),
                		(String) itemJson.get("thumbnailImage"),
                		(String) itemJson.get("upc")
                );
                mapper.save(pc);

                rowCounter++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("Rows inserted: " + rowCounter);
        return (rowCounter);
    }


    public void placeOrders() {

    	Session session = getSession();
        ResultSet results = session.execute("SELECT sku, name, regular_price FROM retail_ks.product_catalog;");
        int numberOfProducts = results.getAvailableWithoutFetching();
        String[][] productList = new String[numberOfProducts][3];
        // Build product array
        int counter = 0;
        for (Row row : results) {
            productList[counter][0] = row.getString("sku");
            productList[counter][1] = row.getString("name");
            double regular_price = row.getDouble("regular_price");
            productList[counter][2] = Double.toString(regular_price);

            //System.out.println("counter = "+counter+ " : sku ="+productList[counter][0]+ " : name = "+productList[counter][1]+ " : regular_price = "+regular_price );
            counter++;
        }

        int sleepTime = 10;
        System.out.println("Sending orders every " + sleepTime + " milliseconds");
        Random rand = new Random();

        for (int i = 0; i < 100001; i++) {
            if (i % 1000 == 0) {
                System.out.println("Orders placed = " + i);
            }
            // Slow it down
            try {
                Thread.sleep(sleepTime);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }


            // Create a customer. There will be 100.
            int randomC = rand.nextInt((800 - 700) + 1) + 700;
            String randomCustomer = Integer.toString(randomC);
            String stringUUID = "a4a70900-24e1-11df-8924-001ff3591".concat(randomCustomer);
            UUID customer_id = UUID.fromString(stringUUID);
            //System.out.println("Random customer id = " + customer_id);

            // Create a quantity. May be between 1-100.
            int randomQuantity = rand.nextInt(99) + 1;
            if (randomQuantity > 100) {
                randomQuantity = 99;
            }

            // Select a random product
            // Create a quantity. May be between 1-100.
            int randomProduct = rand.nextInt(numberOfProducts) + 1;
            if (randomProduct > 99) {
                randomProduct = 99;
            }
            //System.out.println("Random product number = "+randomProduct);

            // Total price
            double price = Double.parseDouble(productList[randomProduct][2]);
            double total_price = randomQuantity * price;

            // Generate order_id timeuuid
            UUID timeUUID = UUIDs.timeBased();
            //System.out.println("Time UUID = "+timeUUID);

            // Insert order
            ResultSet execute = session.execute("insert into retail_ks.orders ("
                    + "customer_id,"
                    + "order_id,"
                    + "date,"
                    + "order_lines"
                    + ")"
                    + "values("
                    + customer_id + ","
                    + timeUUID + ","
                    + "dateof(now()),"
                    + "[{"
                    + "sku:'" + productList[randomProduct][0] + "',"
                    + "product_name:'" + productList[randomProduct][1] + "',"
                    + "quantity:" + randomQuantity + ","
                    + "unit_price:" + productList[randomProduct][2] + ","
                    + "total_price:" + total_price
                    + "}]"
                    + ");");
        }
    }

    
    DataGenerator (String cqlFile) {
		super(cqlFile);
	}

	
    public static void main(String[] args) {

    	// Setup schema if not exists
    	String datGenCreatDDLPath = PropertyHelper.getProperty("datGenCreatDDLPath","cql/create_cassandra_ddl.cql");
    	DataGenerator client = new DataGenerator(datGenCreatDDLPath);
		client.internalSetup();
		
		
        // How many milliseconds to sleep in between orders
        int sleepTime = 10;
        if (args.length > 1) {
            sleepTime = Integer.parseInt(args[1]);
        }

        // Location of the json file to load
        //String fileName = "src/main/resources/products.json";
        String datGenDataPath = PropertyHelper.getProperty("datGenDataPath","src/main/resources/product.json");
        if (args.length > 2) {
        	datGenDataPath = args[2];
        }

        int numberOfProducts = 0;

        
        numberOfProducts = client.loadJson(datGenDataPath);
        
      
        //System.out.println("Products loaded = " + numberOfProducts);
        client.placeOrders();        
        client.shutdown();

    }
}
