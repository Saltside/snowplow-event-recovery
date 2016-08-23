/**
 * troubleshoot
 */
package se.saltside.data.pipeline.troubleshoot;

import java.io.IOException;

import org.json.JSONException;

import se.saltside.data.pipeline.troubleshoot.core.EnrichmentRequest;
import se.saltside.data.pipeline.troubleshoot.core.Processor;
import se.saltside.data.pipeline.troubleshoot.core.RecoveryProcessor;

/**
 * @author brijeshsingh - 23-Aug-2016
 * 
 */
public class Enrichment {
	public static void main(String[] args){
		String foldersName = "";
		String badInputFolder = "";
		String badOutPutFolderAfterRename = "";
		if (args != null && args.length > 0) {
			foldersName = args[0];
			badInputFolder = args[1];
			badOutPutFolderAfterRename = args[2];
			if (foldersName == "" && badInputFolder == ""
					&& badOutPutFolderAfterRename == "") {
				System.out.println("argument required");
				return;
			}
			try {
				FileUtils.process(foldersName, badInputFolder,
						badOutPutFolderAfterRename);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Process Done");
		} else {
			System.out.println("Not sufficient argument");
		}
		Processor<EnrichmentRequest> processor = new RecoveryProcessor();
		processor.execute(new EnrichmentRequest());
		
	}
}

