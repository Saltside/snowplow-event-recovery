/**
 * troubleshoot
 */
package se.saltside.data.pipeline.troubleshoot.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author brijeshsingh - 23-Aug-2016
 * 
 */
public class EnrichmentRequest extends BaseEnrichmentRequest {

	private static EnrichmentRequest EnrichmentRequestINSTANCE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * se.saltside.data.pipeline.troubleshoot.core.BaseEnrichmentRequest#tsvToArray
	 * (java.lang.String)
	 */
	@Override
	public String[] tsvToArray(String event) {
		return event.split("\t", -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * se.saltside.data.pipeline.troubleshoot.core.BaseEnrichmentRequest#arrayToTsv
	 * (java.lang.String[])
	 */
	@Override
	public String arrayToTsv(String[] eventTsv) {
		return StringUtils.join(Arrays.asList(eventTsv), "\t");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.saltside.data.pipeline.troubleshoot.core.BaseEnrichmentRequest#
	 * parseQuerystring(java.lang.String)
	 */
	@Override
	public Map<String, String> parseQuerystring(String eventQuery)
			throws UnsupportedEncodingException {
		Map<String, String> query = new HashMap<String, String>();
		String[] arr = eventQuery.split("&");
		for (int i = 0; i < arr.length; i++) {
			String[] array = arr[i].split("=");
			if (array[1] == null || array[1] == "") {
				array[1] = "";
			}
			query.put(URLDecoder.decode(array[0], "UTF-8"),
					URLDecoder.decode(array[1], "UTF-8"));
		}
		return query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see se.saltside.data.pipeline.troubleshoot.core.BaseEnrichmentRequest#
	 * buildQuerystring(java.lang.String[])
	 */
	@Override
	public String buildQuerystring(Map<String, String> fields) {
		List<String> parts = new ArrayList<String>();
		for (Map.Entry<String, String> field : fields.entrySet()) {
			parts.add(field.getKey() + "=" + fields.get(field.getKey()));
		}
		return StringUtils.join(parts, "&");
	}
	
	public String process(String jsonObj) throws JSONException, UnsupportedEncodingException{
		JSONObject jsonObject = new JSONObject(jsonObj);
		JSONArray array = new JSONArray(jsonObject.getString("errors"));
		if(new JSONObject(array.get(0).toString()).get("message").toString().contains("is not a supported tracking platform")){
			String[] fields = tsvToArray(jsonObject.getString("line"));
			if(fields[5].equals("GET")){
				Map<String,String> querystringDict = parseQuerystring(fields[11]);
				querystringDict.put("p", "mob");
				fields[11] = buildQuerystring(querystringDict);
			}
			return arrayToTsv(fields);
		}
		return null;
	}
	public static EnrichmentRequest getInstance() {
		if (EnrichmentRequestINSTANCE == null)
			synchronized (EnrichmentRequest.class) {
				if (EnrichmentRequestINSTANCE == null)
					EnrichmentRequestINSTANCE = new EnrichmentRequest();
			}
		return EnrichmentRequestINSTANCE;
	}

}
