package edu.upenn.cis573;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GPXcalculatorTest {

	private GPXobject obj;
	//private GPXcalculator calc = new GPXcalculator();

	@Before
	public void setUp() throws Exception {
		GPXtrkpt pt0 = new GPXtrkpt(0, 0, 0, "2012-10-29T15:03:00Z");
		GPXtrkpt pt1 = new GPXtrkpt(10, 10, 10, "2012-10-30T15:03:00Z");
		GPXtrkpt pt2 = new GPXtrkpt(20, 20, 20, "2012-10-31T15:03:00Z");
		GPXtrkpt pt3 = new GPXtrkpt(30, 30, 30, "2012-11-02T15:03:00Z");
		
		ArrayList<GPXtrkpt> pts0 = new ArrayList<GPXtrkpt>();
		pts0.add(pt0);
		pts0.add(pt1);
		
		ArrayList<GPXtrkpt> pts1 = new ArrayList<GPXtrkpt>();
		pts1.add(pt2);
		pts1.add(pt3);
		
		GPXtrkseg seg0 = new GPXtrkseg(pts0);
		GPXtrkseg seg1 = new GPXtrkseg(pts1);
		
		ArrayList<GPXtrkseg> segs = new ArrayList<GPXtrkseg>();
		segs.add(seg0);
		segs.add(seg1);
		
		GPXtrk trk = new GPXtrk("Test track", segs);
		
		obj = new GPXobject("2012-11-05T15:03:00Z", trk);
	}

	@Test
	public void testElapsedTimeGPXtrk() {		
		long time = obj.getTrack().totalTime();

		// should be three days
		long threeDays = 3 * 24 * 60 * 60 * 1000;
		assertEquals(threeDays, time);
	}

	@Test
	public void testElapsedTimeGPXtrkseg() {
		
		long time = obj.getTrack().getTrackSegment(0).totalTime();
		// should be one day
		long oneDay = 24 * 60 * 60 * 1000;
		assertEquals(oneDay, time);

		time = obj.getTrack().getTrackSegment(1).totalTime();
		// should be two days
		assertEquals(oneDay * 2, time);
		
	}

	@Test
	public void testDistanceTraveledGPXtrk() {
		
		double dist = obj.getTrack().totalLength();
		
		assertEquals(3067.685, dist, 0.01);
	}

	@Test
	public void testDistanceTraveledGPXtrkseg() {
		double dist = obj.getTrack().getTrackSegment(0).totalLength();
		assertEquals(1568.552, dist, 0.01);
		
		dist = obj.getTrack().getTrackSegment(1).totalLength();
		assertEquals(1499.132, dist, 0.01);
	}
	
	@Test
	public void testAverageSpeed() {
		
		double speed = obj.getTrack().averageSpeed();
		assertEquals(0.000011835, speed, 0.0000001);

	}

	@Test
	public void testBearing() {

		double bearing = obj.getTrack().bearing();
		assertEquals(40.89, bearing, 0.01);
		
	}
	
	@Test
	public void testFastestSegment() {
		
		int fastest = obj.getTrack().fastestSegment();
		//Change test??
		assertEquals(0, fastest);
		
	}


}
