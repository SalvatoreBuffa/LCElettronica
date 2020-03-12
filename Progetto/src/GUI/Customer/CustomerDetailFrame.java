package GUI.Customer;



import Core.Customer.CompanyBuilder;
import Core.Customer.Customer;
import Core.Common.Item;
import Core.Common.SettingName;
import Core.Customer.CustomerBuilder;
import Core.Customer.PersonBuilder;
import GUI.Common.ButtonFactory;
import GUI.Common.ItemTableModel;

import javax.swing.*;
import java.awt.*;

/**
 * Classe che crea un frame con i campi di un cliente, con la possibilità di modificare o eliminare dalla lista
 * Viene implementata l'interfaccia 'JFrame':{@link javax.swing.JFrame}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class CustomerDetailFrame extends JFrame {
    //cliente di cui vogliamo vedere i dettagli, e il modello su cui desideriamo efettuare delle operazioni
    private Customer customer;
    private ItemTableModel model;

    //frame singleton
    private static JFrame frame;

    //rispettivamente il pannello principale, quello con i campi del cliente e quello dei bottoni per le operazioni
    private JPanel mainPanel;
    private JPanel detailsPanel;
    private JPanel buttonPanel;

    //bottoni per le varie operazioni
    private JButton delete;
    private JButton update;
    //inizialmente la modifica è bloccata, sbloccabile con una check box
    private boolean isEditable = false;
    private JCheckBox edit;

    //labels e Text fields con i vari attributti del cliente
    private JLabel lblname;
    private JTextField name;

    private JLabel lblsurname;
    private JTextField surname;

    private JLabel lbladdress;
    private JTextField address;

    private JLabel lblcodeSDI;
    private JTextField codeSDI;

    private JLabel lblmail;
    private JTextField mail;

    private JLabel lblpec;
    private JTextField pec;

    private JLabel lblpiva;
    private JTextField piva;

    private JLabel lbltelephone;
    private JTextField telephone;

    private JLabel lblpersonalID;
    private JTextField personalID;

    private JLabel lblshipping;
    private JTextField shipping;

    //metodo static per creare un frame singleton, se è già presente lo elimina e lo ricrea
    //utile per evitare di aprire più finestre contemporaneamente
    public static void buildFrame(Item i, ItemTableModel model){
        if( frame == null){
            frame = new CustomerDetailFrame(i, model);
        }else{
            frame.dispose();
            frame = new CustomerDetailFrame(i, model);
        }
    }

    /**
     * Costruttore
     * @param i elemento di cui visualizzare i dettagli
     * @param model modello su cui effettuare le operazioni di modifica o rimozione
     */
    private CustomerDetailFrame(Item i, ItemTableModel model){
        //creiamo il frame
        super(SettingName.GUIDETAILS);

        //inizializziamo cliente e modello
        this.customer = (Customer) i;
        this.model = model;

        //settiamo il layout del frame con un border layout
        BorderLayout layout = new BorderLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        add(mainPanel);

        //creiamo e settiamo il pannello con i campi del cliente con un grid bag layout
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;

        //creiamo il form con tutti i campi in base al tipo di cliente
        if(this.customer.getType().equals(SettingName.PERSONTYPE)){
            customerForm(detailsPanel, constraint);
        }else{
            companyForm(detailsPanel, constraint);
        }

        //abilitiamo/disabilitiamo i campi in base a se è selezionata la modalità modifica
        areTextFiledsEnabled(isEditable);

        //renderizziamo i bottni con le operazioni
        createButtonPanel();

        //terminiamo l'operazione di creazione del frame
        finish();
    }

    /**
     * Metodo che crea il form per visualizzare le informazioni del cliente di tipo persona
     * @param detailsPanel pannello in cui renderizzare il form
     * @param constraint constraint usato nel gridbag layout
     */
    private void customerForm(JPanel detailsPanel, GridBagConstraints constraint){
        /*
        nelle rihe di codice a seguire creiamo le label e i textfield che idnetificano gli attributi
        di una persona, tramite il constraint specifichiamo la posizione.
        */

        lblname = new JLabel();
        lblname.setText("Nome:");
        name = new JTextField(customer.get(SettingName.PERSONNAME));
        name.setColumns(15);
        constraint.gridx = 0;
        constraint.gridy = 0;
        detailsPanel.add(lblname,constraint);
        constraint.gridx = 0;
        constraint.gridy = 1;
        detailsPanel.add(name,constraint);

        lblsurname = new JLabel();
        lblsurname.setText("Cognome:");
        surname = new JTextField(customer.get(SettingName.PERSONSURNAME));
        surname.setColumns(15);
        constraint.gridx = 1;
        constraint.gridy = 0;
        detailsPanel.add(lblsurname,constraint);
        constraint.gridx = 1;
        constraint.gridy = 1;
        detailsPanel.add(surname,constraint);

        lblpersonalID = new JLabel();
        lblpersonalID.setText("Codice fiscale:");
        personalID = new JTextField(customer.get(SettingName.PERSONPERSONALID));
        constraint.gridx = 0;
        constraint.gridy = 2;
        detailsPanel.add(lblpersonalID,constraint);
        constraint.gridx = 0;
        constraint.gridy = 3;
        detailsPanel.add(personalID,constraint);

        lblpiva = new JLabel();
        lblpiva.setText("Partita IVA:");
        piva = new JTextField(customer.get(SettingName.CUSTOMERPIVA));
        constraint.gridx = 1;
        constraint.gridy = 2;
        detailsPanel.add(lblpiva,constraint);
        constraint.gridx = 1;
        constraint.gridy = 3;
        detailsPanel.add(piva,constraint);

        lblmail = new JLabel();
        lblmail.setText("E-mail:");
        mail = new JTextField(customer.get(SettingName.CUSTOMERMAIL));
        constraint.gridx = 0;
        constraint.gridy = 4;
        detailsPanel.add(lblmail,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 5;
        detailsPanel.add(mail,constraint);

        lblpec = new JLabel();
        lblpec.setText("PEC:");
        pec = new JTextField(customer.get(SettingName.CUSTOMERPEC));
        constraint.gridx = 0;
        constraint.gridy = 6;
        detailsPanel.add(lblpec,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 7;
        detailsPanel.add(pec,constraint);

        constraint.gridwidth = 1;
        lblcodeSDI = new JLabel();
        lblcodeSDI.setText("Codice SDI:");
        codeSDI = new JTextField(customer.get(SettingName.PERSONCODESDI));
        constraint.gridx = 0;
        constraint.gridy = 8;
        detailsPanel.add(lblcodeSDI,constraint);
        constraint.gridx = 0;
        constraint.gridy = 9;
        detailsPanel.add(codeSDI,constraint);

        lbltelephone = new JLabel();
        lbltelephone.setText("Telefono:");
        telephone = new JTextField(customer.get(SettingName.CUSTOMERTELEPHONE));
        constraint.gridx = 1;
        constraint.gridy = 8;
        detailsPanel.add(lbltelephone,constraint);
        constraint.gridx = 1;
        constraint.gridy = 9;
        detailsPanel.add(telephone,constraint);

        lbladdress = new JLabel();
        lbladdress.setText("Indirizzo:");
        address = new JTextField(customer.get(SettingName.CUSTOMERADDRESS));
        constraint.gridx = 0;
        constraint.gridy = 10;
        detailsPanel.add(lbladdress,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 11;
        detailsPanel.add(address,constraint);

        lblshipping = new JLabel();
        lblshipping.setText("Indirizzo di spedizione:");
        shipping = new JTextField(customer.get(SettingName.CUSTOMERSHIPPINGADDRESS));
        constraint.gridx = 0;
        constraint.gridy = 12;
        detailsPanel.add(lblshipping,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 13;
        detailsPanel.add(shipping,constraint);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);
    }

    /**
     * Metodo che crea il form per visualizzare le informazioni del cliente di tipo azienda
     * @param detailsPanel pannello in cui renderizzare il form
     * @param constraint constraint usato nel gridbag layout
     */
    private void companyForm(JPanel detailsPanel, GridBagConstraints constraint){
        /*
        nelle rihe di codice a seguire creiamo le label e i textfield che idnetificano gli attributi
        di un'azienda, tramite il constraint specifichiamo la posizione.
        */

        lblname = new JLabel();
        lblname.setText("Denominazione:");
        name = new JTextField(customer.get(SettingName.COMPANYNAME));
        name.setColumns(15);
        constraint.gridx = 0;
        constraint.gridy = 0;
        detailsPanel.add(lblname,constraint);
        constraint.gridx = 0;
        constraint.gridy = 1;
        detailsPanel.add(name,constraint);

        lblpiva = new JLabel();
        lblpiva.setText("Partita IVA:");
        piva = new JTextField(customer.get(SettingName.CUSTOMERPIVA));
        piva.setColumns(15);
        constraint.gridx = 1;
        constraint.gridy = 0;
        detailsPanel.add(lblpiva,constraint);
        constraint.gridx = 1;
        constraint.gridy = 1;
        detailsPanel.add(piva,constraint);

        lblmail = new JLabel();
        lblmail.setText("E-mail:");
        mail = new JTextField(customer.get(SettingName.CUSTOMERMAIL));
        constraint.gridx = 0;
        constraint.gridy = 2;
        detailsPanel.add(lblmail,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 3;
        detailsPanel.add(mail,constraint);

        lblpec = new JLabel();
        lblpec.setText("PEC:");
        pec = new JTextField(customer.get(SettingName.CUSTOMERPEC));
        constraint.gridx = 0;
        constraint.gridy = 4;
        detailsPanel.add(lblpec,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 5;
        detailsPanel.add(pec,constraint);

        constraint.gridwidth = 1;
        lblcodeSDI = new JLabel();
        lblcodeSDI.setText("Codice SDI:");
        codeSDI = new JTextField(customer.get(SettingName.COMPANYCODESDI));
        constraint.gridx = 0;
        constraint.gridy = 6;
        detailsPanel.add(lblcodeSDI,constraint);
        constraint.gridx = 0;
        constraint.gridy = 7;
        detailsPanel.add(codeSDI,constraint);

        lbltelephone = new JLabel();
        lbltelephone.setText("Telefono:");
        telephone = new JTextField(customer.get(SettingName.CUSTOMERTELEPHONE));
        constraint.gridx = 1;
        constraint.gridy = 6;
        detailsPanel.add(lbltelephone,constraint);
        constraint.gridx = 1;
        constraint.gridy = 7;
        detailsPanel.add(telephone,constraint);

        lbladdress = new JLabel();
        lbladdress.setText("Indirizzo:");
        address = new JTextField(customer.get(SettingName.CUSTOMERADDRESS));
        constraint.gridx = 0;
        constraint.gridy = 8;
        detailsPanel.add(lbladdress,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 9;
        detailsPanel.add(address,constraint);

        lblshipping = new JLabel();
        lblshipping.setText("Indirizzo di spedizione:");
        shipping = new JTextField(customer.get(SettingName.CUSTOMERSHIPPINGADDRESS));
        constraint.gridx = 0;
        constraint.gridy = 10;
        detailsPanel.add(lblshipping,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 11;
        detailsPanel.add(shipping,constraint);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);
    }

    /**
     * Metodo che abilita o disabilità le text filed in base al valore del booleano a parametro
     * @param b se b è true abilità tutte le texfiled, le disabilita altrimenti
     */
    private void areTextFiledsEnabled(boolean b){
        this.name.setEnabled(b);
        if(customer.getType().equals(SettingName.PERSONTYPE)){
            this.surname.setEnabled(b);
        }
        this.address.setEnabled(b);
        this.shipping.setEnabled(b);
        this.codeSDI.setEnabled(b);
        this.telephone.setEnabled(b);
        if(customer.getType().equals(SettingName.PERSONTYPE)){
            this.personalID.setEnabled(b);
        }
        this.piva.setEnabled(b);
        this.mail.setEnabled(b);
        this.pec.setEnabled(b);
    }

    /**
     * Metodo che renderizza i bottoni per le varie operazioni
     */
    private void createButtonPanel(){
        //creiamo il pannello
        buttonPanel = new JPanel();

        //creiamo il bottone "Elimina" che al click rimuove un cliente
        delete = ButtonFactory.createButton(SettingName.GUIDELETE, e -> {
            model.delete(customer.getID());
            frame.dispose();
        });

        /*
        creiamo il bottone "Applica" che è abilitato/disabilitato in base al valore della checkbox.
        Al click crea un nuovo cliente i cui parametri sono i valori
        correnti delle texfield, e aggiorniamo il cliente corrente con uno nuovo
        */
        update = ButtonFactory.createButton(SettingName.GUIUPDATE, e -> {
            CustomerBuilder builder;
            //se è una persona creiamo una persona, un'azienda altrimenti
            if(customer.getType().equals(SettingName.PERSONTYPE)){
                builder = new PersonBuilder();
                builder.addName(name.getText());
                builder.addSurname(surname.getText());
                builder.addPersonalID(personalID.getText());
            }else{
                builder = new CompanyBuilder();
                builder.addName(name.getText());
            }
            builder.addPIVA(piva.getText());
            builder.addCodeSDI(codeSDI.getText());
            builder.addMail(mail.getText());
            builder.addPEC(pec.getText());
            builder.addTelephone(telephone.getText());
            builder.addAddress(address.getText());
            builder.addShippingAddress(shipping.getText());
            model.update(customer.getID(), builder.getResult());
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

        //aggiungiamo i bottoni al pannello
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
