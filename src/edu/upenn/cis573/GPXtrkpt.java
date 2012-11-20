package edu.upenn.cis573;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a track point in a GPX file.
 */

public class GPXtrkpt {

    // latitude
    private double lat;
    // longitude
    private double lon;
    // elevation
    private double ele;
    // time is assumed to be in the following format: YYYY-MM-DDThh:mm:ssZ
    private Date time;
    private String timeString;

    public GPXtrkpt(double lat, double lon, double ele, String time) {
		this.lat = lat;
		this.lon = lon;
		this.ele = ele;
		this.timeString = time;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		try {
			this.time = formatter.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }

    /* Accessors */
    public double getLatitude() { 
    	return lat; 
    }
    
    public double getLongitude() { 
    	return lon; 
    }
    
    public double getElevation() { 
    	return ele; 
    }
    
    public String timeString() { 
    	return this.timeString;
    }
    
    public long getTime() {
    	return this.time.getTime();
    }
}
