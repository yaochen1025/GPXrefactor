/*
 * GPXstatsMain.java
 *
 */

package edu.upenn.cis573;

public class GPXstatsMain {

    public static void main(String[] args) {
	
		// make sure a file is specified
		if (args.length == 0) {
		    System.out.println("Usage: java edu.upenn.cis573.GPXstats.GPXstatsMain [GPX file]");
		    System.exit(1);
		}
	
		// get the name of the file
		String filename = args[0];
		System.out.println("Filename: " + filename);
	
		GPXparser parser = new GPXparser();
		GPXobject object = parser.fromFile(filename);
	
    }



}