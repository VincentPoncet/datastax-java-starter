package com.datastax.demo.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyHelper {

	private static Properties p = null;

	protected PropertyHelper() {
		// set up new properties object from file "myProperties.txt"
		try (FileInputStream propFile = new FileInputStream("src/main/resources/myProperties.txt");) {
			p = new Properties();
			p.load(propFile);
			p.putAll(System.getProperties());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// set the system properties
		System.setProperties(p);

		// display new properties
		// System.getProperties().list(System.out);
	}

	private static void getInstance() throws Exception {
		if (p == null) {
			new PropertyHelper();
		}
	}

	public static String getProperty(String name, String defaultValue) {
		try {
			getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return System.getProperty(name) == null ? defaultValue : System.getProperty(name);
	}
}
