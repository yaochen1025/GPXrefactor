package edu.upenn.cis573;

/**
 * Represents a GPX track, which includes a name and some number
 * of GPX track segments.
 */

import java.util.ArrayList;

public class GPXtrk {
    
    // the name for this track
    private String name;
    // a list of track segments
    private ArrayList<GPXtrkseg> trksegs;

    public GPXtrk(String name, ArrayList<GPXtrkseg> trksegs, GPXobject parent) {
		this.name = name;
		this.trksegs = trksegs;
    }

    public String name() { 
    	return name; 
    }

    /**
     * Get the track segment for the given index.
     *
     * @param index The index of the segment to be retrieved.
     * @return The track segment at the provided index. Return null if the index is too large (i.e., is larger than the number of segments)
     */
    public GPXtrkseg trkseg(int index) {
		if (index >= trksegs.size()) {
			return null;
		}
		return trksegs.get(index);
    }

    /**
     * @return the number of segments
     */
    public int numSegments() {
    	return trksegs.size();
    }
    
    /**
     * @return an array containing all of the track segments.
     */
    public GPXtrkseg[] trksegs() {
		GPXtrkseg segs[] = new GPXtrkseg[trksegs.size()];
		for (int i = 0; i < segs.length; i++) {
			segs[i] = trksegs.get(i);
		}
		return segs;
    }

}