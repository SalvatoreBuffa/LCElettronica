package GUI.Common;



import Core.Activity.Activity;
import Core.Customer.Customer;
import Core.Common.Item;
import Core.Product.Product;
import Core.Repair.Repair;
import GUI.Activity.ActivityDetailFrame;
import GUI.Customer.CustomerDetailFrame;
import GUI.Product.ProductDetailFrame;
import GUI.Repair.RepairDetailFrame;

import javax.swing.*;
import java.awt.Component;

/**
 * Classe che crea il bottone e la relativa azione dopo essere stato disegnato da 'TableButtonRenderer':{@link GUI.Common.TableButtonRenderer}
 * L'azione consiste nel creare una finestra 'Dettagli' contenente le informazioni di un 'Item':{@link Core.Common.Item}
 * Viene la classe astratta 'DefaultCellEditor':{@link javax.swing.DefaultCellEditor}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class ButtonEditor extends DefaultCellEditor {
    //elemento di cui vogliamo visualizzare i dettagli e il bottone associato
    private Item i;
    private JButton button;

    //testo del bottone
    private String label;

    /**
     * Costruttore, Purtroppo la classe Java 'DefaultCellEditor':{@link javax.swing.DefaultCellEditor} non ha il costruttore di Default e ho dovuto inserire una checkbox fittizia
     * @param checkBox checkbox fittizia richiesta dal costruttore
     */
    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        //creo il bottone
        button = new JButton();
        button.setOpaque(true);
        /*
        * Al click, dobbiamo chiamare questo metodo del DefaultCellEditor che fa sapere alla tabella che può disabilitare l'editor
        * per far sì che possa essere usato un altro bottone della tabella, altrimenti l'editor di uno specifico bottone rimarrebbe
        * attivo anche se la finestra di dettagli è chiusa
        */
        button.addActionListener(e -> fireEditingStopped());
    }

    /**
     * Overriding del metodo 'getTableCellEditorComponent':{@link javax.swing.DefaultCellEditor#getTableCellEditorComponent(JTable, Object, boolean, int, int)}
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        ItemTableModel model = (ItemTableModel) table.getModel();
        i = model.getItemByRow(row);

        if(i instanceof Customer){
            CustomerDetailFrame.buildFrame(i, model);
        }
        if(i instanceof Product){
            ProductDetailFrame.buildFrame(i, model);
        }
        if(i instanceof Repair){
            RepairDetailFrame.buildFrame(i, model);
        }
        if(i instanceof Activity){
            ActivityDetailFrame.buildFrame(i, model);
        }


        label = (value == null) ? "" : value.toString();
        button.setText(label);

        return button;
    }

    /**
     * Concretizzazione del metodo 'fireEditingStopped':{@link DefaultCellEditor#fireEditingStopped()}
     */
    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
