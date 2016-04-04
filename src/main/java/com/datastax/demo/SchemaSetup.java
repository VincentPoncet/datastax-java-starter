package com.datastax.demo;

import com.datastax.demo.utils.PropertyHelper;

public class SchemaSetup extends RunCQLFile {

	SchemaSetup(String cqlFile) {
		super(cqlFile);
	}

	public static void main(String args[]){
		
		//String schemSetDDLPath = PropertyHelper.getProperty("schemSetDDLPath","cql/create_schema.cql");
		String schemSetDDLPath = PropertyHelper.getProperty("datGenCreatDDLPath","cql/create_schema.cql");
		SchemaSetup setup = new SchemaSetup(schemSetDDLPath);
		setup.internalSetup();
		setup.shutdown();
	}
}
