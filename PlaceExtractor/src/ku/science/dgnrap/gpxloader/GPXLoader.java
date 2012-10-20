package ku.science.dgnrap.gpxloader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.topografix.gpx.Gpx;
import com.topografix.gpx.Gpx.Trk;
import com.topografix.gpx.Gpx.Trk.Trkseg;
import com.topografix.gpx.Gpx.Trk.Trkseg.Trkpt;

/**
 *
 * @author perrym
 */
public class GPXLoader extends DefaultHandler {
    
	
    
	public static void main(String[] args) {
		
		GPXLoader gpxl = new GPXLoader();
		try {
			gpxl.readTrack(new File("data/57039001-20111005.gpx"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void readTrack(File file) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("com.topografix.gpx._1._0");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Gpx xx = (Gpx) unmarshaller.unmarshal(new File("data/57039001-20111005.gpx"));
		List<Trk> tracks = xx.getTrk();
		for (Trk t : tracks) {
			for (Trkseg ts : t.getTrkseg()) {
				List<Trkpt> trackPoints = ts.getTrkpt();
				for (Trkpt tp : trackPoints) {
					System.out.println(tp.getLat() + " : " + tp.getLon() + " : " + tp.getTime());
				}
			}
		}
	}
    
}