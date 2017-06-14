
import java.util.Date;

public class Device {
	
//	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//	Date date = new Date();
//	System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
	
	private String ipAddress;
	private String macAddress;
	private String deviceName;
	private String vendor;
	private Date time;
	
	public Device (String name, String mac){
		this.deviceName = name;
		this.macAddress = mac;
	}
	
	public Device(String ip, String mac, Date time){
		this.ipAddress = ip;
		this.macAddress = mac;
		this.time = time;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
