package edu.upenn.cis573;


/**
 * Represents all the data in a GPX file.
 */

public class GPXobject {

	// name of this object
	private String name;
	// time at which it was created
	private String time;
    // holds all the information about the track
    private GPXtrk trk;

    // string buffer used for printing
    private StringBuffer out;

    public GPXobject(String time, GPXtrk trk) {

    	this.time = time;
    	this.trk = trk;
    }

    /* Accessors */
    public GPXtrk getTrack() { 
    	return trk; 
    }
    
    public String getName() { 
    	return name; 
    }
    
    public String getTime() { 
    	return time; 
    }

    public String toString() {
		out = new StringBuffer();
		out.append("<gpx>");
		out.append("\n\t<time>" + time + "</time>");		
		out.append(trk.toString().replace("\n", "\n\t"));
		out.append("\n</gpx>\n");
		return out.toString();
    }
}