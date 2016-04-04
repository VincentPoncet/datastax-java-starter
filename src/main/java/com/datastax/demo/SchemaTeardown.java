package com.datastax.demo;

import com.datastax.demo.utils.PropertyHelper;

public class SchemaTeardown extends RunCQLFile {

	SchemaTeardown(String cqlFile) {
		super(cqlFile);
	}

	public static void main(String args[]){
		
		String schemTrDwnPath = PropertyHelper.getProperty("schemTrDwnPath","cql/drop_schema.cql");
		SchemaTeardown setup = new SchemaTeardown(schemTrDwnPath);
		setup.internalSetup();
		setup.shutdown();
	}
}
