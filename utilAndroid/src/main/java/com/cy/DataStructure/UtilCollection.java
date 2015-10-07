package com.cy.DataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/** 
 *  
 */  
public class UtilCollection {  
	
	/**数组填充进arraylist
	 * @param array
	 * @return
	 */
	public ArrayList<String> getArrayListFromArray(String[] array){
		  final List<String> urls = new ArrayList<String>();
		  
		    // Ensure we get a different ordering of images on each run.
		    Collections.addAll(urls, array);
		    Collections.shuffle(urls);

		    // Triple up the list.
		    ArrayList<String> copy = new ArrayList<String>(urls);
		    urls.addAll(copy);
		    urls.addAll(copy);
		    
		    return (ArrayList<String>) urls;
	}

}  