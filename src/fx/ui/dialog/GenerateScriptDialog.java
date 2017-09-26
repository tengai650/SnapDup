package fx.ui.dialog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
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
	private Button btnSave;
	@FXML
	private RadioButton btnWindows;
	@FXML
	private RadioButton btnLinux;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}
	
	@FXML
	private void btnOKAction()
	{
		Stage stage = (Stage) btnOk.getScene().getWindow();

		stage.close();
	}
	
	@FXML
	private void btnSaveAction()
	{
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showSaveDialog(btnSave.getScene().getWindow());
		
		if(file == null)
			return;
		
		try
		{
			FileWriter stream = new FileWriter(file);
			
			try
			{
				file.createNewFile();
				stream.write(txtArea.getText());
			}
			finally
			{
				stream.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML 
	private void btnLinuxAction()
	{
		ObjectEventDispatcher.postObject(new GenerateScriptContainer(txtArea, false));
	}
	
	@FXML 
	private void btnWindowsAction()
	{
		ObjectEventDispatcher.postObject(new GenerateScriptContainer(txtArea, true));
	}
}
