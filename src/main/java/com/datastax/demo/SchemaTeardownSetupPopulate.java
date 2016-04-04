package com.datastax.demo;

import com.datastax.demo.utils.PropertyHelper;

public class SchemaTeardownSetupPopulate extends RunCQLFile {

	SchemaTeardownSetupPopulate(String cqlFile) {
		super(cqlFile);
	}

	public static void main(String args[]){

		String schemTrDwnPath = PropertyHelper.getProperty("schemTrDwnPath","cql/drop_schema.cql");
		SchemaTeardown setup1 = new SchemaTeardown(schemTrDwnPath);
		setup1.internalSetup();
		setup1.shutdown();

		String schemSetDDLPath = PropertyHelper.getProperty("schemSetDDLPath","cql/create_schema.cql");
		SchemaSetup setup2 = new SchemaSetup(schemSetDDLPath);
		setup2.internalSetup();
		setup2.shutdown();

		String schemPopDataPath = PropertyHelper.getProperty("schemPopDataPath","cql/test_data.cql");
		SchemaPopulate setup3 = new SchemaPopulate(schemPopDataPath);
		setup3.internalSetup();
		setup3.shutdown();
	}
}
