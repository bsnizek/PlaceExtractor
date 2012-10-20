package ku.science.dgnrap.gpxloader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ku.science.dgnrap.placeextractor.GPSPoint;

import org.xml.sax.helpers.DefaultHandler;

import com.topografix.gpx.Gpx;
import com.topografix.gpx.Gpx.Trk;
import com.topografix.gpx.Gpx.Trk.Trkseg;
import com.topografix.gpx.Gpx.Trk.Trkseg.Trkpt;

/**
 *
 * @author Bernhard Snizek, <besn@life.ku.dk>
 */
public class GPXLoader extends DefaultHandler {
  

	public ArrayList<GPSPoint> readTrack(String fileName) throws JAXBException {
		
		ArrayList<GPSPoint> points = new ArrayList<GPSPoint>();
		
		JAXBContext jc = JAXBContext.newInstance("com.topografix.gpx._1._0");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Gpx xx = (Gpx) unmarshaller.unmarshal(new File(fileName));
		List<Trk> tracks = xx.getTrk();
		for (Trk t : tracks) {
			for (Trkseg ts : t.getTrkseg()) {
				List<Trkpt> trackPoints = ts.getTrkpt();
				for (Trkpt tp : trackPoints) {
					GPSPoint p = new GPSPoint(tp.getLat().doubleValue(), 
											  tp.getLon().doubleValue(), 
											  tp.getTime().toGregorianCalendar().getGregorianChange()
											  );
					points.add(p);
				}
			}
		}
		return points;
	}
	
	public static void main(String[] args) {
		
		GPXLoader gpxl = new GPXLoader();
		try {
			gpxl.readTrack("data/57039001-20111005.gpx");
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}
    
}