package data;

import javafx.collections.ObservableList;
import util.Pair;

public class DeviceListContainer
{
	public ObservableList<Pair<String, String>> deviceList;
	
	public DeviceListContainer(ObservableList<Pair<String, String>> devices)
	{
		deviceList = devices;
	}
}
