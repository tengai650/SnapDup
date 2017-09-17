package data;

import java.util.Map;

public class DeviceMapContainer
{
	private Map<String, String> deviceMap;
	
	public DeviceMapContainer(Map<String, String> devices)
	{
		deviceMap = devices;
	}
	
	public Map<String, String> getDeviceMap() { return deviceMap; }
}
