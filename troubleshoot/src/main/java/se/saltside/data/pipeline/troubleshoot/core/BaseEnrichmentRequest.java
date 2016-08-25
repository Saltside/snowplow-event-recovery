/**
 * troubleshoot
 */
package se.saltside.data.pipeline.troubleshoot.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @author brijeshsingh - 23-Aug-2016
 * 
 */
public abstract class BaseEnrichmentRequest {

	public abstract String[] tsvToArray(String event);

	public abstract String arrayToTsv(String[] event);

	public abstract Map<String, String> parseQuerystring(String eventQuery)
			throws UnsupportedEncodingException;

	public abstract String buildQuerystring(Map<String, String> fields)
			throws UnsupportedEncodingException;

	public Map<String, String> parseJSON(String jsonString) {
		return null;
	}

	public String stringifyJson(Map<String, String> jsonObject) {
		return new Gson().toJson(jsonObject);
	}

	public String encodeBase64(String unencodedString)
			throws UnsupportedEncodingException {

		return URLEncoder.encode(unencodedString, "UTF-8");
	}

	public String decodeBase64(String encodedString)
			throws UnsupportedEncodingException {
		return URLDecoder.decode(encodedString, "UTF-8");
	}
}
