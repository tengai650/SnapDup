package fx.ui.dialog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutDialog implements Initializable
{
	@FXML
	private Button btnClose;

	@FXML
	private void btnCloseAction()
	{
		Stage stage = (Stage) btnClose.getScene().getWindow();

		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

	}
}
