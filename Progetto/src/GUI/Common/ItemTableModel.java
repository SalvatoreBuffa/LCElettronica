package GUI.Common;



import Core.Common.Item;

import javax.swing.table.AbstractTableModel;

/**
 * Classe che modellizza la struttura della tabella contenente le informazioni su un elemento generico della tabella
 * Viene implementata la classe astratta 'AbstractTableModel':{@link javax.swing.table.AbstractTableModel}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public abstract class ItemTableModel extends AbstractTableModel {
    public abstract Item getItemByRow(int row);
    public abstract void insert(Item i);
    public abstract void delete(int ID);
    public abstract void update(int oldID, Item newItem);
    public abstract void undo();
    public abstract void importCSV(String filePath);
}
