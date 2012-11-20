package edu.upenn.cis573;

/**
 * Represents a track segment, which is just a collection of zero or more track points.
 */

import java.util.ArrayList;
import java.util.List;

public class GPXtrkseg {

	private static final int R = 6371; // radius of the earth in km
	
	private ArrayList<GPXtrkpt> trkpts;

	public GPXtrkseg(ArrayList<GPXtrkpt> trkpts) {
		this.trkpts = trkpts;
		if (trkpts == null) {
			this.trkpts = new ArrayList<GPXtrkpt>();
		}
	}

	/**
	 * Get the track point for the given index.
	 *
	 * @param index The index of the point to be retrieved.
	 * @return The track point at the provided index. Return null if the index is too large (i.e., is larger than the number of points)
	 */
	public GPXtrkpt getTrackPoint(int index) {
		if (index >= trkpts.size()) {
			return null;
		}
		return trkpts.get(index);
	}

	/**
	 * @return the number of track points in this segment
	 */
	public int pointsCount() {
		return trkpts.size();
	}

	/**
	 * @return an array of track point objects
	 */
	public List<GPXtrkpt> getTrackPoints() {
		return trkpts;
	}

	/**
	 * Calculates the elapsed time for the given segment by returning
	 * the difference between the first and last track points.
	 *
	 * @param trkseg The track segment for which to calculate the elapsed time.
	 * @return the elapsed time in seconds; -1 if the track segment object is null
	 */

	public long totalTime() {
		// get the time of the first point of the segment
		GPXtrkpt firstPt = trkpts.get(0);
		long start = firstPt.getTime();

		// get the time of the last point of the segment
		GPXtrkpt lastPt = trkpts.get(trkpts.size()-1);
		long end = lastPt.getTime();
		// total elapsed time in milliseconds
		return end - start;
	}

	public double totalLength() {
		double totalDistance = 0;

		for (int j = 0; j < trkpts.size()-1; j++) {
			GPXtrkpt pt1 = trkpts.get(j);
			GPXtrkpt pt2 = trkpts.get(j+1);

			// convert lat and lon from degrees to radians
			double lat1 = pt1.getLatitude() * 2 * Math.PI / 360.0;
			double lon1 = pt1.getLongitude() * 2 * Math.PI / 360.0;
			double lat2 = pt2.getLatitude() * 2 * Math.PI / 360.0;
			double lon2 = pt2.getLongitude() * 2 * Math.PI / 360.0;

			// use the spherical law of cosines to figure out 2D distance
			double d = Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1)) * R;	
			// now we need to take the change in elevation into account
			double ele1 = pt1.getElevation();
			double ele2 = pt2.getElevation();

			// calculate the 3D distance
			double distance = Math.sqrt(d*d + (ele1-ele2)*(ele1-ele2));

			// add it to the running total
			totalDistance += distance;
		}
		return totalDistance;
	}
	
	public double averageSpeed() {
		long totalTime = this.totalTime();
		double totalDistance = this.totalLength();
		return totalDistance/totalTime;
	}
}