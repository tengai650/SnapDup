package data;

import java.util.List;
import java.util.Map;

import javafx.beans.property.BooleanPropertyBase;
import javafx.collections.ObservableList;

public class ObservableDataContainer
{
	private ObservableList<DupData> observableList;
	private Map<BooleanPropertyBase, BooleanPropertyBase> booleanPropertyMap;
	private List<BooleanPropertyBase> booleanLeftPropertyList;
	private List<BooleanPropertyBase> booleanRightPropertyList;
	
	private Map<String, String> deviceMap;
	
	public ObservableDataContainer(ObservableList<DupData> list, List<BooleanPropertyBase> leftList, List<BooleanPropertyBase> rightList, Map<BooleanPropertyBase, BooleanPropertyBase> propertyMap, Map<String, String> deviceMap)
	{
		this.observableList = list;
		this.booleanLeftPropertyList = leftList;
		this.booleanRightPropertyList = rightList;
		this.booleanPropertyMap = propertyMap;
		this.deviceMap = deviceMap;
	}
	
	public ObservableList<DupData> getObservableList() { return observableList; }
	public List<BooleanPropertyBase> getLeftPropertyList() { return booleanLeftPropertyList; }
	public List<BooleanPropertyBase> getRigtPropertyList() { return booleanRightPropertyList; }
	public Map<BooleanPropertyBase, BooleanPropertyBase> getPropertyMap() { return booleanPropertyMap; }
	public Map<String, String> getDeviceMap() { return deviceMap; }
}
