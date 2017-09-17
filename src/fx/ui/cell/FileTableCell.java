package fx.ui.cell;

import data.DupData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import tools.Pair;

public class FileTableCell extends TextFieldTableCell<DupData, String>
{
	private ContextMenu tableCellContextMenu;

	public FileTableCell(Callback<Pair<ObservableList<Integer>, Boolean>, Void> callback)
	{
		tableCellContextMenu = new ContextMenu();
		MenuItem menuItem = new MenuItem("Select...");
		menuItem.setOnAction((ActionEvent e) -> {
			TableView<DupData> tableView = getTableView();
			ObservableList<Integer> list = tableView.getSelectionModel().getSelectedIndices();

			callback.call(new Pair<>(list, Boolean.TRUE));
		});

		tableCellContextMenu.getItems().add(menuItem);
		
		menuItem = new MenuItem("UnSelect...");
		menuItem.setOnAction((ActionEvent e) -> {
			TableView<DupData> tableView = getTableView();
			ObservableList<Integer> list = tableView.getSelectionModel().getSelectedIndices();

			callback.call(new Pair<>(list, Boolean.FALSE));
		});
		
		tableCellContextMenu.getItems().add(menuItem);
	}

	@Override
	public void updateItem(String item, boolean empty)
	{
		super.updateItem(item, empty);

		if(!empty)
		{
			setContextMenu(tableCellContextMenu);
		}
	}
}