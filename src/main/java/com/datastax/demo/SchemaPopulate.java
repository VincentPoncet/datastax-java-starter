package com.datastax.demo;

public class SchemaPopulate extends RunCQLFile {

	SchemaPopulate(String cqlFile) {
		super(cqlFile);
	}

	public static void main(String args[]){
		
		SchemaPopulate setup = new SchemaPopulate("cql/test_data.cql");
		setup.internalSetup();
		setup.shutdown();
	}
}
