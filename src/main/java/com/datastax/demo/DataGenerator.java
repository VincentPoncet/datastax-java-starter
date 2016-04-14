package com.datastax.demo;

import com.datastax.demo.utils.PropertyHelper;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.retail.model.ProductAccessories;
import com.datastax.retail.model.ProductCatalog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

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

public class DataGenerator {

	private static Cluster cluster;
	private static Session session;

	
	private static Integer  loadJson(String fileName, String table) {
    		
        JSONParser parser = new JSONParser();
        int rowCounter = 0; // Track number of products

        try {
            Object obj = parser.parse(new FileReader(fileName));
            System.out.println("Input file =" + fileName);
            System.out.println("Table =" + table);


            JSONObject jsonObject = (JSONObject) obj;
            JSONArray itemList = (JSONArray) jsonObject.get("products");
            Iterator<String> item = itemList.iterator();
            while (item.hasNext()) {
                Object itemObject = item.next();
                JSONObject itemJson = (JSONObject) itemObject;

                
               
             
                if(table.equals("product_catalog")){
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
                } else{
                	 Mapper<ProductAccessories> mapper = new MappingManager(session).mapper(ProductAccessories.class);
                	ProductAccessories ac = new ProductAccessories(
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
                	                	mapper.save(ac);
                }
                
                

                rowCounter++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("Rows inserted: " + rowCounter);
        return (rowCounter);
    }


	public static void placeOrders() {

        // Process Product table
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
            counter++;
        }

        // Process Accessories table
        ResultSet results2 = session.execute("SELECT sku, name, regular_price FROM retail_ks.product_accessories;");
        int numberOfProducts2 = results2.getAvailableWithoutFetching();
        String[][] productList2 = new String[numberOfProducts2][3];
        // Build product array
        int counter2 = 0;
        for (Row row2 : results2) {
            productList2[counter2][0] = row2.getString("sku");
            productList2[counter2][1] = row2.getString("name");
            double regular_price2 = row2.getDouble("regular_price");
            productList2[counter2][2] = Double.toString(regular_price2);
            counter2++;
        }

        int sleepTime = 10;
        System.out.println("Sending orders every " + sleepTime + " milliseconds");
        Random rand = new Random();

        // --------------------
        // Place main order
        // --------------------


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
            int randomProduct = rand.nextInt(numberOfProducts);
            //System.out.println("Random product number = "+randomProduct);

            // Total price
            double price = Double.parseDouble(productList[randomProduct][2]);
            double total_price = randomQuantity * price;

            // Build out product order
            String itemsToAddToCart = "{" +
                    "sku:'" + productList[randomProduct][0] + "',"
                    + "product_name:'" + productList[randomProduct][1] + "',"
                    + "quantity:" + randomQuantity + ","
                    + "unit_price:" + productList[randomProduct][2] + ","
                    + "total_price:" + total_price
                   + "}";

            // Track grand total
            double grandTotal = total_price;

            // --------------------
            // Get accessories now
            // --------------------

            // Create a quantity for accessories. May be between 1-10.
            int randomQuantity2 = rand.nextInt(10);
            //System.out.println("randomQuantity2 = "+randomQuantity2);


            // Build out the accessory order

            for (int i2 = 0; i2 < randomQuantity2; i2++) {

                int randomProduct2 = rand.nextInt(numberOfProducts2);
                // System.out.println("randomProduct2 = "+randomProduct2);

                // Total price
                double price2 = Double.parseDouble(productList2[randomProduct2][2]);
                double total_price2 = randomQuantity2 * price2;


                itemsToAddToCart = itemsToAddToCart + ",{" +
                        "sku:'" + productList2[randomProduct2][0] + "',"
                        + "product_name:'" + productList2[randomProduct2][1].replaceAll("\"","\\\"").replaceAll("'","`") + "',"
                        + "quantity:" + randomQuantity2 + ","
                        + "unit_price:" + productList2[randomProduct2][2] + ","
                        + "total_price:" + total_price2
                        + "}";

                grandTotal = grandTotal + total_price2;
            }


            String insertOrder = "insert into retail_ks.orders ("
                    + "customer_id,"
                    + "order_id,"
                    + "date,"
                    + "order_lines_,"
                    + "total_price"
                    + ")"
                    + "values("
                    + customer_id + ","
                    + UUIDs.timeBased() + ","
                    + "dateof(now()),"
                    + "[ "
                    + itemsToAddToCart
                    + " ],"
                    + grandTotal
                    + ");";

            //System.out.println(insertValue);
            session.execute(insertOrder);
        }
    }

	
    public static void main(String[] args) {

    	// init cluster/session
		String contactPointsStr = PropertyHelper.getProperty("contactPoints","127.0.0.1");
    	cluster = Cluster.builder().addContactPoints(contactPointsStr.split(",")).build();
		session = cluster.connect();
		
    	
        // How many milliseconds to sleep in between orders
        int sleepTime = 10;
        if (args.length > 1) {
            sleepTime = Integer.parseInt(args[1]);
        }

        // Location of the json file to load
        String datGenDataPath = PropertyHelper.getProperty("datGenProdDataPath", "src/Dat/resources/dataGeneratorData/products.json");
        if (args.length > 2) {
        	datGenDataPath = args[2];
        }

        int numberOfProducts = 0;
        int numberOfAccessories = 0;


        
        numberOfProducts = loadJson(datGenDataPath,"product_catalog");

     // Location of the json file to load
        datGenDataPath = PropertyHelper.getProperty("datGenAccDataPath", "src/main/resources/dataGeneratorData/accessories.json");
        if (args.length > 3) {
        	datGenDataPath = args[3];
        }
        
       
        numberOfAccessories = loadJson(datGenDataPath,"product_accessories");
        
        System.out.println("Products loaded = " + numberOfProducts);
        System.out.println("Accessories loaded = " + numberOfAccessories);
        
        
      
       placeOrders();        
        
        session.close();
		cluster.close();

    }
}
