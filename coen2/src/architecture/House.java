package architecture;

import sensordevices.Sensor;
import simulation.Event;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

public class House {

	public String name;
	public String custName;
	public String phoneNo1;
	public String phoneNo2;
	private List <Room> Rooms = new ArrayList<Room>();
	private List <Event> Events = new ArrayList<Event>();
	
	public House() {
		
	}
	public House(String n) {
		this.name = n;
	}
	
	//GETTERS & SETTERS -
	public List <Event> getEvents() {
		return Events;
	}
	public List <Room> getRooms() {
		return Rooms;
	}
	public String getName() {
		return name;
	}
	public String getCustName() {
		return custName;
	}
	public String getPhone1() {
		return phoneNo1;
	}
	public String getPhone2() {
		return phoneNo2;
	}
	public void setName(String n) {
		this.name=n;
	}
	public void setCustName(String n) {
		this.custName=n;
	}
	public void setPhoneNo1(String n) {
		this.phoneNo1=n;
	}
	public void setPhoneNo2(String n) {
		this.phoneNo2=n;
	}
	public void setRooms(List <Room> R) {
		this.Rooms = R;
	}
	//GETTERS & SETTERS +
	
	public void addRoom(Room r) {
		Rooms.add(r);
	}

	public void addEvent(Event e) {
		Events.add(e);
	}
	
	public void displayRooms() {
		for(int i=0; i<Rooms.size(); i++) {
			Room r = new Room();
			r = Rooms.get(i);
			System.out.println("Room Description: " + r.getDescription() + " Size: " + r.getRoomArea());
		}
	}
	
	public void displayHouseEventLogs() {
		for(int i=0; i<Events.size(); i++) {
			Event e = new Event();
			e = Events.get(i);
			System.out.println("Room Affected: ");
		}
	}
	public Room findRoom(String n) {
		Room r1 = new Room();
		String name=n;
		
		for(int i=0; i<Rooms.size(); i++) {
			Room r = new Room();
			r = Rooms.get(i);
			if (n == r.getDescription()) {
				r1 = Rooms.get(i);
			}
		}
		if (r1.getDescription() == "") {
			JOptionPane.showMessageDialog(null, "Not a valid Room.");
		}
		return r1;
	}
	public int findRoomKey(String n) {
		int r1 = 0;
		String name=n;
		
		for(int i=0; i<Rooms.size(); i++) {
			Room r = new Room();
			r = Rooms.get(i);
			if (n == r.getDescription()) {
				r1 = i;
				
			}
		}
		return r1;
	}
	public void replaceRoom(Room r1,String n) {
		String name=n;
		
		for(int i=0; i<Rooms.size(); i++) {
			Room r = new Room();
			r = Rooms.get(i);
			if (n == r.getDescription()) {
				Rooms.set(i, r1);
			}
		}
	}
	public String[] getRoomArray() {
		String[] output = new String[100];
		
		for (int x=0; x<Rooms.size(); x++) {
			Room r1 = Rooms.get(x); 
			output[x] = r1.getDescription();
		}
		return output;
	}
	public String[] getEventsArray() {
		String[] output = new String[100];
		
		for (int x=0; x<Events.size(); x++) {
			Event r1 = Events.get(x); 
			Sensor s = new Sensor();
			s = r1.getSensor();
			Room r = new Room();
			r = r1.getRoom();
			output[x] = x + ". " + "The "+ s.getType() +" sensor in room '"+ r.getDescription() + "' has gone off and the proper authorities have been called ("+r1.getTimeStamp()+")"; //e1.getDescription();
		}
		return output;
	}

}
