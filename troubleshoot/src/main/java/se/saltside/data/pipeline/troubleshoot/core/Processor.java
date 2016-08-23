/**
 * troubleshoot
 */
package se.saltside.data.pipeline.troubleshoot.core;

/**
 * @author brijeshsingh - 23-Aug-2016
 * 
 */
public interface Processor<T extends BaseEnrichmentRequest>{
	
	public void execute(T request);

}

