package fx.ui.cell;

import java.util.List;

import data.DupData;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import util.Pair;

public class FileTableCellFactory implements Callback<TableColumn<DupData, String>, TableCell<DupData, String>>
{
	Callback<Pair<ObservableList<Integer>, Boolean>, Void> callback;
	List<ObjectProperty<OverrunStyle>> propertyList; 
	
	public FileTableCellFactory(Callback<Pair<ObservableList<Integer>, Boolean>, Void> callback, List<ObjectProperty<OverrunStyle>> list)
	{
		this.callback = callback;
		this.propertyList = list;
	}
	
	@Override
	public TableCell<DupData, String> call(TableColumn<DupData, String> column)
	{
		final FileTableCell cell = new FileTableCell(callback);
		propertyList.add(cell.textOverrunProperty());
		return cell;
	}
}
