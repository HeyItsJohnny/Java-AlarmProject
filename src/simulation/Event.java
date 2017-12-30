package simulation;

import sensordevices.Sensor;
import architecture.Room;
import architecture.House;
import java.security.Timestamp;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event {
	
	private Room r;
	private Sensor s;
	private House h;
	private String event;
	private String timeStamp;

	public Event(House hou, Room ro, Sensor se, String e) {
		this.r = ro;
		this.s = se;
		this.h = hou;
		this.event = e;
	    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    Date dateobj = new Date();
	    this.timeStamp = df.format(dateobj);
	}
	public Event() {
		
	}
	
	//GETTERS & SETTERS -
	public String getEvent() {
		return event;
	}
	public Sensor getSensor() {
		return s;
	}
	public Room getRoom() {
		return r;
	}
	public House getHouse() {
		return h;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	//GETTERS & SETTERS +
	
	/*public String displayError(Room r, Sensor s) {
		String errorMessage = "";
			
		return errorMessage;
	}*/
	
	
	
}
