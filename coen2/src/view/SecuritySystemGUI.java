package view;

import sensordevices.Sensor;
import architecture.Room;
import architecture.House;
import simulation.Event;
import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SecuritySystemGUI {

	String resetTextField="";
	House h1 = new House();
	
	private JFrame frame;
	private JTextField houseTXT;
	private JTextField roomDescriptionTxt;
	private JTextField roomWidthTxt;
	private JTextField roomLengthTxt;
	private JTextField sensorType1;
	private JTextField sensorType2;
	private JTextField custTXT;
	private JTextField phone1TXT;
	private JTextField phone2TXT;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecuritySystemGUI window = new SecuritySystemGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SecuritySystemGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		final JList armedList = new JList();
		armedList.setBounds(377, 34, 317, 382);
		frame.getContentPane().add(armedList);
		
		final JList houseActivityList = new JList();
		houseActivityList.setBounds(6, 447, 688, 203);
		frame.getContentPane().add(houseActivityList);
		
		JLabel lblRecentHouseActivity = new JLabel("Recent House Activity");
		lblRecentHouseActivity.setBounds(267, 428, 145, 16);
		frame.getContentPane().add(lblRecentHouseActivity);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmActivateEvent = new JMenuItem("Initiate Random Event");
		mntmActivateEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//CREATE NEW RANDOM FIRE EVENT -
				//Event event1 = new Event(mainHouse,room1,sensor2,"Front Door Broken Open");
				
				//Get Random Room
				List <Room> r1 = new ArrayList<Room>();
				r1 = h1.getRooms();
				
				Room randomRoom = new Room();
				int n = (int )(Math.random() * r1.size() + 0);
				
				randomRoom = r1.get(n);
				
				List <Sensor> s1 = new ArrayList<Sensor>();
				s1 = randomRoom.getSensors();
				Sensor randomSensorActivated = new Sensor();
				
				int k = (int )(Math.random() * s1.size() + 0);
				randomSensorActivated = s1.get(k);
				randomSensorActivated.setIsActivated(false);    //Not activated means it went off
				
				//Get Random Random Sensor From Room;
				Event fireEvent = new Event(h1,randomRoom,randomSensorActivated,randomSensorActivated.getType());
				h1.addEvent(fireEvent);
				
				houseActivityList.setListData(h1.getEventsArray());	//Set List Data
				//CREATE NEW RANDOM FIRE EVENT +
			}
		});
		mnFile.add(mntmActivateEvent);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("House");
		menuBar.add(mnEdit);
		
		JMenuItem mntmViewDetails = new JMenuItem("View Room Sensors");
		mntmViewDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Print Sensor Information:
				String room1 = armedList.getSelectedValue().toString();
				
				//Get room -
				Room addSensorToRoom = new Room();
				addSensorToRoom = h1.findRoom(room1);	
				//Get room +
				
				List <Sensor> RoomSensors = new ArrayList<Sensor>();
				RoomSensors = addSensorToRoom.getSensors();
				String displaySensors = "Sensors: ";
				
				for (int i=0; i<RoomSensors.size(); i++) {
					Sensor s1 = new Sensor();
					s1 = RoomSensors.get(i);
					displaySensors = displaySensors +  s1.getType()  + ", " ;
				}
				JOptionPane.showMessageDialog(null, "Room: "+ addSensorToRoom.getDescription() + "\n"+displaySensors);
				//addSensorToRoom = h1.findRoom(addSensorToRoom.getDescription());
				//addSensorToRoom.addSensor(sensor1);	
			}
		});
		mnEdit.add(mntmViewDetails);
		
		JMenuItem mntmHouseInfo = new JMenuItem("House Information");
		mntmHouseInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List <Room> R1 = new ArrayList<Room>();
				R1 = h1.getRooms();
				
				int totalSensors=0;
				int totalSurfaceArea=0;
				
				for (int i=0; i<R1.size(); i++) {
					Room r = new Room();
					r = R1.get(i);
					List <Sensor> S1 = new ArrayList<Sensor>();
					S1 = r.getSensors();
					totalSensors += S1.size();
					totalSurfaceArea += r.getRoomArea();
				}	
				JOptionPane.showMessageDialog(null, "House Name: " +h1.getName()+ "\nOwner: " + h1.getCustName() + "\nEmergency No. 1: "+ h1.getPhone1()+ "\nEmergency No. 2: " + h1.getPhone2() +"\n# of Rooms: " + R1.size() + "\n# of Sensors: " + totalSensors + "\nTotal surface area of house: " + totalSurfaceArea);
			}
		});
		mnEdit.add(mntmHouseInfo);
		
		JMenuItem mntmViewLogDetails = new JMenuItem("View Log Details");
		mntmViewLogDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List <Event> e1 = new ArrayList<Event>();
				List <Room> room1 = new ArrayList<Room>();
				Event e2 = new Event();
				Room r1 = new Room();
				Sensor s1 = new Sensor();
				
				String buf = houseActivityList.getSelectedValue().toString();
				String buf2 = buf.substring(0,1);
				int key = Integer.parseInt(buf2);
				
				e1 = h1.getEvents();
				e2 = e1.get(key);
				r1 = e2.getRoom();
				s1 = e2.getSensor();
				
				double numerator=0;
				double denomator=0;
				double percent=0;
				
				numerator = r1.getRoomArea();
				
				room1 = h1.getRooms();
				for (int i=0; i<room1.size(); i++) {
					Room r2 = new Room();
					r2 = room1.get(i);
					denomator += r2.getRoomArea();
				}
				percent = (numerator/denomator)*100;
				JOptionPane.showMessageDialog(null, "Room Affected: " + r1.getDescription() + "\nTotal Surface Area Affected: " + r1.getRoomArea() +"\n% of House affected: "+ percent +"\nSensor Tripped: " + s1.getType() +"\nDate & Time: " + e2.getTimeStamp());
			}
		});
		mnEdit.add(mntmViewLogDetails);
		
		JMenuItem mntmExportLogs = new JMenuItem("Export Logs");
		mntmExportLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setDialogTitle("Export To Text File");
				 fileChooser.setSelectedFile(new File("ExportLogData.txt"));
				 int result = fileChooser.showSaveDialog(null);
				 
				 if (result == JFileChooser.APPROVE_OPTION) {
					 
					String newline = System.getProperty("line.separator");
					
					@SuppressWarnings("unused")
					String contentHeader = "Key" + "," +
							 "House" + "," +
					         "Room" + "," +
					         "Surface Area" + "," +
					         "% home affected" + "," +
					         "Sensor" + ","+
					         "Date Time" + newline;
					
					List <Event> e1 = new ArrayList<Event>();
					e1 = h1.getEvents();
					String content = "";
					
					for (int x=0; x<e1.size(); x++) {
						List <Room> room1 = new ArrayList<Room>();
						Event r1 = e1.get(x); 
						Sensor s = new Sensor();
						s = r1.getSensor();
						Room r = new Room();
						r = r1.getRoom();
						
						double numerator=0;
						double denomator=0;
						double percent=0;
						
						numerator = r.getRoomArea();
						
						room1 = h1.getRooms();
						for (int i=0; i<room1.size(); i++) {
							Room r2 = new Room();
							r2 = room1.get(i);
							denomator += r2.getRoomArea();
						}
						percent = (numerator/denomator)*100;
						content += x + "," +
								 h1.getName() + "," +
								 r.getDescription() + "," +
								 r.getRoomArea() + "," +
								 percent + ","  + 
								 s.getType() + "," +
						         r1.getTimeStamp() + newline;
					}
					
					File file = fileChooser.getSelectedFile();
					 try {
						 FileWriter fw = new FileWriter(file.getPath());
						 fw.write(content);
						 fw.flush();
					 } catch (Exception e2) {
						 JOptionPane.showMessageDialog(null, e2.getMessage());
					 }
					 
				 }
			}
		});
		mnEdit.add(mntmExportLogs);
		
		JMenu mnSecurity = new JMenu("Security");
		menuBar.add(mnSecurity);
		
		JMenuItem mntmNewHouse = new JMenuItem("Set House Details");
		mntmNewHouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//House h1 = new House(houseTXT.getText());
				h1.setName(houseTXT.getText());
				h1.setCustName(custTXT.getText());
				h1.setPhoneNo1(phone1TXT.getText());
				h1.setPhoneNo2(phone2TXT.getText());
				JOptionPane.showMessageDialog(null, "Thank you! House details have been set.");
			}
		});
		mnSecurity.add(mntmNewHouse);
		
		JMenuItem mntmAddRoom_1 = new JMenuItem("Add Room & Sensor");
		mntmAddRoom_1.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				
				//CREATE SENSOR -
				Sensor sensor1 = new Sensor(sensorType1.getText());
				sensor1.setIsActivated(true);
				Sensor sensor2 = new Sensor(sensorType2.getText());
				sensor2.setIsActivated(true);
				//CREATE SENSOR +
					
				//ADD ROOM -
				Room addRoom = new Room(roomDescriptionTxt.getText(),roomWidthTxt.getText(),roomLengthTxt.getText());
				if (sensorType1.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Warning Sensor Type 1 not set");
				} else  {
					addRoom.addSensor(sensor1);
				}
				if (sensorType2.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Warning Sensor Type 2 not set");
				} else  {
					addRoom.addSensor(sensor2);
				}
				h1.addRoom(addRoom);
				//ADD ROOM +
				
				armedList.setListData(h1.getRoomArray());	//Set List Data
				
				//Reset Textfields -
				roomDescriptionTxt.setText(resetTextField);
				roomWidthTxt.setText(resetTextField);
				roomLengthTxt.setText(resetTextField);
				sensorType1.setText(resetTextField);
				sensorType2.setText(resetTextField);
				//Reset Textfields +
			}
		});
		mnSecurity.add(mntmAddRoom_1);
		
		JMenuItem mntmEditRoom = new JMenuItem("Edit Room & Sensor");
		mntmEditRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				//Print Sensor Information:
				String room1 = armedList.getSelectedValue().toString();
				
				//Get room -
				Room editRoom = new Room();
				editRoom = h1.findRoom(room1);	
				//Get room +

				int w1 = editRoom.getWidth();
				String w = Integer.toString(w1);
				
				int l1 = editRoom.getLength();
				String l = Integer.toString(l1);
				
				//set Room & Sensor Data -
				roomDescriptionTxt.setText(editRoom.getDescription());
				roomWidthTxt.setText(w);
				roomLengthTxt.setText(l);
				//set Room & Sensor Data +
				
				List <Sensor> RoomSensors = new ArrayList<Sensor>();
				RoomSensors = editRoom.getSensors();
				
				for (int i=0; i<RoomSensors.size(); i++) {
					Sensor s1 = new Sensor();
					s1 = RoomSensors.get(i);
					
					if (i==0) {
						sensorType1.setText(s1.getType());
					} else if (i==1) {
						sensorType2.setText(s1.getType());
					}
				}
				//addSensorToRoom = h1.findRoom(addSensorToRoom.getDescription());
				//addSensorToRoom.addSensor(sensor1);	
			}
		});
		mnSecurity.add(mntmEditRoom);
		
		JMenu mnAboutUs = new JMenu("Info");
		menuBar.add(mnAboutUs);
		
		JMenuItem mntmReadMe = new JMenuItem("Read Me");
		mntmReadMe.addActionListener(new ActionListener() {
				@SuppressWarnings("resource")
				public void actionPerformed(ActionEvent e) {
					 JFileChooser fileChooser = new JFileChooser();
					 fileChooser.setDialogTitle("Save ReadMe.txt to file");
					 fileChooser.setSelectedFile(new File("JLaroco_JZadwick_SecurityProject_ReadMe.txt"));
					 int result = fileChooser.showSaveDialog(null);
					 
					 if (result == JFileChooser.APPROVE_OPTION) {
						 
						String newline = System.getProperty("line.separator");
						
						String content = "Jason Zadwick  & Jonathan Laroco" + newline +
						         "COEN 275" + newline +
						         "README – Document for Home Security Project" + newline + newline +
						         "IDE: Eclipse IDE for Java Developers" + newline +
						         "Version of Java: Java 7 Update 60" + newline +
						         "Tested OS: Mac OSX " + newline + newline +
						         "Program that allows user to create a new house, add rooms and simulate a home security system." + newline + newline +
						         "Please see the “Text File Format” button for more information";
						File file = fileChooser.getSelectedFile();
						 try {
							 FileWriter fw = new FileWriter(file.getPath());
							 fw.write(content);
							 fw.flush();
						 } catch (Exception e2) {
							 JOptionPane.showMessageDialog(null, e2.getMessage());
						 }
						 
					 }
					
				}			
		});
		
		JMenuItem mntmInvoiceSummary = new JMenuItem("Invoice Summary");
		mntmInvoiceSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				List <Room> R1 = new ArrayList<Room>();
				R1 = h1.getRooms();
				List <Event> E1 = new ArrayList<Event>();
				E1 = h1.getEvents();
				
				int totalSensors=0;
				int totalSurfaceArea=0;
				
				for (int i=0; i<R1.size(); i++) {
					Room r = new Room();
					r = R1.get(i);
					List <Sensor> S1 = new ArrayList<Sensor>();
					S1 = r.getSensors();
					totalSensors += S1.size();
					totalSurfaceArea += r.getRoomArea();
				}				
			
				
				GenerateInvoice gi = new GenerateInvoice();
				
				
				double annualFee = gi.getAnnualFee();
				double totalRoom = gi.getTotalRoom(R1.size());
				double totalSensor = gi.getTotalSensor(totalSensors);
				double totalEvent = gi.getTotalEvent(E1.size());
				double totalInvoice = annualFee+totalRoom+totalSensor+totalEvent;
				
				
				
				JOptionPane.showMessageDialog(null, "Invoice Summary:"+"\nCustomer: "+ h1.getCustName() +"\nRoom Total: $" + totalRoom + "\nSensor Total: $" + totalSensor + "\nActivity Total: $" + totalEvent + "\nAnnual Fee: $" + annualFee + "\nInvoice Total: $" + totalInvoice );
			}
		});
		mnAboutUs.add(mntmInvoiceSummary);
		
		JMenuItem mntmExportInvoice = new JMenuItem("Export Invoice");
		mntmExportInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List <Room> R1 = new ArrayList<Room>();
				R1 = h1.getRooms();
				List <Event> E1 = new ArrayList<Event>();
				E1 = h1.getEvents();
				
				int totalSensors=0;
				int totalSurfaceArea=0;
				
				for (int i=0; i<R1.size(); i++) {
					Room r = new Room();
					r = R1.get(i);
					List <Sensor> S1 = new ArrayList<Sensor>();
					S1 = r.getSensors();
					totalSensors += S1.size();
					totalSurfaceArea += r.getRoomArea();
				}				
			
				
				GenerateInvoice gi = new GenerateInvoice();
				
				
				double annualFee = gi.getAnnualFee();
				double totalRoom = gi.getTotalRoom(R1.size());
				double totalSensor = gi.getTotalSensor(totalSensors);
				double totalEvent = gi.getTotalEvent(E1.size());
				double totalInvoice = annualFee+totalRoom+totalSensor+totalEvent;
				
				 JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setDialogTitle("Save Invoice.txt to file");
				 fileChooser.setSelectedFile(new File("HomeSecurityInvoice.txt"));
				 int result = fileChooser.showSaveDialog(null);
				 
				 if (result == JFileChooser.APPROVE_OPTION) {
					 
					String newline = System.getProperty("line.separator");
					String content = "Invoice Details: " + newline + newline +
									 "House Name: " + h1.getName() + newline +
									 "Customer Name: " + h1.getCustName() + newline +
									 "Emergency Phone No 1: " + h1.getPhone1() + newline +
									 "Emergency Phone No 2: " + h1.getPhone2() + newline + newline +
									 "Security Annual Fee (Flat $100): " + annualFee + newline +
									 "Room Fees ($25 per room): " + totalRoom + newline +
									 "Sensor Fees ($25 per sensor): " + totalSensor + newline +
									 "Emergency Fees ($10 per visit): " + totalEvent + newline +
									 "----------------------------------------------------------" + newline +
									 "Total: " + totalInvoice;

					File file = fileChooser.getSelectedFile();
					 try {
						 FileWriter fw = new FileWriter(file.getPath());
						 fw.write(content);
						 fw.flush();
					 } catch (Exception e2) {
						 JOptionPane.showMessageDialog(null, e2.getMessage());
					 }
					 
				 }
			}
		});
		mnAboutUs.add(mntmExportInvoice);
		mnAboutUs.add(mntmReadMe);
		frame.getContentPane().setLayout(null);
		
		JLabel lblHouse = new JLabel("Armed Room Details");
		lblHouse.setBounds(466, 6, 129, 16);
		frame.getContentPane().add(lblHouse);
		
		JLabel lblHouseDetails = new JLabel("House Name");
		lblHouseDetails.setBounds(6, 40, 98, 16);
		frame.getContentPane().add(lblHouseDetails);
		
		houseTXT = new JTextField();
		houseTXT.setBounds(116, 34, 249, 28);
		frame.getContentPane().add(houseTXT);
		houseTXT.setColumns(10);
		
		JLabel lblRoom = new JLabel("Add Room Details");
		lblRoom.setBounds(116, 160, 122, 16);
		frame.getContentPane().add(lblRoom);
		
		roomDescriptionTxt = new JTextField();
		roomDescriptionTxt.setColumns(10);
		roomDescriptionTxt.setBounds(116, 188, 249, 28);
		frame.getContentPane().add(roomDescriptionTxt);
		
		JLabel lblDescription = new JLabel("Name");
		lblDescription.setBounds(6, 194, 98, 16);
		frame.getContentPane().add(lblDescription);
		
		roomWidthTxt = new JTextField();
		roomWidthTxt.setColumns(10);
		roomWidthTxt.setBounds(116, 217, 249, 28);
		frame.getContentPane().add(roomWidthTxt);
		
		roomLengthTxt = new JTextField();
		roomLengthTxt.setColumns(10);
		roomLengthTxt.setBounds(116, 245, 249, 28);
		frame.getContentPane().add(roomLengthTxt);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(6, 223, 98, 16);
		frame.getContentPane().add(lblWidth);
		
		JLabel lblLength = new JLabel("Length");
		lblLength.setBounds(6, 251, 98, 16);
		frame.getContentPane().add(lblLength);
		
		JLabel lblAddSensorTo = new JLabel("Add Sensors To Room");
		lblAddSensorTo.setBounds(116, 285, 145, 16);
		frame.getContentPane().add(lblAddSensorTo);
		
		JLabel lblSensorType = new JLabel("Sensor 1");
		lblSensorType.setBounds(6, 319, 98, 16);
		frame.getContentPane().add(lblSensorType);
		
		sensorType1 = new JTextField();
		sensorType1.setColumns(10);
		sensorType1.setBounds(116, 313, 249, 28);
		frame.getContentPane().add(sensorType1);
		
		sensorType2 = new JTextField();
		sensorType2.setColumns(10);
		sensorType2.setBounds(116, 341, 249, 28);
		frame.getContentPane().add(sensorType2);
		
		JLabel lblSensor = new JLabel("Sensor 2");
		lblSensor.setBounds(6, 347, 98, 16);
		frame.getContentPane().add(lblSensor);
		
		JButton saveEditedRoom = new JButton("Save Edited Room & Sensors");
		saveEditedRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String roomKey = armedList.getSelectedValue().toString();	
				int key = h1.findRoomKey(roomKey);
				
				List <Room> Rooms = new ArrayList<Room>();
				Rooms = h1.getRooms();	
				
				//CREATE SENSOR -
				Sensor sensor1 = new Sensor(sensorType1.getText());
				sensor1.setIsActivated(true);
				Sensor sensor2 = new Sensor(sensorType2.getText());
				sensor2.setIsActivated(true);
				//CREATE SENSOR +
					
				//ADD ROOM -
				Room addRoom = new Room(roomDescriptionTxt.getText(),roomWidthTxt.getText(),roomLengthTxt.getText());
				addRoom.addSensor(sensor1);
				addRoom.addSensor(sensor2);
				
				Rooms.set(key,addRoom);
				
				h1.setRooms(Rooms);
				armedList.setListData(h1.getRoomArray());	//Set List Data		
				//ADD ROOM +
				
				
				//Redisplay List
				
				//Clear Objects
				roomDescriptionTxt.setText("");
				roomWidthTxt.setText("");
				roomLengthTxt.setText("");
				sensorType1.setText("");
				sensorType2.setText("");

			}
		});
		saveEditedRoom.setBounds(6, 381, 359, 35);
		frame.getContentPane().add(saveEditedRoom);
		
		JLabel lblHouseDetails_1 = new JLabel("House Details");
		lblHouseDetails_1.setBounds(126, 6, 98, 16);
		frame.getContentPane().add(lblHouseDetails_1);
		
		JLabel lblCustomerName = new JLabel("Cust. Name");
		lblCustomerName.setBounds(6, 68, 98, 16);
		frame.getContentPane().add(lblCustomerName);
		
		custTXT = new JTextField();
		custTXT.setColumns(10);
		custTXT.setBounds(116, 62, 249, 28);
		frame.getContentPane().add(custTXT);
		
		phone1TXT = new JTextField();
		phone1TXT.setColumns(10);
		phone1TXT.setBounds(116, 91, 249, 28);
		frame.getContentPane().add(phone1TXT);
		
		JLabel lblPhoneNo = new JLabel("Phone No. 1");
		lblPhoneNo.setBounds(6, 97, 98, 16);
		frame.getContentPane().add(lblPhoneNo);
		
		phone2TXT = new JTextField();
		phone2TXT.setColumns(10);
		phone2TXT.setBounds(116, 120, 249, 28);
		frame.getContentPane().add(phone2TXT);
		
		JLabel lblPhoneNo_1 = new JLabel("Phone No. 2");
		lblPhoneNo_1.setBounds(6, 126, 98, 16);
		frame.getContentPane().add(lblPhoneNo_1);
		
		JMenuItem mntmDeleteRoom = new JMenuItem("Delete Room & Sensor");
		mntmDeleteRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String roomKey = armedList.getSelectedValue().toString();			
				int key = h1.findRoomKey(roomKey);

				List <Room> Rooms = new ArrayList<Room>();
				Rooms = h1.getRooms();		
				
				Rooms.remove(key);
				
				h1.setRooms(Rooms);
				armedList.setListData(h1.getRoomArray());	//Set List Data		
				//ADD ROOM +				
				//Clear Objects
				roomDescriptionTxt.setText("");
				roomWidthTxt.setText("");
				roomLengthTxt.setText("");
				sensorType1.setText("");
				sensorType2.setText("");
			}
		});
		mnSecurity.add(mntmDeleteRoom);
		
		JMenu mnImportexport = new JMenu("Import/Export");
		menuBar.add(mnImportexport);
		
		JMenuItem mntmImporthousedata = new JMenuItem("Import House Data");
		mntmImporthousedata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 JFileChooser fileChooser = new JFileChooser();
				 int result = fileChooser.showOpenDialog(null);
				 
				 if (result == JFileChooser.APPROVE_OPTION) {
			        String houseName = null;
			        String custName = null;
			        String phoneNo1 = null;
			        String phoneNo2 = null;
			        
					File file = fileChooser.getSelectedFile();
					FileInputStream fis = null;
				    BufferedInputStream bis = null;
				    DataInputStream dis = null;
				    
				    //create new hashmap here
				    
					 try {
						 fis = new FileInputStream(file);
					     bis = new BufferedInputStream(fis);
					     dis = new DataInputStream(bis);
					     while (dis.available() != 0) {
					    	 	String line = null;
					         // this statement reads the line from the file and print it to
					           // the console.
					           //System.out.println(dis.readLine());
					    	   line = dis.readLine();
					           String[] values = line.split(",");
					           int x = 0;
					           int key = 0;

					           
					           for (String str : values) {
					        	 if (x==0) {
					        		 houseName = str;
					        		 x+=1;
					             } else if (x==1) {
					        		 custName = str;
					        		 x+=1;
					        	 } else if (x==2) {
					        		 phoneNo1 = str;
					        		 x+=1;
					        	 } else if (x==3) {
					        		 phoneNo2 = str;
					        		 x+=1;
					        	 }
					           }					           
					        }
						     //Add items to house object -
						     h1.setName(houseName);
						     h1.setCustName(custName);
						     h1.setPhoneNo1(phoneNo1);
						     h1.setPhoneNo2(phoneNo2);
						     //Add items to house object +
						     
						     //Set House Information on textfields -					     						 
						     houseTXT.setText(houseName);
							 custTXT.setText(custName);
							 phone1TXT.setText(phoneNo1);
							 phone2TXT.setText(phoneNo2);
							 //Set House Information on textfields +

					         // dispose all the resources after using them.
					         fis.close();
					         bis.close();
					         dis.close();
					         JOptionPane.showMessageDialog(null, "Thank you. You have automatically setup your house");
					 } catch (Exception e2) {
						 JOptionPane.showMessageDialog(null, e2.getMessage());
					 }
					 
				 }
			}
		});
		mnImportexport.add(mntmImporthousedata);
		
		JMenuItem mntmExportHouseData = new JMenuItem("ExportedHouseData");
		mntmExportHouseData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setDialogTitle("Export To Text File");
				 fileChooser.setSelectedFile(new File("ExportHouseData.txt"));
				 int result = fileChooser.showSaveDialog(null);
				 
				 if (result == JFileChooser.APPROVE_OPTION) {
					 
					String newline = System.getProperty("line.separator");
					JOptionPane.showMessageDialog(null, "Format: House Name, Customer Name, Phone No. 1, Phone No. 2");
					String content = "";

					content += h1.name+","+h1.custName+","+h1.phoneNo1+","+h1.phoneNo2; 
					
					File file = fileChooser.getSelectedFile();
					 try {
						 FileWriter fw = new FileWriter(file.getPath());
						 fw.write(content);
						 fw.flush();
					 } catch (Exception e2) {
						 JOptionPane.showMessageDialog(null, e2.getMessage());
					 }
					 
				 }
			}
		});
		mnImportexport.add(mntmExportHouseData);
		
		JMenuItem mntmImportRoom = new JMenuItem("Import Room & Sensor Data");
		mntmImportRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 JFileChooser fileChooser = new JFileChooser();
				 int result = fileChooser.showOpenDialog(null);
				 
				 if (result == JFileChooser.APPROVE_OPTION) {
					
			        String roomName = null;
			        String roomWidth = null;
			        String roomLength = null;
			        String sensor1 = null;
			        String sensor2 = null;
			        List <Room> importRoom = new ArrayList<Room>();
			        
					File file = fileChooser.getSelectedFile();
					FileInputStream fis = null;
				    BufferedInputStream bis = null;
				    DataInputStream dis = null;
				    
				    //create new hashmap here
				    
					 try {
						 fis = new FileInputStream(file);
					     bis = new BufferedInputStream(fis);
					     dis = new DataInputStream(bis);
					     while (dis.available() != 0) {
					    	   Room r = new Room();
					    	 
					    	   String line = null;
					    	   line = dis.readLine();
					           String[] values = line.split(",");
					           int x = 0;
					           int key = 0;

					           
					           for (String str : values) {
					        	 if (x==0) {
					        		 roomName = str;
					        		 x+=1;
					             } else if (x==1) {
					        		 roomWidth = str;
					        		 x+=1;
					        	 } else if (x==2) {
					        		 roomLength = str;
					        		 x+=1;
					        	 } else if (x==3) {
					        		 sensor1 = str;
					        		 x+=1;
					        	 } else if (x==4) {
					        		 sensor2 = str;
					        		 x+=1;
					        	 }
					           }
					           r.setDescription(roomName);
					           r.setWidth(Integer.parseInt(roomWidth));
					           r.setLength(Integer.parseInt(roomLength));
					                   
					           //Get Sensors -
					           if (sensor2.equals("BLANK"))  {
					        	   Sensor s1 = new Sensor();
					        	   s1.setType(sensor1);
					        	   s1.setIsActivated(true);
					        	   r.addSensor(s1);
					           } else {
					        	   Sensor s1 = new Sensor();
					        	   s1.setType(sensor1);
					        	   s1.setIsActivated(true);
					        	   Sensor s2 = new Sensor();
					        	   s2.setType(sensor2);
					        	   s2.setIsActivated(true);
					        	   r.addSensor(s1);
					        	   r.addSensor(s2);
					           }
					           //Get Sensors +
					           
					           
					           importRoom.add(r);
					        }
						     h1.setRooms(importRoom);
						     armedList.setListData(h1.getRoomArray());	//Set List Data

					         // dispose all the resources after using them.
					         fis.close();
					         bis.close();
					         dis.close();
					         JOptionPane.showMessageDialog(null, "Thank you. You have automatically setup your rooms & sensors");
					 } catch (Exception e2) {
						 JOptionPane.showMessageDialog(null, e2.getMessage());
					 }
					 
				 }
			}
		});
		mnImportexport.add(mntmImportRoom);
		
		JMenuItem mntmExportRoom = new JMenuItem("Export Room & Sensor Data");
		mntmExportRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setDialogTitle("Export To Text File");
				 fileChooser.setSelectedFile(new File("ExportedRoomSensorData.txt"));
				 int result = fileChooser.showSaveDialog(null);
				 
				 if (result == JFileChooser.APPROVE_OPTION) {
					String newline = System.getProperty("line.separator");
					String content = "";
					
					List <Room> Rooms = new ArrayList<Room>();
					Rooms = h1.getRooms();
					
					for (int i=0; i<Rooms.size(); i++) {
						Room r = new Room();
						List <Sensor> s = new ArrayList<Sensor>();
						r = Rooms.get(i);
						s = r.getSensors();
						
						content += r.getDescription()+","+r.getWidth()+","+r.getLength();
						
						if (s.size() > 1) {
							for (int k=0; k<s.size(); k++) {
								Sensor s1 = new Sensor();
								s1 = s.get(k);
								content += "," + s1.getType();
							}
						} else {
							for (int k=0; k<s.size(); k++) {
								Sensor s1 = new Sensor();
								s1 = s.get(k);
								content += "," + s1.getType()+ ",BLANK";
							}
						}
						content += newline;		
					}					
					JOptionPane.showMessageDialog(null, "Format: Room Name, Width, Length, Sensor 1, Sensor 2");
					File file = fileChooser.getSelectedFile();
					 try {
						 FileWriter fw = new FileWriter(file.getPath());
						 fw.write(content);
						 fw.flush();
					 } catch (Exception e2) {
						 JOptionPane.showMessageDialog(null, e2.getMessage());
					 }
					 
				 }
			}
		});
		mnImportexport.add(mntmExportRoom);
		

	}
}
