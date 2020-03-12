package GUI.Common;



import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Classe renderizza un bottone nella cella di una 'JTable':{@link javax.swing.JTable}
 * Viene estesa la classe 'JButton':{@link javax.swing.JButton} e implementata l'interfaccia' 'TableCellRenderer':{@link javax.swing.table.TableCellRenderer}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class TableButtonRenderer extends JButton implements TableCellRenderer {

    /**
     * Costruttore
     */
    public TableButtonRenderer() {
        setOpaque(true);
    }

    /**
     * Concretizzazione del metodo 'getTableCellRendererComponent':{@link javax.swing.table.TableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)}
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}
