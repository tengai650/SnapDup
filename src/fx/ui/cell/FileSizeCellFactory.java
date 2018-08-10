package fx.ui.cell;

import java.text.DecimalFormat;

import data.DupData;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class FileSizeCellFactory implements Callback<TableColumn<DupData, Long>, TableCell<DupData, Long>> {

    static final DecimalFormat dec = new DecimalFormat("0.00");
    final static long KB = 0x400L;
    final static long MB = 0x100000L;
    final static long GB = 0x40000000L;
    final static long TB = 0x10000000000L;

    private static final String formatSize(String size) {
        Long lSize = Long.parseLong(size);
        SimpleStringProperty value = new SimpleStringProperty();

        if (lSize >= TB) {
            double ratio = lSize / (double) TB;
            value.setValue(dec.format(ratio) + " TB");
        } else if (lSize >= GB) {
            double ratio = lSize / (double) GB;
            value.setValue(dec.format(ratio) + " GB");
        } else if (lSize >= MB) {
            double ratio = lSize / (double) MB;
            value.setValue(dec.format(ratio) + " MB");
        } else if (lSize >= KB) {
            double ratio = lSize / (double) KB;
            value.setValue(dec.format(ratio) + " KB");
        } else {
            value.setValue(size + " B");
        }
        return value.getValue();
    }

    @Override
    public TableCell<DupData, Long> call(TableColumn<DupData, Long> column) {

        return new TableCell<DupData, Long>() {

            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(formatSize(item.toString()));
                }
            }
        };

    }

}
