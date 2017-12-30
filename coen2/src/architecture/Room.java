package architecture;

import sensordevices.Sensor;
import java.util.ArrayList;
import java.util.List;

public class Room {
	
	private String RoomDescription;
	private int sizeWidth;
	private int sizeLength;
	private List <Sensor> sensors = new ArrayList<Sensor>();

	/**
	 * Constructor 1
	 * @param rd
	 * @param w
	 * @param l
	 */
	public Room(String rd, String w, String l) {
		this.RoomDescription = rd;
		this.sizeWidth = Integer.parseInt(w);
		this.sizeLength = Integer.parseInt(l);
	}
	
	/**
	 * Constructor 2
	 * @param rd
	 * @param w
	 * @param l
	 * @param s
	 */
	public Room(String rd, int w, int l, List<Sensor> s) {
		this.RoomDescription = rd;
		this.sizeWidth = w;
		this.sizeLength = l;
		this.sensors = s;
	}
	public Room() {
		
	}
	
	/**
	 * GETTERS & SETTERS
	 */
	public String getDescription() {
		return RoomDescription;
	}
	public int getRoomArea() {
		return sizeWidth*sizeLength;
	}
	public List <Sensor> getSensors() {
		return sensors;
	}
	public int getWidth() {
		return sizeWidth;
	}
	public int getLength() {
		return sizeLength;
	}
	public void setDescription(String d) {
		this.RoomDescription = d;
	}
	public void setSensors(List <Sensor> S) {
		this.sensors = S;
	}
	public void setWidth(int w) {
		this.sizeWidth = w;
	}
	public void setLength(int l) {
		this.sizeLength = l;
	}
	/**
	 * Add sensor Method
	 */
	public void addSensor(Sensor s) {
		sensors.add(s);
	}
	
	/**
	 * Display Sensors Method
	 */
	public void displaySensors() {
		for (int i=0; i<sensors.size(); i++) {
			Sensor s = new Sensor();
			s = sensors.get(i);
			System.out.println("Sensor: " + s.getType() + " Activated: " + s.getActivated());
		}
	}
}
