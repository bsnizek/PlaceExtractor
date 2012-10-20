package ku.science.dgnrap.placeextractor;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBException;

import ku.science.dgnrap.gpxloader.GPXLoader;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;


public class PlaceExtractor {

	private Coordinate oldC;
	private Date startTS;
	private Coordinate currC;
	private Date endTS;
	private boolean clustering;
	private ArrayList<Double> distanceBuffer = new ArrayList<Double>();
	private int noisyData;
	
	private int m = 5;

	public PlaceExtractor() {
		// TODO Auto-generated constructor stub
	}
	
	public void identifyPlaces(ArrayList<GPSPoint> ls, 
							   double t, ArrayList<Point> db, 
							   Coordinate lastC, 
							   Date lastTS, 
							   double dist, 
							   double dur) {
	
		this.oldC = lastC;
		this.startTS = lastTS;
		
		this.currC = ls.get(0).getCoordinate();
		this.endTS = ls.get(0).getTimeStamp();
		
		this.clustering = false;
		this.distanceBuffer.add(dist);
		
		this.noisyData = 1;
		
		if ( oldC.distance(currC) < dist && ((endTS.compareTo(startTS)>dur))) {
			clustering = true;
			Coordinate clusterCenter = calcMeanCoordinate(oldC,currC);
			oldC = clusterCenter;
		} else {
			oldC = currC;
			startTS = endTS;
		}
		
		int i = 0;
		
		while (i < ls.size()) {
			currC = ls.get(i+1).getCoordinate();
			endTS = ls.get(i+1).getTimeStamp();
			
			double distance = oldC.distance(currC) / noisyData; // line 17
			
			if (distance <= doubleAverage(distanceBuffer) * m) {
				noisyData = 1;
				distanceBuffer.add(distance);
				if ((clustering == false) && (distance<dist)) {
					clustering = true;
					// clusterCenter = 
				}
			}
		}
		
		
	}
	
	private Double doubleAverage(ArrayList<Double> dd) {
		Double sum = 0.0d;
		for (Double d : dd) {
			sum = sum + d;
		}
		return sum/dd.size();
	}
	
	private Coordinate calcMeanCoordinate(Coordinate c1, Coordinate c2) {
		double x = (c1.x+c2.x)/2;
		double y = (c1.y+c2.y)/2;
		double z = (c1.z+c2.y)/2;
		return new Coordinate(x,y,z);
	}
	

	/**
	 * @param args
	 * @throws JAXBException 
	 */
	public static void main(String[] args) throws JAXBException {
		PlaceExtractor pe = new PlaceExtractor();
		pe.createPauseSet("data/57039001-20111005.gpx");
		

	}

	private void createPauseSet(String string) throws JAXBException {
		
		GPXLoader gpxLoader = new GPXLoader();
		ArrayList<GPSPoint> track = gpxLoader.readTrack(string);
		
		
		double t = 0;
		ArrayList<Point> db = null;
		
		Coordinate lastC = null;
		Date lastTS = null;
		double dist = 0d;
		double dur = 0d;
		
		this.identifyPlaces(track, t, db, lastC, lastTS, dist, dur);
		
	}

}
