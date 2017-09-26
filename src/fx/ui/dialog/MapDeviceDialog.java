package fx.ui.dialog;

import java.net.URL;
import java.util.ResourceBundle;

import data.DeviceListContainer;
import fx.ui.cell.TableDeviceLocationCellFactory;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.ObjectEventDispatcher;
import util.Pair;

public class MapDeviceDialog implements Initializable
{
	@FXML
	private TableColumn<Pair<String, String>, String> deviceNameColumn;
	@FXML
	private TableColumn<Pair<String, String>, String> deviceLocationColumn;
	@FXML
	private TableView<Pair<String, String>> tblMapDevice;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancel;
	
	class EditCommit implements EventHandler<TableColumn.CellEditEvent<Pair<String, String>, String>>
	{
		@Override
		public void handle(CellEditEvent<Pair<String, String>, String> event)
		{
			ObservableList<Pair<String, String>> deviceList = tblMapDevice.getItems();
			Pair<String, String> pair = event.getRowValue();
			
			int index = deviceList.indexOf(pair);
			
			deviceList.set(index, new Pair<String, String>(pair.first, event.getNewValue()));
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		deviceNameColumn.setCellValueFactory(new PropertyValueFactory<Pair<String, String>, String>("First"));
		deviceLocationColumn.setCellValueFactory(new PropertyValueFactory<Pair<String, String>, String>("Second"));
		deviceLocationColumn.setCellFactory(new TableDeviceLocationCellFactory<Pair<String, String>>());
		
		deviceLocationColumn.setOnEditCommit(new EditCommit());
	}
	
	@FXML
	private void btnCancelAction()
	{
		Stage stage = (Stage) btnCancel.getScene().getWindow();

		stage.close();
	}
	
	@FXML
	private void btnOKAction()
	{
		Stage stage = (Stage) btnOk.getScene().getWindow();
		
		DeviceListContainer container = new DeviceListContainer(tblMapDevice.getItems());
		
		ObjectEventDispatcher.postObject(container);
		
		stage.close();
	}
}
