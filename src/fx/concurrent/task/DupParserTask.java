package fx.concurrent.task;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import util.ObjectEventDispatcher;
import data.DupData;
import data.ObservableDataContainer;


public class DupParserTask extends Task<String> 
{
	private StringBuilder buff;
	private File file;
	private Pattern dupPattern = Pattern.compile("([:])+");
	
	public DupParserTask(File log_file) throws EOFException
	{
		file = log_file;
		
		if(Integer.MAX_VALUE < file.length())
			throw new EOFException("File too big to read.");
			
		buff = new StringBuilder((int)file.length());
	}
	
	@Override
	protected String call() throws Exception 
	{		
		try
		{
			LineNumberReader reader = new LineNumberReader(new FileReader(file));
			ObservableList<DupData> tableDataModel = FXCollections.<DupData> observableArrayList();
			Map<String, String> deviceMap = new HashMap<String, String>();
			
			try
			{
				int txtLen = 0;
				
				while(reader.ready())
				{
					String line = reader.readLine();
					String[] group  = dupPattern.split(line.subSequence(0, line.length()));
					
					if(group.length > 0)
					{
						if(group.length == 3 && group[0].equals("data"))
							deviceMap.put(group[1], group[2]);
						else if(group.length == 7 && group[0].equals("dup"))
						{
							String[] devices = new String[2];
							String[] files = new String[2];
							int[] offsets = new int[2];
							
							devices[0] = group[1];
							devices[1] = group[3];
							
							files[0] = group[2];
							files[1] = group[4];
							
							offsets[0] = txtLen;
							offsets[1] = txtLen + line.length();
			
							tableDataModel.add(new DupData(devices, files, Long.parseLong(group[5]), offsets));
						}
					}
					
					txtLen += line.length();
					txtLen++; // increment needed for line feed.
					buff.append(line).append("\n");
				}
			}
			finally
			{
				reader.close();
			}
			
			Map<BooleanPropertyBase, BooleanPropertyBase> booleanPropertyMap  = new HashMap<>();;
			List<BooleanPropertyBase> booleanLeftPropertyList = new ArrayList<>(tableDataModel.size());
			List<BooleanPropertyBase> booleanRightPropertyList = new ArrayList<>(tableDataModel.size());
			
			for(int i = 0; i < tableDataModel.size(); i++)
			{
				BooleanPropertyBase propertyLeft = new SimpleBooleanProperty();
				BooleanPropertyBase propertyRight = new SimpleBooleanProperty();
				
				booleanLeftPropertyList.add(propertyLeft);
				booleanRightPropertyList.add(propertyRight);
				
				booleanPropertyMap.put(propertyLeft, propertyRight);
				booleanPropertyMap.put(propertyRight, propertyLeft);
			}
			
			ObjectEventDispatcher.postObject(new ObservableDataContainer(tableDataModel, booleanLeftPropertyList, booleanRightPropertyList, booleanPropertyMap, deviceMap));
			
			return buff.toString();
		}
		catch(Exception e)
		{
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(stream));
			
			return stream.toString();
		}
	}
}
