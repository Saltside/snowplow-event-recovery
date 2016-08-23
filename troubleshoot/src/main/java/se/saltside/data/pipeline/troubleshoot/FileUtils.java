/**
 * troubleshoot
 */
package se.saltside.data.pipeline.troubleshoot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author brijeshsingh - 21-Aug-2016
 * 
 */
public class FileUtils {

	public static File[] readfile() {
		File folder = new File(
				"/home/brijeshsingh/Desktop/test/badFile/");
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;
	}

	/**
	 * @param listOfFolderPath
	 * @param badInputFolder
	 * @param badOutPutFolderAfterRename
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void process(String listOfFolderPath, String badInputFolder,
			String badOutPutFolderAfterRename) throws FileNotFoundException,
			IOException {
		File file = new File(listOfFolderPath);
		FileReader reader = new FileReader(file);
		List<String> listForFolder = readFromFile(reader);
		if (listForFolder.size() > 0) {
			for (String folderName : listForFolder) {
				renameFile(folderName, badInputFolder,
						badOutPutFolderAfterRename);
			}
		}
	}

	/**
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	private static List<String> readFromFile(FileReader reader)
			throws IOException {
		BufferedReader br = new BufferedReader(reader);
		List<String> listOfFolder = new ArrayList<String>();
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(",");
				line = br.readLine();
			}
			listOfFolder = prepareFloderList(sb.toString());
		} finally {
			br.close();
		}
		return listOfFolder;
	}

	/**
	 * @param string
	 * @return
	 */
	private static List<String> prepareFloderList(String folders) {
		String[] arr = folders.split(",");
		List<String> listOfFolder = new ArrayList<String>();
		for (String folder : arr) {
			listOfFolder.add(folder);
		}
		return listOfFolder;
	}

	/**
	 * @param badOutPutFolderAfterRename
	 * @param badInputFolder
	 * 
	 */
	private static void renameFile(String folderName, String badInputFolder,
			String badOutPutFolderAfterRename) {
		String absolutePath = badInputFolder + folderName;
		File dir = new File(absolutePath);
		File[] filesInDir = dir.listFiles();
		for (File file : filesInDir) {
			String newName = folderName + "-" + file.getName();
			String newPath = badOutPutFolderAfterRename;
			file.renameTo(new File(newPath, newName));
			System.out.println(file.getName() + " changed to " + newName);
		}
	}

	/**
	 * @param objects
	 * @param fileName
	 */
	public static void write(List<String> objects, String fileName) {
		try {
			@SuppressWarnings("resource")
			PrintWriter writer = new PrintWriter(
					"/home/brijeshsingh/Desktop/test/afterEnrichment/"+fileName,
					"UTF-8");
			for (String object : objects) {
				writer.println(object);
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public static List<String> read() {
		// TODO Auto-generated method stub
		return null;
	}

}
