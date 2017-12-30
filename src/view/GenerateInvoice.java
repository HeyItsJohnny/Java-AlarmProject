package view;

import java.util.List;

public class GenerateInvoice {
	
	public double annualFee = 100;
	public double roomFee = 25;
	public double sensorFee = 25;
	public double eventFee = 10; 

	public GenerateInvoice() {
		
	}
	
	/**
	 * GETTERS -
	 */
	public double getAnnualFee() {
		return annualFee;
	}
	public double getRoomFee() {
		return roomFee;
	}
	public double getSensorFee() {
		return sensorFee;
	}
	public double getEventFee() {
		return eventFee;
	}
	/**
	 * GETTERS +
	 */
	
	/**
	 * GET Total RoomFee
	 */
	public double getTotalRoom(int noRooms) {
		double output = 0;
		output = noRooms * roomFee;
		return output;
	}
	/**
	 * GET Total SensorFee
	 */
	public double getTotalSensor(int noSensor) {
		double output = 0;
		output = noSensor * sensorFee;
		return output;
	}
	/**
	 * GET Total EventFee
	 */
	public double getTotalEvent(int noEvent) {
		double output = 0;
		output = noEvent * eventFee;
		return output;
	}

}
