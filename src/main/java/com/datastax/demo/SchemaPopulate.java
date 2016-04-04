package com.datastax.demo;

import com.datastax.demo.utils.PropertyHelper;

public class SchemaPopulate extends RunCQLFile {

	SchemaPopulate(String cqlFile) {
		super(cqlFile);
	}

	public static void main(String args[]){
		String schemPopDataPath = PropertyHelper.getProperty("schemPopDataPath","cql/test_data.cql");
		SchemaPopulate setup = new SchemaPopulate(schemPopDataPath);
		
	    
		setup.internalSetup();
		setup.shutdown();
	}
}
