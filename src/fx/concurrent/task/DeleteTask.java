package fx.concurrent.task;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import data.DupData;
import data.ObservableDataContainer;
import javafx.beans.property.BooleanPropertyBase;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class DeleteTask extends Task<String>
{
	private static final StringBuilder buff = new StringBuilder(32768);
	private ObservableDataContainer dataContainer;
	
	public DeleteTask(ObservableDataContainer container)
	{
		this.dataContainer = container;
	}

	@Override
	protected String call() throws Exception
	{
		final List<BooleanPropertyBase> leftList = dataContainer.getLeftPropertyList();
		final List<BooleanPropertyBase> rightList = dataContainer.getRigtPropertyList();
		final ObservableList<DupData> dupDataList = dataContainer.getObservableList();
		final Map<String, String> deviceMap = dataContainer.getDeviceMap();
				
		FileSystem fs = FileSystems.getDefault();
		
		buff.setLength(0);
		
		for(int i = 0; i < leftList.size(); i++)
		{
			final boolean leftSelected = leftList.get(i).get();
			final boolean rightSelected = rightList.get(i).get();
			
			if(leftSelected || rightSelected)
			{
				final DupData data = dupDataList.get(i);
				final Path fileName1 = fs.getPath(deviceMap.get(data.getDevice1()),  data.getFile1());
				final Path fileName2 = fs.getPath(deviceMap.get(data.getDevice2()), data.getFile2());
				
				File file1 = fileName1.toFile();
				File file2 = fileName2.toFile();
				
				if(file1.isFile() && file2.isFile()) // both files must exist in order to delete one.
				{
					if(leftSelected)
					{
						if(file1.delete())
							buff.append("Deleted file (1): ").append(fileName1).append("\n");
						else
							buff.append("Failed to delete file (2): ").append(fileName1).append("\n");
					}
					else if(rightSelected)
					{
						if(file2.delete())
							buff.append("Deleted file (2): ").append(fileName2).append("\n");
						else
							buff.append("Failed to delete file (2): ").append(fileName2).append("\n");
					}
				}
				else
				{
					if(!file1.exists())
						buff.append("File (1) does not exist: ").append(fileName1).append("\n");
					
					if(!file2.exists())
						buff.append("File (2) does not exist: ").append(fileName2).append("\n");
				}
			}
		}
		
		return buff.toString();
	}
}
