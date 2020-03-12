package GUI.Repair;


import Core.Common.Item;
import Core.Common.SettingName;
import Core.Common.UserLogManager;
import Core.Customer.Customer;
import Core.Customer.CustomerManager;
import Core.Repair.PDFGenerate;
import Core.Repair.Repair;
import Core.Repair.RepairBuilder;
import GUI.Common.ButtonFactory;
import GUI.Common.DateLabelFormatter;
import GUI.Common.FilePicker;
import GUI.Common.ItemTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * Classe che crea un frame con i campi di una riparazione, con la possibilità di modificare o eliminare dalla lista
 * Viene implementata l'interfaccia 'JFrame':{@link javax.swing.JFrame}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class RepairDetailFrame extends JFrame {
    //riparazione di cui vogliamo vedere i dettagli, e il modello su cui desideriamo efettuare delle operazioni
    private Repair repair;
    private ItemTableModel model;
    private List<Customer> customers;
    private List<ComboItem> customerList;

    //frame singleton
    private static JFrame frame;
    private JPanel mainPanel;

    //rispettivamente il pannello principale, quello con i campi della riparazione e quello dei bottoni per le operazioni
    private JPanel detailsPanel;
    private JPanel buttonPanel;

    //bottoni per le varie operazioni
    private JButton delete;
    private JButton update;
    private JButton downloadpdf;
    //inizialmente la modifica è bloccata, sbloccabile con una check box
    private boolean isEditable = false;
    private JCheckBox edit;

    //labels e Text fields con i vari attributti della riparazione
    private JLabel lblcustomer;
    private JComboBox<ComboItem> comboBox;

    private JLabel lblcode;
    private JTextField code;

    private JLabel lblnumber;
    private JTextField number;

    private JLabel lblpickupdate;
    private JDatePickerImpl pickupdate;

    private JLabel lblbrand;
    private JTextField brand;

    private JLabel lblmodel;
    private JTextField txtmodel;

    private JLabel lblserialnumber;
    private JTextField serialnumber;

    private JLabel lblpurchasedate;
    private JDatePickerImpl purchasedate;

    private JLabel lblbrake;
    private JTextField brake;

    private JLabel lblnote;
    private JTextArea note;

    private JLabel lblprice;
    private JTextField price;

    private JCheckBox preventive;

    //metodo static per creare un frame singleton, se è già presente lo elimina e lo ricrea
    //utile per evitare di aprire più finestre contemporaneamente
    public static void buildFrame(Item i, ItemTableModel model){
        if( frame == null){
            frame = new RepairDetailFrame(i, model);
        }else{
            frame.dispose();
            frame = new RepairDetailFrame(i, model);
        }
    }

    /**
     * Costruttore
     * @param i elemento di cui visualizzare i dettagli
     * @param model modello su cui effettuare le operazioni di modifica o rimozione
     */
    private RepairDetailFrame(Item i, ItemTableModel model){
        //creiamo il frame
        super(SettingName.GUIDETAILS);
        //inizializziamo riparazione e modello
        this.repair = (Repair) i;
        this.model = model;

        //settiamo il layout del frame con un border layout
        BorderLayout layout = new BorderLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        add(mainPanel);

        //creiamo e settiamo il pannello con i campi della riparazione con un grid bag layout
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;

        //creiamo e popoliamo una lista di clienti per visualizzare il cliente associato alla riparazione
        //ed eventualmente modificarlo
        customers = CustomerManager.getList();
        customerList = new ArrayList<>();
        //aggiungiamo un comboitem vuoto, che identica il cliente "Nessuno", se la riparazione non ha un cliente associato
        customerList.add(new ComboItem());
        for(Customer c: customers){
            //se è una persona identifichiamo il cliente con "Nome Cognome", altrimenti con la denominazione aziendale
            if(c.getType().equals(SettingName.PERSONTYPE)){
                String s = c.get(SettingName.PERSONNAME)+" "+c.get(SettingName.PERSONSURNAME);
                ComboItem item = new ComboItem(s,c);
                customerList.add(item);
            }else{
                String s = c.get(SettingName.COMPANYNAME);
                ComboItem item = new ComboItem(s,c);
                customerList.add(item);
            }
        }

        //creiamo il form con tutti i campi
        repairForm();

        //abilitiamo/disabilitiamo i campi in base a se è selezionata la modalità modifica
        areTextFiledsEnabled(isEditable);

        //renderizziamo i bottni con le operazioni
        createButtonPanel();

        //terminiamo l'operazione di creazione del frame
        finish();
    }

    /**
     * Metodo che crea il form per visualizzare le informazioni della riparazione
     */
    private void repairForm(){
        //creiamo e settiamo il layout con un grid bag layout
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;

        //definiamo le proprietà della data
        Properties p = new Properties();
        p.put("text.today", "Giorno");
        p.put("text.month", "Mese");
        p.put("text.year", "Anno");

        /*
        nelle rihe di codice a seguire creiamo le label e i textfield che idnetificano gli attributi
        di una riparazione, tramite il constraint specifichiamo la posizione.
        */
        lblcustomer = new JLabel();
        lblcustomer.setText(SettingName.REPAIRCUSTOMER+":");
        constraint.gridx = 0;
        constraint.gridy = 0;
        detailsPanel.add(lblcustomer,constraint);
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.gridwidth = 2;
        renderComboBox(detailsPanel, constraint);
        constraint.gridwidth = 1;

        lblcode = new JLabel();
        lblcode.setText(SettingName.REPAIRCODE+":");
        code = new JTextField(repair.get(SettingName.REPAIRCODE));
        code.setColumns(15);
        constraint.gridx = 0;
        constraint.gridy = 2;
        detailsPanel.add(lblcode,constraint);
        constraint.gridx = 0;
        constraint.gridy = 3;
        detailsPanel.add(code,constraint);

        lblnumber = new JLabel();
        lblnumber.setText(SettingName.REPAIRNUMBER+":");
        number = new JTextField(repair.get(SettingName.REPAIRNUMBER));
        constraint.gridx = 1;
        constraint.gridy = 2;
        detailsPanel.add(lblnumber,constraint);
        constraint.gridx = 1;
        constraint.gridy = 3;
        detailsPanel.add(number,constraint);

        lblpickupdate = new JLabel();
        lblpickupdate.setText(SettingName.REPAIRPICKUPDATE+":");
        UtilDateModel dateModelPickUp = new UtilDateModel();
        JDatePanelImpl datePanelPickUp = new JDatePanelImpl(dateModelPickUp, p);
        pickupdate = new JDatePickerImpl(datePanelPickUp, new DateLabelFormatter());
        pickupdate.getJFormattedTextField().setText(repair.get(SettingName.REPAIRPICKUPDATE));
        constraint.gridx = 0;
        constraint.gridy = 4;
        detailsPanel.add(lblpickupdate,constraint);
        constraint.gridx = 0;
        constraint.gridy = 5;
        detailsPanel.add(pickupdate,constraint);

        lblbrand = new JLabel();
        lblbrand.setText(SettingName.PRODUCTBRAND+":");
        brand = new JTextField(repair.get(SettingName.PRODUCTBRAND));
        brand.setColumns(15);
        constraint.gridx = 1;
        constraint.gridy = 4;
        detailsPanel.add(lblbrand,constraint);
        constraint.gridx = 1;
        constraint.gridy = 5;
        detailsPanel.add(brand,constraint);

        lblmodel = new JLabel();
        lblmodel.setText(SettingName.REPAIRMODEL+":");
        txtmodel = new JTextField(repair.get(SettingName.REPAIRMODEL));
        constraint.gridx = 0;
        constraint.gridy = 6;
        detailsPanel.add(lblmodel,constraint);
        constraint.gridx = 0;
        constraint.gridy = 7;
        detailsPanel.add(txtmodel,constraint);

        lblserialnumber = new JLabel();
        lblserialnumber.setText(SettingName.REPAIRSERIALNUMBER+":");
        serialnumber = new JTextField(repair.get(SettingName.REPAIRSERIALNUMBER));
        constraint.gridx = 1;
        constraint.gridy = 6;
        detailsPanel.add(lblserialnumber,constraint);
        constraint.gridx = 1;
        constraint.gridy = 7;
        detailsPanel.add(serialnumber,constraint);

        lblpurchasedate = new JLabel();
        lblpurchasedate.setText(SettingName.REPAIRPURCHUASEDATE+":");
        UtilDateModel dateModelPurchase = new UtilDateModel();
        JDatePanelImpl datePanelPurchase = new JDatePanelImpl(dateModelPurchase, p);
        purchasedate = new JDatePickerImpl(datePanelPurchase, new DateLabelFormatter());
        purchasedate.getJFormattedTextField().setText(repair.get(SettingName.REPAIRPURCHUASEDATE));
        constraint.gridx = 0;
        constraint.gridy = 8;
        detailsPanel.add(lblpurchasedate,constraint);
        constraint.gridx = 0;
        constraint.gridy = 9;
        detailsPanel.add(purchasedate,constraint);

        lblbrake = new JLabel();
        lblbrake.setText(SettingName.REPAIRBRAKE+":");
        brake = new JTextField(repair.get(SettingName.REPAIRBRAKE));
        constraint.gridx = 1;
        constraint.gridy = 8;
        detailsPanel.add(lblbrake,constraint);
        constraint.gridx = 1;
        constraint.gridy = 9;
        detailsPanel.add(brake,constraint);

        constraint.gridwidth = 2;
        lblnote = new JLabel();
        lblnote.setText(SettingName.REPAIRNOTE+":");
        note = new JTextArea(repair.get(SettingName.REPAIRNOTE));
        note.setRows(4);
        note.setLineWrap(true);
        note.setWrapStyleWord(true);
        note.setBorder(code.getBorder());
        JScrollPane sp = new JScrollPane(note);
        constraint.gridx = 0;
        constraint.gridy = 10;
        detailsPanel.add(lblnote,constraint);
        constraint.gridheight= 4;
        constraint.gridx = 0;
        constraint.gridy = 11;
        detailsPanel.add(sp,constraint);

        constraint.gridwidth = 1;
        constraint.gridheight= 1;
        lblprice = new JLabel();
        lblprice.setText(SettingName.REPAIRPRICE+":");
        price = new JTextField(repair.get(SettingName.REPAIRPRICE));
        constraint.gridx = 0;
        constraint.gridy = 15;
        detailsPanel.add(lblprice,constraint);
        constraint.gridx = 0;
        constraint.gridy = 16;
        detailsPanel.add(price,constraint);

        preventive = new JCheckBox(SettingName.REPAIRPREVENTIVE+"?");
        preventive.setSelected(Boolean.parseBoolean(repair.get(SettingName.REPAIRPREVENTIVE)));
        constraint.gridx = 1;
        constraint.gridy = 16;
        detailsPanel.add(preventive,constraint);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);
    }

    /**
     * metodo che renderizza la combobox di selezione del cliente
     * @param detailsPanel Il pannello grid bag layout in cui aggiungere la combobox
     * @param constraint il relativo constranit
     */
    private void renderComboBox(JPanel detailsPanel, GridBagConstraints constraint){
        //creo la combobox
        comboBox = new JComboBox<>();

        //aggiungo ogni item nella lista di customers alla combobox, selezionando di default quello corrente
        for(ComboItem item: customerList){
            comboBox.addItem(item);
            if(repair.getCustomer() != null && item.getCustomer() != null && item.getCustomer().isEqualTo(repair.getCustomer())){
                comboBox.setSelectedItem(item);
            }
        }

        //la abilito alla modifica, per poter scrivere il nome del cliete ed effettuare una ricerca
        comboBox.setEditable(true);

        //associo l'editor di default della combobox alla mia textfield, su cui posso effettuare un filtraggio quando scrivo
        final JTextField textfield = (JTextField) comboBox.getEditor().getEditorComponent();
        textfield.setColumns(25);
        textfield.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(() -> comboFilter(textfield.getText()));
            }
        });

        //aggiungo la combobox al pannello
        detailsPanel.add(comboBox, constraint);
    }

    /**
     * Metodo che dato un testo, filtra gli item della combobox
     * @param enteredText Stringa da ricercare
     */
    private void comboFilter(String enteredText) {
        //se non è aperto il menù a tendina, lo apriamo
        if (!comboBox.isPopupVisible()) {
            comboBox.showPopup();
        }

        //lista di item filtrata, inizialmente vuota
        List<ComboItem> filterArray= new ArrayList<>();
        //per ogni item riferito al cliente nela lista, se il testo contiene la stringa da ricercare, lo aggiunge all'array filtrato
        for (ComboItem comboItem : customerList) {
            if (comboItem.getText().toLowerCase().contains(enteredText.toLowerCase())) {
                filterArray.add(comboItem);
            }
        }

        //se nell'array filtrato è presente almeno un elemento
        if (filterArray.size() > 0) {
            //rimuovo dalla combobox tutti gli elementi, e riaggiungo quelli presenti nell'array filtrato
            DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox.getModel();
            model.removeAllElements();
            for (ComboItem s: filterArray){
                model.addElement(s);
            }

            //setto l'editor della combobox con il valore della stringa da ricercare
            JTextField textfield = (JTextField) comboBox.getEditor().getEditorComponent();
            textfield.setText(enteredText);
        }
    }

    /**
     * Metodo che abilita o disabilità le text filed in base al valore del booleano a parametro
     * @param b se b è true abilità tutte le texfiled, le disabilita altrimenti
     */
    private void areTextFiledsEnabled(boolean b){
        this.comboBox.setEnabled(b);
        this.code.setEnabled(b);
        this.number.setEnabled(b);
        this.pickupdate.setEnabled(b);
        this.pickupdate.getComponent(1).setEnabled(b);
        this.pickupdate.getJFormattedTextField().setText(repair.get(SettingName.REPAIRPICKUPDATE));
        this.pickupdate.setTextEditable(false);
        this.brand.setEnabled(b);
        this.txtmodel.setEnabled(b);
        this.serialnumber.setEnabled(b);
        this.purchasedate.setEnabled(b);
        this.purchasedate.getComponent(1).setEnabled(b);
        this.purchasedate.getJFormattedTextField().setText(repair.get(SettingName.REPAIRPURCHUASEDATE));
        this.purchasedate.setTextEditable(false);
        this.brake.setEnabled(b);
        this.note.setEnabled(b);
        this.price.setEnabled(b);
        this.preventive.setEnabled(b);
    }

    /**
     * Metodo che renderizza i bottoni per le varie operazioni
     */
    private void createButtonPanel(){
        //creiamo il pannello
        buttonPanel = new JPanel();

        //creiamo il bottone "Elimina" che al click rimuove una riparazione
        delete = ButtonFactory.createButton(SettingName.GUIDELETE, e -> {
            model.delete(repair.getID());
            frame.dispose();
        });

        /*
        creiamo il bottone "Applica" che è abilitato/disabilitato in base al valore della checkbox.
        Al click crea una nuova riparazionee i cui parametri sono i valori
        correnti delle texfield, e aggiorniamo la riparazione corrente con una nuova
        */
        update = ButtonFactory.createButton(SettingName.GUIUPDATE, e -> {
            RepairBuilder builder = new RepairBuilder();
            builder.addCustomer(comboBox.getItemAt(comboBox.getSelectedIndex()).getCustomer());
            builder.addCode(code.getText());
            builder.addNumber(number.getText());
            builder.addPickUpDate(pickupdate.getJFormattedTextField().getText());
            builder.addBrand(brand.getText());
            builder.addModel(txtmodel.getText());
            builder.addSerialNumber(serialnumber.getText());
            builder.addPurchuaseDate(purchasedate.getJFormattedTextField().getText());
            builder.addBrake(brake.getText());
            builder.addNote(note.getText());
            try {
                builder.addPrice(Double.parseDouble(price.getText()));
            }catch (NumberFormatException ex){
                builder.addPrice(0);
            }
            builder.addPreventive(preventive.isSelected());

            model.update(repair.getID(), builder.getResult());
            frame.dispose();
        });
        update.setEnabled(isEditable);

        //creiamo la checkbox che abilita/disabilita la modifica
        edit = new JCheckBox(SettingName.GUIEDIT, isEditable);
        edit.addActionListener(e -> {
            JCheckBox cb = (JCheckBox) e.getSource();
            isEditable = cb.isSelected();
            areTextFiledsEnabled(isEditable);
            update.setEnabled(isEditable);
        });

        //creiamo il bottone per scaricare il contratto di riparazione
        downloadpdf = ButtonFactory.createButton(SettingName.GUIDOWNLOAD, e -> {
            FilePicker picker = new FilePicker();
            picker.setMode("save");
            if(picker.getPath() != null){
                String namePDF = "riparazione#";
                if(repair.get(SettingName.REPAIRNUMBER).equals("")){
                    namePDF += ("0");
                }else{
                    try{
                        namePDF += (repair.get(SettingName.REPAIRNUMBER).substring(0,3));
                    }catch (StringIndexOutOfBoundsException ex){
                        UserLogManager.notify(SettingName.LOGERROR, "Numero di riparazione non nel formato XXX/ANNO");
                    }
                }
                new PDFGenerate(picker.getPath()+"\\"+namePDF+".pdf",SettingName.PDFPATH, repair);
            }
        });

        //aggiungiamo i bottoni al pannello
        buttonPanel.add(downloadpdf);
        buttonPanel.add(delete);
        buttonPanel.add(update);
        buttonPanel.add(edit);

        //aggiungiamo il pannello dei bottoni a quello principale
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Metodo che finalizza la creazione del frame con alcuni settaggi
     */
    private  void finish(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth()*0.4, screenSize.getHeight()*0.6);
        setSize(screenSize);
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
