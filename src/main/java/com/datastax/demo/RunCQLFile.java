package com.datastax.demo;

import com.datastax.demo.utils.FileUtils;
import com.datastax.demo.utils.PropertyHelper;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunCQLFile {

	static String CREATE_KEYSPACE;
	static String DROP_KEYSPACE;
	private static Logger logger = LoggerFactory.getLogger(RunCQLFile.class);
	private static Session session;
	private Cluster cluster;
	private String CQL_FILE;

	RunCQLFile(String cqlFile) {
		
		logger.info("Running file " + cqlFile);
		this.CQL_FILE = cqlFile;
	
		String contactPointsStr = PropertyHelper.getProperty("contactPoints","127.0.0.1");

		cluster = Cluster.builder().addContactPoints(contactPointsStr.split(",")).build();
		session = cluster.connect();
	}
	
	void internalSetup() {
		this.runfile();		
	}
	
	protected Session getSession(){
		return session;
	}
	
	void runfile() {
		String readFileIntoString = FileUtils.readFileIntoString(CQL_FILE);
		
		String[] commands = readFileIntoString.split(";");
		
		for (String command : commands){
			
			String cql = command.trim();
			
			if (cql.isEmpty()){
				continue;
			}
			
			if (cql.toLowerCase().startsWith("drop")){
				this.runAllowFail(cql);
			}else{
				this.run(cql);
			}			
		}
	}

	void runAllowFail(String cql) {
		try {
			run(cql);
		} catch (InvalidQueryException e) {
			logger.warn("Ignoring exception - " + e.getMessage());
		}
	}

	void run(String cql){
		logger.info("Running : " + cql);
		if (!(cql.startsWith("--") || cql.startsWith("//")))
			session.execute(cql);
	}

	void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (Exception e) {
		}
	}

	
	void shutdown() {
		session.close();
		cluster.close();
	}
}
