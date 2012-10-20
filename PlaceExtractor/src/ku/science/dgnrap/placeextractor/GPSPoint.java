package ku.science.dgnrap.placeextractor;

import java.util.Date;

import com.vividsolutions.jts.geom.Coordinate;

public class GPSPoint {
	
	Date timeStamp;
	Coordinate coordindate;
	
	public Coordinate getCoordinate() {
		return coordindate;
	}


	public void setCoordinate(Coordinate coord) {
		this.coordindate = coord;
	}


	public Date getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public double getLat() {
		return coordindate.x;
	}

	public double getLon() {
		return coordindate.y;
	}	
	
	public GPSPoint(double lat, double lon, Date timeStamp) {
		coordindate = new Coordinate(lat, lon);
		this.timeStamp = timeStamp;
	}

	public GPSPoint(Coordinate c, Date timeStamp) {
		coordindate = c;
		this.timeStamp = timeStamp;
	}
	
	
	
}
