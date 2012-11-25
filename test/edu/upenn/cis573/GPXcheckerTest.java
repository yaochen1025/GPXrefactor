package edu.upenn.cis573;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GPXcheckerTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testCheckFormatGood() {
		boolean isValid = GPXparser.checkFormat("files/good.gpx");
		assertEquals(true, isValid);
	}

	@Test
	public void testCheckFormatMissingName() {
		boolean isValid = GPXparser.checkFormat("files/missingName.gpx");
		assertEquals(true, isValid);
	}

	@Test
	public void testCheckFormatMissingTime() {
		boolean isValid = GPXparser.checkFormat("files/missingTime.gpx");
		assertEquals(false, isValid);
		//assertEquals("Format error! Expected <time> tag", format.message());
	}
	
	@Test
	public void testCheckFormatNoTrkPts() {
		boolean isValid = GPXparser.checkFormat("files/noTrkPts.gpx");
		assertEquals(true, isValid);
	}

	@Test
	public void testCheckFormatUnbalancedTags() {
		boolean isValid = GPXparser.checkFormat("files/unbalancedTags.gpx");
		assertEquals(false, isValid);
		//assertEquals("Format error! Expected </trkpt> tag", format.message());
	}


}
