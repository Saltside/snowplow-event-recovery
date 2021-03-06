/**
 * troubleshoot
 */
package se.saltside.data.pipeline.troubleshoot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author brijeshsingh - 21-Aug-2016
 * 
 */
public class RenameBadFile {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String listOfFolderPath = "";
		String badInputFolder = "";
		String badOutPutFolderAfterRename = "";
		if (args != null && args.length > 0) {
			listOfFolderPath = args[0];
			badInputFolder = args[1];
			badOutPutFolderAfterRename = args[2];
			if (listOfFolderPath == "" && badInputFolder == ""
					&& badOutPutFolderAfterRename == "") {
				System.out.println("argument required");
				return;
			}
			process(listOfFolderPath, badInputFolder,
					badOutPutFolderAfterRename);
			System.out.println("Process Done");
		} else {
			System.out.println("Pass the argument");
		}

	}

	/**
	 * @param listOfFolderPath
	 * @param badInputFolder
	 * @param badOutPutFolderAfterRename
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void process(String listOfFolderPath, String badInputFolder,
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

}
