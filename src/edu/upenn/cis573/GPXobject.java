package edu.upenn.cis573;

import java.util.List;

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


    /**
     * This method writes out the XML for this GPX object.
     * It should primarily be used for debugging purposes.
     *
     * @return a well-formatted (according to the GPX file specification provided for this assignment) string representation of the object
     */
    public String toString() {

		out = new StringBuffer();
	
		// always start with <gpx>
		out.append("<gpx>\n");
		
		out.append("<time>"+time+"</time>\n");		
		
		out.append("\t<trk>\n");
	
		out.append("\t\t<name>" + trk.name() + "</name>\n");
	
		// get all the trkseg obejcts
		List<GPXtrkseg> trksegs = trk.getTrackSegments();
	
		if (trksegs != null) {
	
		    // iterate over the trksegs
		    for (int i = 0; i < trksegs.size(); i++) {
			
			out.append("\t\t<trkseg>\n");
			
			// get all the trkpt objects
			List<GPXtrkpt> trkpts = trksegs.get(i).getTrackPoints();
	
			// iterate over the trkpts
			for (int j = 0; j < trkpts.size(); j++) {
			    GPXtrkpt pt = trkpts.get(j);
			    out.append("\t\t\t<trkpt lat=\"" + pt.getLatitude() + "\" lon=\"" + pt.getLongitude() + "\">\n");
			    out.append("\t\t\t\t<ele>" + pt.getElevation() + "</ele>\n");
			    out.append("\t\t\t\t<time>" + pt.timeString() + "</time>\n");
			    out.append("\t\t\t</trkpt>\n");
	
			}
	
			out.append("\t\t</trkseg>\n");
		    }
	
		}
	
		out.append("\t</trk>\n");
	
		out.append("</gpx>\n");
	
		return out.toString();

    }
    
}