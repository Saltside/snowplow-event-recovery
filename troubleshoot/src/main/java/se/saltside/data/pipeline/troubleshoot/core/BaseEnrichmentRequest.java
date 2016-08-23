/**
 * troubleshoot
 */
package se.saltside.data.pipeline.troubleshoot.core;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @author brijeshsingh - 23-Aug-2016
 * 
 */
public abstract class BaseEnrichmentRequest {
	
	public abstract String[] tsvToArray(String event);
	public abstract String arrayToTsv(String[] event);
	public abstract Map<String, String> parseQuerystring(String eventQuery) throws UnsupportedEncodingException;
	public abstract String buildQuerystring(Map<String, String> fields);
	
	public Map<String,String> parseJSON(String jsonString){
		return null;
	}
	public String stringifyJson(Map<String,String> jsonObject){
		return new Gson().toJson(jsonObject);
	}
	
}

