package fx.ui.callbacks;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

public class TableCheckBoxCellFactory<T> implements Callback<TableColumn<T, Boolean>, TableCell<T, Boolean>>
{
	Callback<Integer, ObservableValue<Boolean>> callback;
	
	public TableCheckBoxCellFactory(Callback<Integer, ObservableValue<Boolean>> callback)
	{
		this.callback = callback;
	}
	
	@Override
	public TableCell<T, Boolean> call(TableColumn<T, Boolean> column)
	{
		CheckBoxTableCell<T, Boolean> cell = new CheckBoxTableCell<>();
		
		cell.setSelectedStateCallback(callback);
		
		return cell;
	}
}