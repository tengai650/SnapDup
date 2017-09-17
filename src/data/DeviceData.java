package data;

public class DeviceData
{
	private String device;
	private String path;
	
	public DeviceData(String device, String path)
	{
		this.device = device;
		this.path = path;
	}
	
	public String getDevice() { return device; }
	public String getPath() { return path; }
}
