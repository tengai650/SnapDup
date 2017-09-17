package fx.ui;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import data.DupData;
import data.GenerateScriptContainer;
import data.ObservableDataContainer;
import fx.concurrent.task.DupParserTask;
import fx.concurrent.task.GenerateScriptTask;
import fx.ui.callbacks.TableCheckBoxCellFactory;
import fx.ui.cell.FileTableCellFactory;
import fx.ui.listeners.TableViewSelection;
import util.ObjectEventDispatcher;
import util.Pair;


public class MainDisplay implements Initializable
{
	@FXML
	private TableView<DupData> dupTableView;
	@FXML
	private Button btnOpen;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnGenerateScript;
	@FXML
	private Button btnAbout;
	@FXML
	private TextArea txtArea;
	@FXML
	private TableColumn<DupData, Boolean> chkOneColumn;
	@FXML
	private TableColumn<DupData, String> deviceOneColumn;
	@FXML
	private TableColumn<DupData, String> fileOneColumn;
	@FXML
	private TableColumn<DupData, Boolean> chkTwoColumn;
	@FXML
	private TableColumn<DupData, String> deviceTwoColumn;
	@FXML
	private TableColumn<DupData, String> fileTwoColumn;
	@FXML
	private TableColumn<DupData, Long> sizeColumn;
		
	ObservableDataContainer dataContainer;
	int totalSelected = 0; // Used to keep track whether to dim btnDelete and btnGenerateScript. When '0' buttons are dimmed.
	
	private class PropertyListener implements ChangeListener<Boolean>
	{
		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
		{
			if(newValue.booleanValue())
			{
				dataContainer.getPropertyMap().get(observable).set(false);
				totalSelected++;
				
				if(totalSelected == 1)
				{
					// btnDelete.setDisable(false);
					btnGenerateScript.setDisable(false);
				}
			}
			else
			{
				if(totalSelected > 0)
					totalSelected--;
				
				if(totalSelected == 0)
				{
					// btnDelete.setDisable(true);
					btnGenerateScript.setDisable(true);
				}
			}
		}
	}
	
	private class CheckBoxLeftCallBack implements Callback<Integer, ObservableValue<Boolean>>
	{
		@Override
		public ObservableValue<Boolean> call(Integer index)
		{
			return dataContainer.getLeftPropertyList().get(index.intValue());
		}
	}
	
	private class CheckBoxRightCallBack implements Callback<Integer, ObservableValue<Boolean>>
	{
		@Override
		public ObservableValue<Boolean> call(Integer index)
		{
			return dataContainer.getRigtPropertyList().get(index.intValue());
		}
	}
	
	private class SelectLeftCallBack implements Callback<Pair<ObservableList<Integer>, Boolean>, Void>
	{
		@Override
		public Void call(Pair<ObservableList<Integer>, Boolean> pair)
		{
			List<BooleanPropertyBase> leftList = dataContainer.getLeftPropertyList();
			
			for(Integer index : pair.first)
	    	{
				if(pair.second.booleanValue())
					leftList.get(index.intValue()).set(true);
				else if(leftList.get(index.intValue()).get())
					leftList.get(index.intValue()).set(false);
	    	}
			
			return null;
		}	
	}
	
	private class SelectRightCallBack implements Callback<Pair<ObservableList<Integer>, Boolean>, Void>
	{
		@Override
		public Void call(Pair<ObservableList<Integer>, Boolean> pair)
		{
			List<BooleanPropertyBase> rightList = dataContainer.getRigtPropertyList();
			
			for(Integer index : pair.first)
	    	{
				if(pair.second.booleanValue())
					rightList.get(index.intValue()).set(true);
				else if(rightList.get(index.intValue()).get())
					rightList.get(index.intValue()).set(false);
	    	}
			
			return null;
		}	
	}
		
