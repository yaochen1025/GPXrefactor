package edu.upenn.cis573;

/**
 * Contains a static method for checking whether a file matches the 
 * GPX format specification.
 *
 * Note that, for purposes of simplicity, this does not perform the check
 * using the REAL specification, but rather uses the one provided for this
 * particular assignment!
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GPXchecker {

    /*
     * Checks the format of the GPX file specified by the filename.
     * See the documentation for the expected file format.
     *
     * @param filename The GPX file to be checked.
     * @return a GPXformat object that indicates whether this file is valid; if so, it contains an ArrayList of all the tags that have been encountered (to make parsing easier); if not, it contains an error message.
     */
    public static GPXformat checkFormat(String filename) {
		// the return value
		GPXformat format = new GPXformat();
	
		try {
		  
			ArrayList<String> tags = extractTags(filename);
	
		    // now go through and see what we've got in the list
		    
		    // index into the list
		    int index = 0;
	
		    // the first tag must be <gpx>
		    if (tags.get(index++).equals("<gpx") == false) throw new Exception("Format error! Expected <gpx> tag");
	
			// then <time>
			if (tags.get(index++).equals("<time") == false) throw new Exception("Format error! Expected <time> tag");
			// then </time>
			if (tags.get(index++).equals("</time") == false) throw new Exception("Format error! Expected <text> tag");

		    // now we should have <trk>
		    if (tags.get(index++).equals("<trk") == false) throw new Exception("Format error! Expected <trk> tag");
	
		    // the <name> tag is optional
		    if (tags.get(index).equals("<name")) {
			index++;
			// now we should have </name>
			if (tags.get(index++).equals("</name") == false) throw new Exception("Format error! Expected </name> tag");
		    }
	
		    // now we have some number of <trkseg>
		    String tag = (String)(tags.get(index++));
		    while (tag.equals("<trkseg")) {
			// now we have some number of <trkpt>
			tag = (String)(tags.get(index++));
			while (tag.equals("<trkpt")) {
	
			    // now we should have <ele>
			    if (tags.get(index++).equals("<ele") == false) throw new Exception("Format error! Expected <ele> tag");
	
			    // now we should have </ele>
			    if (tags.get(index++).equals("</ele") == false) throw new Exception("Format error! Expected </ele> tag");
	
			    // now we should have <time>
			    if (tags.get(index++).equals("<time") == false) throw new Exception("Format error! Expected <time> tag");
	
			    // now we should have </time>
			    if (tags.get(index++).equals("</time") == false) throw new Exception("Format error! Expected </time> tag");
	
			    // now we should have </trkpt>
			    if (tags.get(index++).equals("</trkpt") == false) throw new Exception("Format error! Expected </trkpt> tag");
			    
			    // consume the next tag
			    tag = (String)(tags.get(index++));
			}
			
			// now we should have </trkseg>
			if (tag.equals("</trkseg") == false) throw new Exception("Format error! Expected </trkseg> tag");
	
			// consume the next tag
			tag = (String)(tags.get(index++));
		    }
	
		    // if we get here, then we should be on </trk>
		    if (tag.equals("</trk") == false) throw new Exception("Format error! Expected </trk> tag");
	
		    // last but not least: </gpx>
		    if (tags.get(index++).equals("</gpx") == false) throw new Exception("Format error! Expected </gpx> tag");
	
		    // if we made it here, everything is okay
		    format.setValid(true);
		    format.setMessage("Format is valid.");
		    format.setTags(tags);
	
		}
		catch (Exception e) {
		    //e.printStackTrace();
		    // indicate that this is not a valid file
		    format.setValid(false);
		    format.setMessage(e.getMessage());
		}
	
		return format;
    }
    
    
    private static ArrayList<String> extractTags(String filename) throws FileNotFoundException{
    	 Scanner in = new Scanner(new File(filename));
		    in.useDelimiter(">");
	
		    // hold all the tags we find, in sequence
		    ArrayList<String> tags = new ArrayList<String>();
	
		    // loop through each line and find all the tags, then add them to the list
		    while (in.hasNext()) {
			// this is a line ending with ">" (which is ignored)
			String tagLine = in.next().trim();
			// this is the tag we're looking for
			String tag = null;
	
			if (tagLine.startsWith("<")) {
			    // if it starts with a "<", and has a blank space in the line, then there's more stuff but we'll just ignore it
			    if (tagLine.contains(" ")) {
				tag = tagLine.substring(0, tagLine.indexOf(' '));
			    }
			    // otherwise we want the whole line
			    else tag = tagLine;
			}
			else {
			    // if it doesn't start with a "<", we can ignore whatever comes before it
			    tag = tagLine.substring(tagLine.indexOf("<"));
			}
			//System.out.println(tag);
			tags.add(tag);
		    }
		    return tags;
    }
    
    private static boolean validateSegment() {
    	return false;
    }
}