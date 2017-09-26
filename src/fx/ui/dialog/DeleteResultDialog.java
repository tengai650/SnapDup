package fx.ui.dialog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DeleteResultDialog implements Initializable
{
	@FXML
	private TextArea txtArea;
	@FXML
	private Button btnOk;
	
	@FXML
	private void btnOKAction()
	{
		Stage stage = (Stage) btnOk.getScene().getWindow();

		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}
}
