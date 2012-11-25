package edu.upenn.cis573;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GPXparserTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testParseGood() {

		GPXobject obj = GPXparser.fromFile("files/good.gpx");
		
		assertEquals("2009-10-17T22:58:43Z", obj.getTime());
		
		GPXtrk trk = obj.getTrack();
		
		//assertEquals(obj, trk.parent());
		assertEquals("Walking around a little bit", trk.getName());
		
		List<GPXtrkseg> trksegs = trk.getTrackSegments();
		GPXtrkpt trkpt = trksegs.get(0).getTrackPoint(0);

		assertEquals(47.644548, trkpt.getLatitude(), 0.001);
		assertEquals(-122.326897, trkpt.getLongitude(), 0.001);
		assertEquals(4.46, trkpt.getElevation(), 0.001);
		assertEquals("2009-10-17T18:37:26Z", trkpt.timeString());
	}


	@Test
	public void testParseMissingName() {

		GPXobject obj = GPXparser.fromFile("files/missingName.gpx");
		
		assertEquals("2009-10-17T22:58:43Z", obj.getTime());
		
		GPXtrk trk = obj.getTrack();
		
		//assertEquals(obj, trk.parent());
		
		List<GPXtrkseg> trksegs = trk.getTrackSegments();
		GPXtrkpt trkpt = trksegs.get(0).getTrackPoint(0);

		assertEquals(47.644548, trkpt.getLatitude(), 0.001);
		assertEquals(-122.326897, trkpt.getLongitude(), 0.001);
		assertEquals(4.46, trkpt.getElevation(), 0.001);
		assertEquals("2009-10-17T18:37:26Z", trkpt.timeString());
	}

	@Test
	public void testParseNoTrkPts() {

		GPXobject obj = GPXparser.fromFile("files/noTrkPts.gpx");
		
		assertEquals("2009-10-17T22:58:43Z", obj.getTime());
		
		GPXtrk trk = obj.getTrack();
		
		//assertEquals(obj, trk.parent());
		assertEquals("Walking around a little bit", trk.getName());
		
		List<GPXtrkseg> trksegs = trk.getTrackSegments();		
		assertEquals(0, trksegs.get(0).getTrackPoints().size());
	}

}
