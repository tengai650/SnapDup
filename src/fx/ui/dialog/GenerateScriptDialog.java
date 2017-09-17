package fx.ui.dialog;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import util.ObjectEventDispatcher;
import data.GenerateScriptContainer;

public class GenerateScriptDialog implements Initializable
{
	@FXML
	private TextArea txtArea;
	@FXML
	private Button btnOk;
	@FXML
	private RadioButton btnWindows;
	@FXML
	private RadioButton btnLinux;

	@FXML
	private void btnOKAction()
	{
		Stage stage = (Stage) btnOk.getScene().getWindow();

		stage.close();
	}
	
	@FXML
	private void btnWindowsAction()
	{
		ObjectEventDispatcher.postObject(new GenerateScriptContainer(txtArea, true));
	}
	
	@FXML 
	private void btnLinuxAction()
	{
		ObjectEventDispatcher.postObject(new GenerateScriptContainer(txtArea, false));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}
}
