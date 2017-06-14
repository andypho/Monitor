
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Read {

	private static final Pattern PATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public static boolean validate1(final String ip) {
	    return PATTERN.matcher(ip).matches();
	}
	
	private FirebaseService firebaseservice;
	private Map<String, Device> devicesMap;
	private ArrayList<Device> devicesNameList;
	private Runnable r;

	public Read(){
		firebaseservice = new FirebaseService();
		devicesMap = new HashMap<String, Device>();
		devicesNameList = new ArrayList<>(); 
		
//		Device one = new Device("192.168.1.1","11:22:33:44:55:66", new Date());
//		Device two = new Device("192.168.1.2","11:22:33:44:55:99", new Date());
//		users.put(one.getMacAddress(), one);
//		users.put(two.getMacAddress(), two);
//		firebaseservice.writeToDatabase(users);

		/*
		r = new Runnable(){
			public void run() {
				System.out.println("hello");
			
		}};
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(r, 0, 3, TimeUnit.SECONDS);
		*/
		
		readARP();
		
		r = new Runnable(){
			public void run() {
				readARP_SCAN();
				firebaseservice.writeToDatabase(devicesMap);
		}};
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(r, 0, 3, TimeUnit.SECONDS);
		

	}
	
	public static void main(String[] args) {
		Read read = new Read();
	}
	
	public void readARP_SCAN(){
		try {
//			File file = new File("C:/Users/Andy ho/Desktop/testing.txt");
//			BufferedReader br = new BufferedReader(new FileReader(file));

			Process p = Runtime.getRuntime().exec("sudo arp-scan --localnet");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";

			while ((line = br.readLine()) != null) {
			    String[] splitArray = line.split("\\s+");
			    if(splitArray[0].contains("Interface")){
//			    	System.out.println("interface true");
			    	
			    } else if (validate1(splitArray[0])){
//			    	System.out.println(splitArray[0] + " " + splitArray[1] + " " + splitArray[2]);
			    	Device device = new Device(splitArray[0], splitArray[1], new Date());
			    	for(int i = 0; i < devicesNameList.size(); i++){
			    		if(devicesNameList.get(i).getMacAddress().matches(device.getMacAddress())){
//			    			System.out.println("MATCH");
				    		if(device.getDeviceName() == null || device.getDeviceName().isEmpty()){
				    			device.setDeviceName(devicesNameList.get(i).getDeviceName());
				    		}
			    		}
			    	}
			    	devicesMap.put(splitArray[1], device);
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readARP(){
		try {
//			File file = new File("C:/Users/Andy ho/Desktop/testing1.txt");
//			BufferedReader br = new BufferedReader(new FileReader(file));

			Process p = Runtime.getRuntime().exec("arp");
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line = "";

			while ((line = br.readLine()) != null) {
			    String[] splitArray = line.split("\\s+");
			    if(splitArray[0].contains("Address")){
//			    	System.out.println("Address true");
			    } else {
//			    	System.out.println(splitArray[0] + " /" + splitArray[2]);
			    	Device device = new Device(splitArray[0], splitArray[2]);
			    	devicesNameList.add(device);
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
