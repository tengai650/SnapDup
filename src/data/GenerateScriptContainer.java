package data;

import javafx.scene.control.TextArea;

public class GenerateScriptContainer
{
	private TextArea txtArea;
	private boolean isWindows;
	
	public GenerateScriptContainer(TextArea txtArea, boolean isWindows)
	{
		this.txtArea = txtArea;
		this.isWindows = isWindows;
	}
	
	public TextArea getTextArea() { return txtArea; }
	public boolean isWindows() { return isWindows; }
}
