package com.datastax.demo.utils;

import java.util.List;

public class SolrHelper {

	/**
	 * Create a new cassandra Cluster() and Session(). Returns the Session.
	 *
	 * @return A new Cassandra session
	 */

	public static String makeSolrQueryString(String search_term, String filter_by) {
		String solr_query = "\"q\":\"" + escapeQuotes(search_term) + "\"";

		if (filter_by != null && !filter_by.isEmpty()) {
			solr_query += ",\"fq\":\"" + escapeQuotes(filter_by) + "\"";
		}
		return solr_query;
	}

	private static String escapeQuotes(String s) {
		if (s == null) {
			return null;
		}
		return s.replace("\"", "\\\"");
	}

	public static String makeSolrFacetString(String search_term, List<String> facet_columns) {
		String solr_query = SolrHelper.makeSolrQueryString(search_term, null);

		StringBuilder facetStatement = new StringBuilder().append("'{").append(solr_query)
				.append(",\"facet\":{\"field\":[");

		// The mincount parameter limits the facets the the ones that have at
		// least one result
		String comma = "";
		for (String column : facet_columns) {
			facetStatement.append(comma).append("\"").append(column).append("\"");
			comma = ",";
		}
		facetStatement.append("],\"mincount\":1}}'");

		return facetStatement.toString();
	}

}