	public MainDisplay()
	{
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		ObjectEventDispatcher.addEvent(GenerateScriptContainer.class, data -> objectEvent(data));
		ObjectEventDispatcher.addEvent(ObservableDataContainer.class, data -> objectEvent(data));
		ObjectEventDispatcher.addEvent(ObservableDataContainer.class, data -> objectEvent(data));
		
		dupTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		/* add listener to sync TableView click with ListView */
		dupTableView.getSelectionModel().selectedItemProperty().addListener(new TableViewSelection(txtArea));
		
		/* add callback to left checkbox */
		chkOneColumn.setCellFactory(new TableCheckBoxCellFactory<DupData>(new CheckBoxLeftCallBack()));
		deviceOneColumn.setCellValueFactory(new PropertyValueFactory<DupData, String>("device1"));
		fileOneColumn.setCellFactory(new FileTableCellFactory(new SelectLeftCallBack()));
		fileOneColumn.setCellValueFactory(new PropertyValueFactory<DupData, String>("File1"));
		/* add callback to right checkbox */
		chkTwoColumn.setCellFactory(new TableCheckBoxCellFactory<DupData>(new CheckBoxRightCallBack()));
		deviceTwoColumn.setCellValueFactory(new PropertyValueFactory<DupData, String>("device2"));
		fileTwoColumn.setCellFactory(new FileTableCellFactory(new SelectRightCallBack()));
		fileTwoColumn.setCellValueFactory(new PropertyValueFactory<DupData, String>("File2"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<DupData, Long>("Size"));
		
		chkOneColumn.setId("ChkLeft");
		chkTwoColumn.setId("ChkRight");
		fileOneColumn.setId("FileLeft");
		fileTwoColumn.setId("FileRight");
	}
	
	@FXML
	private void btnOpenAction()
	{
		// String fileName = "\\\\LEAP-MEDIA\\data\\dup.out";
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(null);
		// File file = new File(fileName);
		
		try
		{		
			DupParserTask task = new DupParserTask(file);
			txtArea.textProperty().bind(task.valueProperty());

			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
			
			// btnDelete.setDisable(true);
			btnGenerateScript.setDisable(true);
			totalSelected = 0;
		}
		catch (EOFException e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	private void btnDeleteAction()
	{
		// Not implemented at this time.
	}
	
	@FXML
	private void btnGenerateScriptAction()
	{
		try
		{
		    FXMLLoader loader = new FXMLLoader(MainDisplay.class.getResource("/fxml/GenScriptDialog.fxml"));
		    Parent root = loader.load();
		    Scene scene = new Scene(root);
		    Stage stage = new Stage();
		    
		    stage.initModality(Modality.APPLICATION_MODAL);
		    stage.initStyle(StageStyle.UNDECORATED);
		    stage.setScene(scene);
		    stage.show();
		    
		    Node node = scene.lookup("#txtArea");
		    
		    if(node instanceof TextArea)
		    {
		    	TextArea textArea = (TextArea)node;
		    	boolean isWindows = System.getProperty("os.name").startsWith("Windows");
		    	GenerateScriptTask task = new GenerateScriptTask(dataContainer, isWindows);
		    	
		    	textArea.textProperty().bind(task.valueProperty());
		    	
		    	Node button = scene.lookup((isWindows) ? "#btnWindows" : "#btnLinux");
		    	
		    	if(button instanceof ToggleButton)
		    		((ToggleButton)button).setSelected(true);
		    	
		    	Thread th = new Thread(task);
				th.setDaemon(true);
				th.start();
		    }
		    else
		    	throw new IOException("Unable to find \"TextArea\" node");
		    
		}
		catch(IOException e)
		{
		    e.printStackTrace();
		}
	}
	
	@FXML
	private void btnAboutAction()
	{
		try
		{
		    FXMLLoader loader = new FXMLLoader(MainDisplay.class.getResource("/fxml/AboutDialog.fxml"));
		    Parent root = loader.load();
		    Scene scene = new Scene(root);
		    Stage stage = new Stage();
		    stage.initModality(Modality.APPLICATION_MODAL);
		    stage.initStyle(StageStyle.UNDECORATED);
		    stage.setScene(scene);
		    stage.show();
		}
		catch(IOException e)
		{
		    e.printStackTrace();
		}
	}
	
	private void objectEvent(final ObservableDataContainer container) 
	{
		dataContainer = container; 
		
		Platform.runLater( () -> { 
			PropertyListener propertyListener = new PropertyListener();
			List<BooleanPropertyBase> leftList = dataContainer.getLeftPropertyList();
			List<BooleanPropertyBase> rightList = dataContainer.getRigtPropertyList();
					
			dupTableView.setItems(container.getObservableList());
			
			for(int i = 0; i < leftList.size(); i++)
			{
				leftList.get(i).addListener(propertyListener);
				rightList.get(i).addListener(propertyListener);
			}
		});
	}
	
	private void objectEvent(final GenerateScriptContainer container) 
	{
		GenerateScriptTask task = new GenerateScriptTask(dataContainer, container.isWindows());
    	
		container.getTextArea().textProperty().bind(task.valueProperty());
    	
    	Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}
}
