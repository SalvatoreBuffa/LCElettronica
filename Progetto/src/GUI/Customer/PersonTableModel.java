package GUI.Customer;



import Core.Customer.Customer;
import Core.Customer.CustomerManager;
import Core.Common.Item;
import Core.Common.SettingName;
import Core.Customer.CustomerMemento;
import GUI.Common.ItemTableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che modellizza la struttura della tabella contenente le informazioni sui clienti di tipo persona
 * Viene implementata la classe astratta 'ItemTableModel':{@link GUI.Common.ItemTableModel}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class PersonTableModel extends ItemTableModel {
    //lista di colonne e righe
    ArrayList<String> columns = new ArrayList<>();
    ArrayList<Customer> rows = new ArrayList<>();

    //manager del cliente, memento e lista di clienti
    CustomerManager manager;
    CustomerMemento memento;
    List<Customer> customers;

    /**
     * Costruttore
     * @param manager manager su cui eseguire le operazioni
     */
    public PersonTableModel(CustomerManager manager){
        //associamo il manager e la lista di clienti
        this.manager = manager;
        this.customers = CustomerManager.getList();

        //creiamo l'header della tabella
        createFirstRow();

        //aggiungiamo la lista di clienti persone alle righe
        for(Customer c: customers){
            if(c.getType().equals(SettingName.PERSONTYPE)){
                rows.add(c);
            }
        }
    }

    /**
     * Metodo che crea l'header della tabella
     */
    private void createFirstRow(){
        columns.add(SettingName.PERSONNAME);
        columns.add(SettingName.PERSONSURNAME);
        columns.add(SettingName.PERSONPERSONALID);
        columns.add(SettingName.CUSTOMERPIVA);
        columns.add(SettingName.PERSONCODESDI);
        columns.add(SettingName.CUSTOMERMAIL);
        columns.add(SettingName.CUSTOMERPEC);
        columns.add(SettingName.CUSTOMERTELEPHONE);
        columns.add(SettingName.CUSTOMERADDRESS);
        columns.add(SettingName.CUSTOMERSHIPPINGADDRESS);
        columns.add("operation");
    }

    /**
     * Metodo ereditato da 'ItemTableModel':{@link GUI.Common.ItemTableModel} che restituisce l'item corrispondende alla riga
     * @param row l'intero che identifica la riga
     * @return L'item riferito alla riga row-esima
     */
    @Override
    public Item getItemByRow(int row){
        return rows.get(row);
    }

    /**
     * Metodo ereditato da 'ItemTableModel':{@link GUI.Common.ItemTableModel} che inserisce un item nella lista e nel manager
     * @param i Item da inserire
     */
    @Override
    public void insert(Item i) {
        rows.add((Customer) i);
        manager.create((Customer) i);
        fireTableDataChanged();
    }

    /**
     * Metodo ereditato da 'ItemTableModel':{@link GUI.Common.ItemTableModel} che rimuove un elemnto della lista e nel manager
     * @param ID identificativo dell'elemento da rimuovere
     */
    @Override
    public void delete(int ID){
        rows.remove(manager.searchElement(ID));
        manager.delete(ID);
        fireTableDataChanged();

    }

    /**
     * Metodo ereditato da 'ItemTableModel':{@link GUI.Common.ItemTableModel} che aggiorna un elemento della lista e nel manager
     * @param oldID Identificativo del vecchio elemento da aggiornare
     * @param newItem Nuovo elemento da sostituire al vecchio
     */
    @Override
    public void update(int oldID, Item newItem) {
        int index = rows.indexOf(manager.searchElement(oldID));
        //System.out.println("updating "+oldID+" with "+(Customer) newItem);
        manager.update(oldID, (Customer) newItem);
        rows.set(index, manager.searchElement(oldID));
        fireTableDataChanged();
    }

    /**
     * Metodo ereditato da 'ItemTableModel':{@link GUI.Common.ItemTableModel} che annulla l'ultima operazione eseguita
     */
    @Override
    public void undo(){
        //ricaviamo il memento
        this.memento = manager.getMemento();

        //in base al tipo di operazione eseguita la ripristiniamo
        if(memento.getOperation().equals(SettingName.OPERATIONCREATE)){
            rows.removeIf(c -> c.getID() == this.memento.getState().getID());
            manager.restoreCreate();
        }
        if(memento.getOperation().equals(SettingName.OPERATIONDELETE)){
            rows.add(memento.getState());
            manager.restoreDelete();
        }
        if(memento.getOperation().equals(SettingName.OPERATIONUPDATE)){
            rows.set(customers.indexOf(manager.searchElement(this.memento.getState().getID())), this.memento.getState());
            manager.restoreUpdate();
        }

        fireTableDataChanged();
    }

    /**
     * Metodo ereditato da 'ItemTableModel':{@link GUI.Common.ItemTableModel} che popola la lista con gli elementi presenti in un file CSV
     * @param filePath percorso del file .csv
     */
    @Override
    public void importCSV(String filePath) {
        manager.importData(filePath, SettingName.PERSONTYPE);
        this.customers = CustomerManager.getList();
        rows.clear();

        rows.addAll(customers);

        fireTableDataChanged();
    }

    /**
     * Metodo ereditato da 'AbstractTableModel':{@link javax.swing.table.AbstractTableModel}
     * @return Il numero di righe
     */
    @Override
    public int getRowCount() {
        return rows.size();
    }

    /**
     * Metodo ereditato da 'AbstractTableModel':{@link javax.swing.table.AbstractTableModel}
     * @return Il numero di colonne
     */
    @Override
    public int getColumnCount() {
        return columns.size();
    }

    /**
     * Metodo ereditato da 'AbstractTableModel':{@link javax.swing.table.AbstractTableModel} che dato un indice
     * restituisce il nome della colonna i-esima
     * @param col l'indice della colonna
     * @return Il nome della colonna
     */
    @Override
    public String getColumnName(int col) {
        return columns.get(col);
    }

    /**
     * Metodo ereditato da 'AbstractTableModel':{@link javax.swing.table.AbstractTableModel} che date due coordinate
     * restituisce il valore nella cella [rowIndex][columnIndex]
     * @param rowIndex indice della riga
     * @param columnIndex indice della colonna
     * @return valore nella cella [rowIndex][columnIndex]
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item i = rows.get(rowIndex);
        String value = "Dettagli";
        switch (columnIndex){
            case (0):
                return i.get(SettingName.PERSONNAME);
            case (1):
                return i.get(SettingName.PERSONSURNAME);
            case (2):
                return i.get(SettingName.PERSONPERSONALID);
            case (3):
                return i.get(SettingName.CUSTOMERPIVA);
            case (4):
                return i.get(SettingName.PERSONCODESDI);
            case (5):
                return i.get(SettingName.CUSTOMERMAIL);
            case (6):
                return i.get(SettingName.CUSTOMERPEC);
            case (7):
                return i.get(SettingName.CUSTOMERTELEPHONE);
            case (8):
                return i.get(SettingName.CUSTOMERADDRESS);
            case (9):
                return i.get(SettingName.CUSTOMERSHIPPINGADDRESS);
        }
        return value;
    }

    /**
     * Metodo ereditato da 'AbstractTableModel':{@link javax.swing.table.AbstractTableModel} che date due coordinate restituisce
     * se la cella [row][col] è modificabile
     * @param row indice della riga
     * @param col indice della tabella
     * @return true se la cella [row][col] è modificabile, false altrimenti
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //le nostre celle sono tutte non mobificabili, eccetto l'ultima contenente il bottone per visualizzare i dettagli
        if(col == columns.size()-1) return true;
        return false;
    }
}
