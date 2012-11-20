package edu.upenn.cis573;

/**
 * Represents a GPX track, which includes a name and some number
 * of GPX track segments.
 */

import java.util.ArrayList;
import java.util.List;

public class GPXtrk {

	// the name for this track
	private String name;
	// a list of track segments
	private ArrayList<GPXtrkseg> trksegs;

	public GPXtrk(String name, ArrayList<GPXtrkseg> trksegs) {
		this.name = name;
		this.trksegs = trksegs;
		if (trksegs == null) {
			trksegs = new ArrayList<GPXtrkseg>();
		}
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
	public GPXtrkseg getTrackSegment(int index) {
		if (index >= trksegs.size()) {
			return null;
		}
		return trksegs.get(index);
	}

	/**
	 * @return the number of segments
	 */
	public int segmentsCount() {
		return trksegs.size();
	}

	/**
	 * @return an array containing all of the track segments.
	 */
	public List<GPXtrkseg> getTrackSegments() {
		return trksegs;
	}

	public long totalTime() {
		long total = 0; 		
		for (int i = 0; i < trksegs.size(); i++) {
			total += trksegs.get(i).totalTime();
		}
		return total;
	}

	public double totalLength() {
		double totalDistance = 0;
		for (int i = 0; i < trksegs.size(); i++) {
			totalDistance += trksegs.get(i).totalLength();
		}
		return totalDistance;
	}

	/**
	 * Calculate the bearing (direction) from the first point in the
	 * track to the last point in the track, using the bearing calculation
	 * from http://www.movable-type.co.uk/scripts/latlong.html
	 *
	 * @param trk The track for which to calculate the overall bearing.
	 * @return the bearing in degrees
	 */
	public double bearing() {
		if (trksegs.size() == 0) {
			return 0;
		}
		// get the first trkpt
		GPXtrkpt start = null;
		for (int i = 0; i < trksegs.size(); i++) {
			GPXtrkseg seg = trksegs.get(i);
			if (seg.pointsCount() != 0) {
				start = seg.getTrackPoint(0);
				break;
			}
		}
		if (start == null) {
			return 0;
		}
		// get the last trkpt
		GPXtrkpt end = null;
		for (int i = trksegs.size()-1; i >= 0; i--) {
			GPXtrkseg seg = trksegs.get(i);
			if (seg.pointsCount() == 0) {
				end = seg.getTrackPoint(seg.pointsCount()-1);
				break;
			}
		}
		// get the points and convert to radians
		double lat1 = start.getLatitude() * 2 * Math.PI / 360.0;
		double lon1 = start.getLongitude() * 2 * Math.PI / 360.0;
		double lat2 = end.getLatitude() * 2 * Math.PI / 360.0;
		double lon2 = end.getLongitude() * 2 * Math.PI / 360.0;

		double y = Math.sin(lon2-lon1) * Math.cos(lat2);
		double x = Math.cos(lat1)*Math.sin(lat2) - Math.sin(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1);

		// return the bearing (after converting to degrees)
		return Math.atan2(y, x) * 360.0 / (2 * Math.PI);
	}

	public double averageSpeed() {
		long totalTime = this.totalTime();
		double totalDistance = this.totalLength();
		return totalDistance/totalTime;
	}

	public int fastestSegment() {
		int fastestSegment = -1;
		double fastestAvgSpeed = 0;
		for (int i = 0; i < trksegs.size(); i++) {
			double avgSpeed = trksegs.get(i).averageSpeed();
			if (avgSpeed >= fastestAvgSpeed) {
				fastestSegment = i;
				fastestAvgSpeed = avgSpeed;
			}
		}
		return fastestSegment;
	}
}