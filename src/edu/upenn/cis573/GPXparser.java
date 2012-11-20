package edu.upenn.cis573;


/**
 * Contains a static method to parse a well-formed GPX file and create
 * a GPXobject.
 */

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GPXparser {
	
	public GPXobject fromFile(String fileName) {
		Element root = null;
		try {
			root = this.readGPXFile(fileName);
		} catch (Exception e) {
			return null;
		}
		if (!this.validateGPXFile(root)) {
			return null;
		}
		return this.mapToGPXObject(root);
	}
	
	private GPXobject mapToGPXObject(Element root) {
		Element gpx = root.element("gpx");
		Element time = gpx.element("time");
		Element trk = gpx.element("trk");
		String timeVal = time.getText();
		GPXtrk gpxTrk = this.mapToTrk(trk);
		return new GPXobject(timeVal, gpxTrk);
	}
	
	private GPXtrk mapToTrk(Element trk) {
		Element name = trk.element("name");
		List<Element> trkSegs = trk.elements("trkseg");
		String nameVal = null;
		if (name != null) {
			nameVal = name.getText();
		}
		ArrayList<GPXtrkseg> segs = new ArrayList<GPXtrkseg>();
		if (trkSegs != null) {
			for (int i = 0; i < trkSegs.size(); i++) {
				GPXtrkseg seg = mapToTrkSeg(trkSegs.get(i));
				segs.add(seg);
			}
		}
		return new GPXtrk(nameVal, segs);
	}
    
	private GPXtrkseg mapToTrkSeg(Element trkSeg) {
		List<Element> trkPts = trkSeg.elements("trkpt");
		ArrayList<GPXtrkpt> pts = new ArrayList<GPXtrkpt>();
		if (trkPts != null) {
			for (int i = 0; i < trkPts.size(); i++) {
				GPXtrkpt pt = mapToTrkPt(trkPts.get(i));
				pts.add(pt);
			}
		}
		return new GPXtrkseg(pts);
	}
	
	private GPXtrkpt mapToTrkPt(Element trkPt) {
		Attribute lat = trkPt.attribute("lat");
		Attribute lon = trkPt.attribute("lon");
		Element ele = trkPt.element("ele");
		Element time = trkPt.element("time");
		double latVal = Double.parseDouble(lat.getText());
		double lonVal = Double.parseDouble(lon.getText());
		double eleVal = Double.parseDouble(ele.getText());
		String timeVal = time.getText();
		return new GPXtrkpt(latVal, lonVal, eleVal, timeVal);
	}
	
    private Element readGPXFile(String fileName) throws Exception {
    	SAXReader reader = new SAXReader();
    	Document doc = reader.read(new File(fileName));
    	return doc.getRootElement();
    }
    
    private boolean validateGPXFile(Element root) {
    	List<Element> children = root.elements();
    	if (children.size() != 1) {
    		return false;
    	}
    	Element gpx = children.get(0);
    	if (!"gpx".equals(gpx.getName())) {
    		return false;
    	}
    	return validateGPXElement(gpx);
    }
	
	private boolean validateGPXElement(Element gpx) {
		List<Element> children = gpx.elements();
		if (children.size() != 2) {
			return false;
		}
		Element time = children.get(0);
		Element trk = children.get(1);
		if (!"time".equals(time.getName()) || !"trk".equals(trk.getName())) {
			return false;
		}
		return validateTimeElement(time)&&validateTrkElement(trk);
	}
	
	private boolean validateTimeElement(Element time) {
		String value = time.getText();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'T'");
		try {
			format.parse(value);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	private boolean validateTrkElement(Element trk) {
		List<Element> children = trk.elements();
		if (children.size() == 0) {
			return true;
		}
		Element maybeName = children.get(0);
		int start = 0;
		if ("name".equals(maybeName.getName())) {
			start = 1;
		}
		for (int i = start; start < children.size(); i++) {
			Element trkSeg = children.get(i);
			if (!"trkSeg".equals(trkSeg.getName())) {
				return false;
			}
			if (!validateTrkSegElement(trkSeg)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean validateTrkSegElement(Element trkSeg) {
		List<Element> children = trkSeg.elements();
		for (int i = 0; i < children.size(); i++) {
			Element trkPt = children.get(i);
			if (!validateTrkPtElement(trkPt)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean validateTrkPtElement(Element trkPt) {
		List<Element> children = trkPt.elements();
		if (children.size() != 2) {
			return false;
		}
		Element ele = children.get(0);
		Element time = children.get(1);
		if (!"ele".equals(ele.getName())) {
			return false;
		}
		if (!"time".equals(time.getName())) {
			return false;
		}
		if (!validateTimeElement(time)) {
			return false;
		}
		List<Attribute> attrs = ele.attributes();
		if (attrs.size() != 2) {
			return false;
		}
		Attribute lat = attrs.get(0);
		Attribute lon = attrs.get(1);
		if (!"lat".equals(lat.getName())) {
			return false;
		}
		if (!"lon".equals(lon.getName())) {
			return false;
		}
		return true;
	}

}