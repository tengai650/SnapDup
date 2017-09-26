package fx.ui.cell;

import java.text.DecimalFormat;

import data.DupData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class FileSizePropertyFactory implements Callback<TableColumn.CellDataFeatures<DupData, String>,ObservableValue<String>>
{
	static final DecimalFormat dec = new DecimalFormat("0.00");
	final long KB = 0x400L;
	final long MB = 0x100000L;
	final long GB = 0x40000000L;
	final long TB = 0x10000000000L;
	
	@Override
	public ObservableValue<String> call(CellDataFeatures<DupData, String> param)
	{
		final long size = param.getValue().getSize();
		
		SimpleStringProperty value = new SimpleStringProperty();
		
		if(size >= TB)
		{
			double ratio = size / (double)TB;
			value.setValue(dec.format(ratio) + " TB");
		}
		else if(size >= GB)
		{
			double ratio = size / (double)GB;
			value.setValue(dec.format(ratio) + " GB");
		}
		else if(size >= MB)
		{
			double ratio = size / (double)MB;
			value.setValue(dec.format(ratio) + " MB");
		}
		else if(size >= KB)
		{
			double ratio = size / (double)KB;
			value.setValue(dec.format(ratio) + " KB");
		}
		else
		{
			value.setValue(Long.toString(size) + " B");
		}
		
		return value;
	}
}
