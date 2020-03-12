package GUI.Customer;



import Core.Common.UserLogManager;
import Core.Customer.*;
import Core.Common.SettingName;
import GUI.Common.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

/**
 * Classe che crea il pannello contenente la lista di clienti memorizzati ed i vari bottoni per eseguire operazioni su di essi
 * Viene implementata l'interfaccia 'JPanel':{@link javax.swing.JPanel}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class CustomerPanel extends JPanel {
    //manager di clienti
    CustomerManager manager;
    //booleano che identifica il tipo di cliente (Persona/Azienda)
    private boolean isPerson;

    //rispettivamente i sotto-pannelli con la search bar, le liste e i bottoni
    private JPanel filterPanel;
    //essendo presenti due liste, una per le persone e una per le aziende si hanno due modelli che cambiano su un CardPanel
    private JScrollPane personListPanel;
    private JScrollPane companyListPanel;
    private JPanel cardPanel;
    private CardLayout cards;
    private JPanel buttonPanel;

    //rispettivamente i modelli delle tabelle, i sorter utilizzati per ricerca e ordinamento e la barra di ricerca
    private ItemTableModel personModel;
    private ItemTableModel companyModel;
    private TableRowSorter<ItemTableModel> companySorter;
    private TableRowSorter<ItemTableModel> personSorter;
    private JTextField filter;

    //bottoni per le varie operazioni
    private JButton addButton;
    private JButton undoButton;
    private JButton exportButton;
    private JButton importButton;

    //radio buttons per passare dalla tabella persone alla tabella aziende e viceversa
    private JRadioButton choisePerson;
    private JRadioButton choiseCompany;

    /**
     * Costruttore
     */
    public CustomerPanel(){
        //creiamo il manager
        manager = new CustomerManager();
        isPerson = true;

        //settiamo il border layout
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);

        //creiamo il card panel che conterrà le due liste
        cardPanel = new JPanel();
        cards = new CardLayout();
        cardPanel.setLayout(cards);

        //carichiamo la lista di attività salvate precedentemente
        List<Customer> list = CustomerBackup.restoreList();
        manager.setList(list);

        //renderizziamo i radio button e la barra di ricerca
        filterPanel = new JPanel(new FlowLayout());
        renderFilterPanel();

        //creo le tabelle persone/aziende
        personListPanel = new JScrollPane(renderPersonTable());
        companyListPanel = new JScrollPane(renderCompanyTable());

        //le aggiungo al cardpanel identidicate da una stringa tipo
        cardPanel.add(personListPanel, SettingName.PERSONTYPE);
        cardPanel.add(companyListPanel, SettingName.COMPANYTYPE);

        //se è una persona mostriamo la card contenente la tabella persona, altrimenti quella con le aziende
        if(isPerson){
            cards.show(cardPanel, SettingName.PERSONTYPE);
        }else{
            cards.show(cardPanel, SettingName.COMPANYTYPE);
        }

        //creo i bottoni
        buttonPanel = new JPanel();
        renderOperationButtons();
        //aggiungo i tre sotto-pannelli al pannello principale
        add(filterPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Metodo che renderizza la barra di ricerca ed i radio button per passare da una tabella all'altra
     */
    private void renderFilterPanel(){
        //creiamo i due radio button
        choisePerson = new JRadioButton(SettingName.GUIRADIOPERSON);
        choiseCompany = new JRadioButton(SettingName.GUIRADIOCOMPANY);
        //di dafault selezioniamo la tabella persone
        choisePerson.setSelected(true);

        //creiamo il pannello con la barra di ricerca
        filterPanel.add(new JLabel(SettingName.GUISEARCH+":"));
        filter = new JTextField(30);
        filterPanel.add(filter);

        //aggiungiamo i radio button al pannello della riceca
        filterPanel.add(choisePerson);
        filterPanel.add(choiseCompany);

        //creiamo il gruppo dei due radio button
        ButtonGroup group = new ButtonGroup();
        group.add(choisePerson);
        group.add(choiseCompany);

        //se clicchiamo sul radio button delle persone, mostriamo la card con la tabella delle persone
        choisePerson.addActionListener(e -> {
            isPerson = true;
            cards.show(cardPanel, SettingName.PERSONTYPE);
        });

        //se clicchiamo sul radio button delle aziende, mostriamo la card con la tabella delle aziende
        choiseCompany.addActionListener(e -> {
            isPerson = false;
            cards.show(cardPanel, SettingName.COMPANYTYPE);
        });
    }

    /**
     * Metodo che crea la tabella persone, aggiunge il sorter ed il listner e la restituisce
     * @return tabella da renderizzare nel list panel
     */
    private JTable renderPersonTable(){
        //creiamo il modello
        personModel = new PersonTableModel(manager);

        //aggiungiamo al modello un listner, per abilitare/disabilitare alcuni bottoni in base ai cambiamenti
        personModel.addTableModelListener(e -> {
            //se la lista è vuota disabilitiamo il tasto esporta, e viceversa
            if(CustomerManager.getList().isEmpty()) {
                exportButton.setEnabled(false);
            }else{
                exportButton.setEnabled(true);
            }

            //se è presente un Memento abilitiamo il tasto undo
            if(manager.getMemento().getState() != null){
                undoButton.setEnabled(true);
            }
        });

        //creiamo il sorter utilizzato per ricerca ed ordinamento
        personSorter = new TableRowSorter<>(personModel);
        JTable table = new JTable(personModel);
        table.setRowSorter(personSorter);

        //aggiungiamo alla text field della ricerca il listner, ad ogni cambiamento aggiorna la tabella
        filter.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                personFilter();
            }
            public void insertUpdate(DocumentEvent e) {
                personFilter();
            }
            public void removeUpdate(DocumentEvent e) {
                personFilter();
            }
        });

        //aggiungiamo nell'ultima colonna il tasto "Dettagli"
        table.getColumn("operation").setCellRenderer(new TableButtonRenderer());
        table.getColumn("operation").setCellEditor(new ButtonEditor(new JCheckBox()));

        //settiamo delle impostazioni grafiche
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setCellSelectionEnabled(true);
        table.setFillsViewportHeight(true);

        //restituiamo
        return table;
    }

    /**
     * Metodo che crea la tabella aziende, aggiunge il sorter ed il listner e la restituisce
     * @return tabella da renderizzare nel list panel
     */
    private JTable renderCompanyTable(){
        //creiamo il modello
        companyModel = new CompanyTableModel(manager);

        //aggiungiamo al modello un listner, per abilitare/disabilitare alcuni bottoni in base ai cambiamenti
        companyModel.addTableModelListener(e -> {
            //se la lista è vuota disabilitiamo i tasti mostra e esporta, e viceversa
            if(CustomerManager.getList().isEmpty()) {
                exportButton.setEnabled(false);
            }else{
                exportButton.setEnabled(true);
            }

            //se è presente un Memento abilitiamo il tasto undo
            if(manager.getMemento().getState() != null){
                undoButton.setEnabled(true);
            }
        });

        //creiamo il sorter utilizzato per ricerca ed ordinamento
        companySorter = new TableRowSorter<>(companyModel);
        JTable table = new JTable(companyModel);
        table.setRowSorter(companySorter);

        //aggiungiamo alla text field della ricerca il listner, ad ogni cambiamento aggiorna la tabella
        filter.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                companyFilter();
            }
            public void insertUpdate(DocumentEvent e) {
                companyFilter();
            }
            public void removeUpdate(DocumentEvent e) {
                companyFilter();
            }
        });

        //aggiungiamo nell'ultima colonna il tasto "Dettagli"
        table.getColumn("operation").setCellRenderer(new TableButtonRenderer());
        table.getColumn("operation").setCellEditor(new ButtonEditor(new JCheckBox()));

        //settiamo delle impostazioni grafiche
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setCellSelectionEnabled(true);
        table.setFillsViewportHeight(true);

        //restituiamo
        return table;
    }

    /**
     * Metodo che renderizza i bottoni per le varie operazioni
     */
    private void renderOperationButtons(){
        //creiamo il bottone "Inserisci" che al click apre il frame per inserire un cliente
        addButton = ButtonFactory.createButton(SettingName.GUIINSERT, e -> {
            //se è una persona la creiamo nel modello persone, altrimenti nel modello aziende
            if(isPerson){
                CustomerInsertFrame.buildFrame(SettingName.PERSONTYPE, personModel);
            }else{
                CustomerInsertFrame.buildFrame(SettingName.COMPANYTYPE, companyModel);
            }
        });

        //creiamo il bottone "Esporta" e lo settiamo false se la lista è vuota
        exportButton = ButtonFactory.createButton(SettingName.GUIEXPORT, e -> {
            FilePicker picker = new FilePicker();
            picker.setMode("save");
            //se è una persona esportiamo un csv contenente persone, altrimenti nel contenente aziende
            if(isPerson){
                manager.exportData(picker.getPath()+"\\persons.csv", SettingName.PERSONTYPE);
            }else{
                manager.exportData(picker.getPath()+"\\companies.csv", SettingName.COMPANYTYPE);
            }
        });
        exportButton.setEnabled(!CustomerManager.getList().isEmpty());

        //creiamo il bottone "Importa" che al click apre una finestra di scelta del file, e sostituisce la lista corrente
        //con i dati del file scelto
        importButton = ButtonFactory.createButton(SettingName.GUIIMPORT, e -> {
            FilePicker picker = new FilePicker();
            picker.setMode("open");
            if(picker.getPath() != null){
                int choice = JOptionPane.showConfirmDialog(null, SettingName.GUITEXTLISTSUBSTITUTION, SettingName.GUIWARNING, JOptionPane.YES_NO_OPTION);
                if(choice == JOptionPane.YES_OPTION){
                    //se è una persona importiamo un csv contenente persone, altrimenti nel contenente aziende
                    if(isPerson){
                        personModel.importCSV(picker.getPath());
                    }else{
                        companyModel.importCSV(picker.getPath());
                    }
                }else{
                    JOptionPane.showMessageDialog(null, SettingName.GUIABORTOPERATION, null, JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, SettingName.GUIFILENOTSELECTED, SettingName.GUIERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        //creiamo il tasto "Annulla operazione" che al click ripristina lo stato dei clienti all'operazione precedente
        undoButton = ButtonFactory.createButton(SettingName.GUIUNDO, e -> {
            if(manager.getMemento().getState().getType().equals(SettingName.PERSONTYPE)){
                personModel.undo();
            }else{
                companyModel.undo();
            }
        });



        //di default se non è presente un Memento lo disabilitiamo
        if(manager.getMemento().getState() == null){
            undoButton.setEnabled(false);
        }

        //aggiugiamo tutti i bottoni al pannello di appartenenza
        buttonPanel.add(undoButton);
        buttonPanel.add(addButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(importButton);
    }

    /**
     * Metodo che filtra la tabella delle persone in base al valore della JTextField
     */
    private void personFilter() {
        RowFilter<ItemTableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter("(?i)"+filter.getText(), 0,1,2,3,4,5,6,7,8,9);
        } catch (java.util.regex.PatternSyntaxException e) {
            UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
            return;
        }
        personSorter.setRowFilter(rf);
    }

    /**
     * Metodo che filtra la tabella delle persone in base al valore della JTextField
     */
    private void companyFilter() {
        RowFilter<ItemTableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter("(?i)"+filter.getText(), 0,1,2,3,4,5,6,7,8,9);
        } catch (java.util.regex.PatternSyntaxException e) {
            UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
            return;
        }
        companySorter.setRowFilter(rf);
    }

}
