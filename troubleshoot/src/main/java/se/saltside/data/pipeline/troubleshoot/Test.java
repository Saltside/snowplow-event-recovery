/**
 * troubleshoot
 */
package se.saltside.data.pipeline.troubleshoot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author brijeshsingh - 23-Aug-2016
 * 
 */
public class Test {

	public static void main(String[] args) throws IOException {
		String FileName = "/home/brijeshsingh/Desktop/test/badFile/run=2016-08-02-04-03-21-part-00008 (copy)";
		try {
			ArrayList<JSONObject> jsons = readJSON(new File(FileName), "UTF-8");
			for (JSONObject jsonObject : jsons) {
				System.out.println(jsonObject.toJSONString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param file
	 * @param string
	 * @return
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	private static synchronized ArrayList<JSONObject> readJSON(File MyFile,
			String Encoding) throws FileNotFoundException, ParseException {

		@SuppressWarnings("resource")
		Scanner scn = new Scanner(MyFile, Encoding);
		ArrayList<JSONObject> json = new ArrayList<JSONObject>();
		while (scn.hasNext()) {
			JSONObject obj = (JSONObject) new JSONParser()
					.parse(scn.nextLine());
			json.add(obj);
		}
		return json;
	}

}
