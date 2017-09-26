package fx.ui.cell;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;


public class TableDeviceLocationCellFactory<T> implements Callback<TableColumn<T, String>, TableCell<T, String>>
{
	@Override
	public TableCell<T, String> call(TableColumn<T, String> param)
	{
		return new TextFieldTableCell<>(new DefaultStringConverter());
	}
}
