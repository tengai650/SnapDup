package fx.concurrent.task;

import java.util.List;
import java.util.Map.Entry;

import data.DupData;
import data.ObservableDataContainer;
import javafx.beans.property.BooleanPropertyBase;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class GenerateScriptTask extends Task<String>
{
	private StringBuilder buff = new StringBuilder(32768);
	private ObservableDataContainer dataContainer;
	private boolean isWindows;
	
	public GenerateScriptTask(ObservableDataContainer container, boolean isWindows)
	{
		this.dataContainer = container;
		this.isWindows = isWindows;
	}

	@Override
	protected String call() throws Exception
	{
		final List<BooleanPropertyBase> leftList = dataContainer.getLeftPropertyList();
		final List<BooleanPropertyBase> rightList = dataContainer.getRigtPropertyList();
		final ObservableList<DupData> dupDataList = dataContainer.getObservableList();
		
		if(isWindows)
			buff.append("@setlocal\n");
		else
			buff.append("#!/bin/sh\n");

		for(Entry<String, String> entry : dataContainer.getDeviceMap().entrySet())
		{
			if(isWindows)
			{
				buff.append("set ");
				buff.append(entry.getKey().toUpperCase()).append("=").append(entry.getValue().replace('/', '\\')).append("\n");
			}
			else
				buff.append(entry.getKey().toUpperCase()).append("=").append(entry.getValue()).append("\n");
		}
		
		for(int i = 0; i < leftList.size(); i++)
		{
			if(leftList.get(i).get())
			{
				if(isWindows)
				{
					buff.append("del \"%").append(dupDataList.get(i).getDevice1().toUpperCase()).append("%");
					buff.append(dupDataList.get(i).getFile1().replace('/', '\\')).append("\"\n");
				}
				else
				{
					buff.append("rm \"${").append(dupDataList.get(i).getDevice1().toUpperCase()).append("}");
					buff.append(dupDataList.get(i).getFile1()).append("\"\n");
				}
			}
			else if(rightList.get(i).get())
			{
				if(isWindows)
				{
					buff.append("del \"%").append(dupDataList.get(i).getDevice2().toUpperCase()).append("%");
					buff.append(dupDataList.get(i).getFile2().replace('/', '\\')).append("\"\n");
				}
				else
				{
					buff.append("rm \"${").append(dupDataList.get(i).getDevice2().toUpperCase()).append("}");
					buff.append(dupDataList.get(i).getFile2()).append("\"\n");
				}
			}
		}
		
		if(isWindows)
		{
			buff.append("@endlocal");
		}
		
		return buff.toString();
	}
}
