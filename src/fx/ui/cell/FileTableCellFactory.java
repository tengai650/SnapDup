package fx.ui.cell;

import data.DupData;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import util.Pair;

public class FileTableCellFactory implements Callback<TableColumn<DupData, String>, TableCell<DupData, String>>
{
	Callback<Pair<ObservableList<Integer>, Boolean>, Void> callback;
	
	public FileTableCellFactory(Callback<Pair<ObservableList<Integer>, Boolean>, Void> callback)
	{
		this.callback = callback;
	}
	
	@Override
	public TableCell<DupData, String> call(TableColumn<DupData, String> column)
	{
		return new FileTableCell(callback);
	}
}
