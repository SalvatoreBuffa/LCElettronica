package GUI.Activity;



import Core.Activity.*;
import Core.Common.SettingName;
import Core.Common.UserLogManager;
import GUI.Common.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

/**
 * Classe che crea il pannello contenente la lista di attività memorizzate ed i vari bottoni per eseguire operazioni su di esse
 * Viene implementata l'interfaccia 'JPanel':{@link javax.swing.JPanel}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class ActivityPanel extends JPanel {
    //manager di attività
    private ActivityManager manager;

    //rispettivamente i sotto-pannelli con la search bar, la lista e i bottoni
    private JPanel filterPanel;
    private JScrollPane listPanel;
    private JPanel buttonPanel;

    //rispettivamente il modello della tabella, il sorter utilizzato per ricerca e ordinamento e la barra di ricerca
    private ItemTableModel model;
    private TableRowSorter<ItemTableModel> sorter;
    private JTextField filter;

    //bottoni per le varie operazioni
    private JButton addButton;
    private JButton undoButton;
    private JButton exportButton;

    private JButton importButton;
    private JButton showButton;

    /**
     * Costruttore
     */
    public ActivityPanel(){
        //creiamo il manager
        manager = new ActivityManager();

        //settiamo il border layout
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        //carichiamo la lista di attività salvate precedentemente
        List<Activity> list = ActivityBackup.restoreList();
        manager.setList(list);

        //creiamo il pannello con la barra di ricerca
        filterPanel = new JPanel(new FlowLayout());
        filterPanel.add(new JLabel(SettingName.GUISEARCH+":"));
        filter = new JTextField(30);
        filterPanel.add(filter);

        //creo la tabella e la inserisco nel listPanel
        listPanel = new JScrollPane(renderActivityTable());

        //creo i bottoni
        buttonPanel = new JPanel();
        renderOperationButtons();

        //aggiungo i tre sotto-pannelli al pannello principale
        add(filterPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    /**
     * Metodo che crea la tabella, aggiunge il sorter ed il listner e la restituisce
     * @return tabella da renderizzare nel list panel
     */
    private JTable renderActivityTable(){
        //creiamo il modello
        model = new ActivityTableModel(manager);

        //aggiungiamo al modello un listner, per abilitare/disabilitare alcuni bottoni in base ai cambiamenti
        model.addTableModelListener(e -> {
            //se la lista è vuota disabilitiamo i tasti mostra e esporta, e viceversa
            if(ActivityManager.getList().isEmpty()) {
                showButton.setEnabled(false);
                exportButton.setEnabled(false);
            }else{
                showButton.setEnabled(true);
                exportButton.setEnabled(true);
            }

            //se è presente un Memento abilitiamo il tasto undo
            if(manager.getMemento().getState() != null){
                undoButton.setEnabled(true);
            }
        });

        //creiamo il sorter utilizzato per ricerca ed ordinamento
        sorter = new TableRowSorter<>(model);
        JTable table = new JTable(model);
        table.setRowSorter(sorter);

        //aggiungiamo alla text field della ricerca il listner, ad ogni cambiamento aggiorna la tabella
        filter.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                Filter();
            }
            public void insertUpdate(DocumentEvent e) {
                Filter();
            }
            public void removeUpdate(DocumentEvent e) {
                Filter();
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
        //creiamo il bottone "Inserisci" che al click apre il frame per inserire un'attività
        addButton = ButtonFactory.createButton(SettingName.GUIINSERT, e -> {
            ActivityInsertFrame.buildFrame(model);
        });

        //creiamo il bottone "Esporta", lo settiamo false se la lista è vuota e aggiungiamo il listner che apre la  finestra di salvataggio del file csv e lo salva
        exportButton = ButtonFactory.createButton(SettingName.GUIEXPORT, e -> {
            FilePicker picker = new FilePicker();
            picker.setMode("save");
            manager.exportData(picker.getPath()+"\\activities.csv");
        });
        exportButton.setEnabled(!ActivityManager.getList().isEmpty());

        //creiamo il bottone "Importa" che al click apre una finestra di scelta del file, e sostituisce la lista corrente
        //con i dati del file scelto
        importButton = ButtonFactory.createButton(SettingName.GUIIMPORT, e -> {
            FilePicker picker = new FilePicker();
            picker.setMode("open");
            if(picker.getPath() != null){
                int choice = JOptionPane.showConfirmDialog(null, SettingName.GUITEXTLISTSUBSTITUTION, SettingName.GUIWARNING, JOptionPane.YES_NO_OPTION);
                if(choice == JOptionPane.YES_OPTION){
                    model.importCSV(picker.getPath());
                }else{
                    JOptionPane.showMessageDialog(null, SettingName.GUIABORTOPERATION, null, JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, SettingName.GUIFILENOTSELECTED, SettingName.GUIERROR, JOptionPane.ERROR_MESSAGE);
            }
        });

        //creiamo il bottone "Mostra" che al click apre una finestra con un grafico entrate/uscite riferito alle attività
        showButton = ButtonFactory.createButton(SettingName.GUISHOWACTIVITY, e -> {
            ChartFrame.buildFrame(ActivityManager.getList());
        });
        //lo disabilitiamo se la lista di attività è vuota
        showButton.setEnabled(!ActivityManager.getList().isEmpty());

        //creiamo il tasto "Annulla operazione" che al click ripristina lo stato delle attività all'operazione precedente
        undoButton = ButtonFactory.createButton(SettingName.GUIUNDO, e -> {
            model.undo();
        });
        //di default se non è presente un Memento lo disabilitiamo
        if(manager.getMemento().getState() == null){
            undoButton.setEnabled(false);
        }

        //aggiugiamo tutti i bottoni al pannello di appartenenza
        buttonPanel.add(showButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(addButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(importButton);
    }

    /**
     * Metodo che filtra la tabella in base al valore della JTextField
     */
    private void Filter() {
        RowFilter<ItemTableModel, Object> rf;
        try {
            rf = RowFilter.regexFilter("(?i)"+filter.getText(), 0,1,2,3);
        } catch (java.util.regex.PatternSyntaxException e) {
            UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
            return;
        }
        sorter.setRowFilter(rf);
    }
}
