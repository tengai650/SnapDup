package fx.ui.listeners;

import data.DupData;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;

public class TableViewSelection implements ChangeListener<DupData>
{
	private TextArea txtArea;

	public TableViewSelection(TextArea txtArea)
	{
		this.txtArea = txtArea;
	}

	@Override
	public void changed(ObservableValue<? extends DupData> observable, DupData oldValue, DupData newValue)
	{
		if(newValue != null)
		{
			Platform.runLater(() -> {
				txtArea.selectRange(newValue.getStartOffset(), newValue.getEndOffset());
			});
		}
	}
}
