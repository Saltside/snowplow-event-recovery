/**
 * troubleshoot
 */
package se.saltside.data.pipeline.troubleshoot.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonParser;

import se.saltside.data.pipeline.troubleshoot.FileUtils;


/**
 * @author brijeshsingh - 23-Aug-2016
 * 
 */
public class RecoveryProcessor implements Processor<EnrichmentRequest> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * se.saltside.data.pipeline.troubleshoot.core.Processor#execute(se.saltside
	 * .data.pipeline.troubleshoot.core.BaseEnrichmentRequest)
	 */
	@Override
	public void execute(EnrichmentRequest request) {
//		String json = "{\"line\":\"2016-08-01\\t02:37:25\\t-\\t43\\t52.18.246.214\\tGET\\t162.158.38.96\\t/i\\t200\\t-\\t-\\te=pv&duid=18cb79a6-6402-450c-8639-28115e965160&ip=172.17.0.1&ua=Opera%2F9.80%20%28SpreadTrum%3B%20Opera%20Mini%2F4.4.32739%2F37.8773%3B%20U%3B%20en%29%20Presto%2F2.12.423%20Version%2F12.16&lang=en&url=http%3A%2F%2Fefritin.com%2Fen%2Fad%2Fleap-2-black-original-for-sale-lagos&p=xs&tna=Efritin.com&aid=EFRITIN_NOJS&se_pr=578df57bc1465700010d94db&refr=http%3A%2F%2Fefritin.com%2Fen%2Fads%2Fmobile-phones-in-nigeria-5030100%3Futm_medium%3Dfacebook_cpc%26utm_source%3Dfacebook_ad%26utm_campaign%3DGen_Mobile_Featured_Phone%26utm_content%3DFeatured%2BPhone%2BNigeria_%2BMobile_interest%26utm_id%3D56efd50a58e7ab8c508b45c3%26page%3D20&cv=clj-1.1.0-tom-0.2.0&nuid=349fe3e1-cce3-4804-beec-cf054f6e49c2\\t-\\t-\\t-\\t-\\t-\",\"errors\":[{\"level\":\"error\",\"message\":\"Field [p]: [xs] is not a supported tracking platform\"}],\"failure_tstamp\":\"2016-08-01T04:13:10.855Z\"}";
		File[] files= FileUtils.readfile();
		int i = 0;
		for (File file : files) {
			if (file.isFile() && file.getName().startsWith("run")
					&& !file.getName().contains("_SUCCESS")) {
				try {
					ArrayList<JSONObject> jsons=readJSON(file,"UTF-8");
					List<String> objects = new ArrayList<String>();
					for (JSONObject jsonObject : jsons) {
						objects.add(request.process(jsonObject.toJSONString()));
					}
					FileUtils.write(objects,file.getName());
					jsons.clear();
					objects.clear();
					System.out.println(i++ + " File Compleated");
				} catch (ParseException | FileNotFoundException | UnsupportedEncodingException | JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param file
	 * @param string
	 * @return
	 * @throws FileNotFoundException 
	 * @throws ParseException 
	 */
	private static synchronized ArrayList<JSONObject> readJSON(File file, String encoding) throws FileNotFoundException, ParseException {
		
		@SuppressWarnings("resource")
		Scanner scn=new Scanner(file,encoding);
	    ArrayList<JSONObject> json=new ArrayList<JSONObject>();
	    while(scn.hasNext()){
	        JSONObject obj= (JSONObject) new JSONParser().parse(scn.nextLine());
	        json.add(obj);
	    }
	    return json;
	}

}
