package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SnapDup extends Application
{
	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
	    BorderPane page = (BorderPane)FXMLLoader.load(SnapDup.class.getResource("/fxml/MainDisplay.fxml"));
	    Scene scene = new Scene(page);
	    primaryStage.setOnCloseRequest((WindowEvent) -> {   Platform.exit(); });
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("SnapDup");
	    primaryStage.show();	
	}
}
