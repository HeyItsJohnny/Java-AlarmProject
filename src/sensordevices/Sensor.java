package sensordevices;

public class Sensor {

	private String type;
	private String ErrorMessage;
	private boolean isActivated;
	
	public Sensor() {
		
	}
	public Sensor(String t) {
		this.type = t;
	}
	
	//GETTERS & SETTERS -
	public String getType() {
		return type;
	}
	public String getErrorMessage() {
		return ErrorMessage;
	}
	public boolean getActivated() {
		return isActivated;
	}
	public void setType(String t) {
		this.type = t;
	}
	public void setIsActivated(boolean a) {
		this.isActivated = a;
	}
	public void setErrorMessage(String e) {
		this.ErrorMessage = e;
	}
	//GETTERS & SETTERS +

}
