package GUI.Repair;



import Core.Common.SettingName;
import Core.Customer.Customer;
import Core.Customer.CustomerManager;
import Core.Repair.Repair;
import Core.Repair.RepairBuilder;
import GUI.Common.ButtonFactory;
import GUI.Common.DateLabelFormatter;
import GUI.Common.ItemTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
/**
 * Classe che crea un frame di inserimento di una riparazione
 * Viene implementata l'interfaccia 'JFrame':{@link javax.swing.JFrame}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
public class RepairInsertFrame extends JFrame {
    //frame singleton e il modello su cui desideriamo efettuare l'operazione di inserimento
    private static JFrame frame;
    private ItemTableModel model;

    //lista di clienti da far visualizzare durante l'inserimento
    private List<Customer> customers;
    private List<ComboItem> customerList;

    //rispettivamente il panello principale, quello con il form e quello con il bottone
    private JPanel mainPanel;
    private JPanel detailsPanel;
    private JPanel buttonPanel;

    //bottone per l'inserimento
    private JButton insert;

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
    public static void buildFrame(ItemTableModel model){
        if( frame == null){
            frame = new RepairInsertFrame(model);
        }else{
            frame.dispose();
            frame = new RepairInsertFrame(model);
        }
    }

    /**
     * Costruttore
     * @param model Il modello su cui inserire la riparazione
     */
    private RepairInsertFrame(ItemTableModel model){
        //creiamo il frame
        super(SettingName.GUIINSERT);
        //inizializziamo il modello
        this.model = model;

        //settiamo il layout del frame con un border layout
        BorderLayout layout = new BorderLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        add(mainPanel);

        customers = CustomerManager.getList();
        customerList = new ArrayList<>();
        customerList.add(new ComboItem());
        for(Customer c: customers){
            if(c.getType().equals(SettingName.PERSONTYPE)){
                String s = /*c.getID()+" "+*/c.get(SettingName.PERSONNAME)+" "+c.get(SettingName.PERSONSURNAME);
                ComboItem item = new ComboItem(s,c);
                customerList.add(item);
            }else{
                String s = /*c.getID()+" "+*/c.get(SettingName.COMPANYNAME);
                ComboItem item = new ComboItem(s,c);
                customerList.add(item);
            }
        }

        //creiamo il form con tutti i campi
        repairForm();

        //renderizziamo i bottni con le operazioni
        createButtonPanel();

        //terminiamo l'operazione di creazione del frame
        finish();
    }

    /**
     * Metodo che crea il form per inserire i campi di una riparazione
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
        code = new JTextField();
        code.setColumns(15);
        constraint.gridx = 0;
        constraint.gridy = 2;
        detailsPanel.add(lblcode,constraint);
        constraint.gridx = 0;
        constraint.gridy = 3;
        detailsPanel.add(code,constraint);

        lblnumber = new JLabel();
        lblnumber.setText(SettingName.REPAIRNUMBER+":");
        number = new JTextField(String.format("%03d", Repair.getCounter()+1)+"/"+ Calendar.getInstance().get(Calendar.YEAR));
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
        pickupdate.setTextEditable(false);
        constraint.gridx = 0;
        constraint.gridy = 4;
        detailsPanel.add(lblpickupdate,constraint);
        constraint.gridx = 0;
        constraint.gridy = 5;
        detailsPanel.add(pickupdate,constraint);

        lblbrand = new JLabel();
        lblbrand.setText(SettingName.PRODUCTBRAND+":");
        brand = new JTextField();
        brand.setColumns(15);
        constraint.gridx = 1;
        constraint.gridy = 4;
        detailsPanel.add(lblbrand,constraint);
        constraint.gridx = 1;
        constraint.gridy = 5;
        detailsPanel.add(brand,constraint);

        lblmodel = new JLabel();
        lblmodel.setText(SettingName.REPAIRMODEL+":");
        txtmodel = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 6;
        detailsPanel.add(lblmodel,constraint);
        constraint.gridx = 0;
        constraint.gridy = 7;
        detailsPanel.add(txtmodel,constraint);

        lblserialnumber = new JLabel();
        lblserialnumber.setText(SettingName.REPAIRSERIALNUMBER+":");
        serialnumber = new JTextField();
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
        purchasedate.setTextEditable(false);
        constraint.gridx = 0;
        constraint.gridy = 8;
        detailsPanel.add(lblpurchasedate,constraint);
        constraint.gridx = 0;
        constraint.gridy = 9;
        detailsPanel.add(purchasedate,constraint);

        lblbrake = new JLabel();
        lblbrake.setText(SettingName.REPAIRBRAKE+":");
        brake = new JTextField();
        constraint.gridx = 1;
        constraint.gridy = 8;
        detailsPanel.add(lblbrake,constraint);
        constraint.gridx = 1;
        constraint.gridy = 9;
        detailsPanel.add(brake,constraint);

        constraint.gridwidth = 2;
        lblnote = new JLabel();
        lblnote.setText(SettingName.REPAIRNOTE+":");
        note = new JTextArea();
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
        price = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 15;
        detailsPanel.add(lblprice,constraint);
        constraint.gridx = 0;
        constraint.gridy = 16;
        detailsPanel.add(price,constraint);

        preventive = new JCheckBox(SettingName.REPAIRPREVENTIVE+"?");
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
        //aggiungo ogni item nella lista di customers alla combobox
        for(ComboItem item: customerList){
            comboBox.addItem(item);
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
     * Metodo che renderizza i bottoni per le varie operazioni
     */
    private void createButtonPanel() {
        //creiamo il pannello
        buttonPanel = new JPanel();

        //creiamo il bottone "Inserisci" che al click aggiunge una riparazione
        insert = ButtonFactory.createButton(SettingName.GUIINSERT, e -> {
            RepairBuilder builder = new RepairBuilder();
            try {
                builder.addCustomer(comboBox.getItemAt(comboBox.getSelectedIndex()).getCustomer());
            }catch (NullPointerException ex){
                builder.addCustomer(null);
            }
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

            model.insert(builder.getResult());
            frame.dispose();

        });

        //aggiungiamo il bottone al pannello
        buttonPanel.add(insert);

        //aggiungiamo il pannello del bottone a quello principale
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
