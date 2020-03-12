package GUI.Customer;


import Core.Customer.CompanyBuilder;
import Core.Customer.PersonBuilder;
import Core.Common.SettingName;
import Core.Customer.CustomerBuilder;
import GUI.Common.ButtonFactory;
import GUI.Common.ItemTableModel;

import javax.swing.*;
import java.awt.*;
/**
 * Classe che crea un frame di inserimento di un cliente
 * Viene implementata l'interfaccia 'JFrame':{@link javax.swing.JFrame}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */


public class CustomerInsertFrame extends JFrame {
    //frame singleton, il modello su cui desideriamo efettuare l'operazione di inserimento e la modalità (Persona/Azienda)
    private static JFrame frame;
    private ItemTableModel model;
    private String mode;

    //rispettivamente il panello principale, quello con i form (persona/azienda) e quello con il bottone
    private JPanel mainPanel;
    private JPanel cardPanel;
    private CardLayout cards;
    private JPanel buttonPanel;

    //bottone per l'inserimento
    private JButton insert;

    //labels e Text fields con i vari attributti del cliente, duplicati perché si creano due form in base alla tipologia
    private JLabel lblname;
    private JTextField personName;
    private JTextField companyName;

    private JLabel lblsurname;
    private JTextField surname;

    private JLabel lbladdress;
    private JTextField personAddress;
    private JTextField companyAddress;

    private JLabel lblcodeSDI;
    private JTextField personCodeSDI;
    private JTextField companyCodeSDI;

    private JLabel lblmail;
    private JTextField personMail;
    private JTextField companyMail;

    private JLabel lblpec;
    private JTextField personPec;
    private JTextField companyPec;

    private JLabel lblpiva;
    private JTextField personPiva;
    private JTextField companyPiva;

    private JLabel lbltelephone;
    private JTextField personTelephone;
    private JTextField companyTelephone;

    private JLabel lblpersonalID;
    private JTextField personalID;

    private JLabel lblshipping;
    private JTextField personShipping;
    private JTextField companyShipping;

    //metodo static per creare un frame singleton, se è già presente lo elimina e lo ricrea
    //utile per evitare di aprire più finestre contemporaneamente
    public static void buildFrame(String mode, ItemTableModel model){
        if( frame == null){
            frame = new CustomerInsertFrame(mode, model);
        }else{
            frame.dispose();
            frame = new CustomerInsertFrame(mode, model);
        }
    }

    /**
     * Costruttore
     * @param model Il modello su cui inserire un cliente
     */
    private CustomerInsertFrame(String mode, ItemTableModel model){
        //creiamo il frame
        super(SettingName.GUIINSERT);

        //inizializziamo il modello e la modalità (persona/azienda)
        this.model = model;
        this.mode = mode;

        //settiamo il layout del frame con un border layout
        BorderLayout layout = new BorderLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        add(mainPanel);

        //creiamo il pannello con le card contenenti i due form (persona/azienda)
        cardPanel = new JPanel();
        cards = new CardLayout();
        cardPanel.setLayout(cards);

        //creiamo i due form e li aggiungiamo alle card identificati dal tipo
        JPanel personPanel = personForm();
        JPanel companyPanel = companyForm();
        cardPanel.add(personPanel, SettingName.PERSONTYPE);
        cardPanel.add(companyPanel, SettingName.COMPANYTYPE);

        //in base alla modalità di default visualizza la prima card
        if(this.mode.equals(SettingName.PERSONTYPE)){
            cards.show(cardPanel, SettingName.PERSONTYPE);
        }else{
            cards.show(cardPanel, SettingName.COMPANYTYPE);
        }
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        //renderizziamo i bottni con le operazioni
        createButtonPanel();

        //terminiamo l'operazione di creazione del frame
        finish();
    }

    /**
     * Metodo che crea il form per inserire i campi di una persona
     */
    private JPanel personForm(){
        //creiamo e settiamo il layout con un grid bag layout
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;

        /*
        nelle rihe di codice a seguire creiamo le label e i textfield che idnetificano gli attributi
        di una riparazione, tramite il constraint specifichiamo la posizione.
        */
        lblname = new JLabel();
        lblname.setText("Nome:");
        personName = new JTextField(15);
        constraint.gridx = 0;
        constraint.gridy = 0;
        detailsPanel.add(lblname,constraint);
        constraint.gridx = 0;
        constraint.gridy = 1;
        detailsPanel.add(personName,constraint);

        lblsurname = new JLabel();
        lblsurname.setText("Cognome:");
        surname = new JTextField(15);
        constraint.gridx = 1;
        constraint.gridy = 0;
        detailsPanel.add(lblsurname,constraint);
        constraint.gridx = 1;
        constraint.gridy = 1;
        detailsPanel.add(surname,constraint);

        lblpersonalID = new JLabel();
        lblpersonalID.setText("Codice fiscale:");
        personalID = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 2;
        detailsPanel.add(lblpersonalID,constraint);
        constraint.gridx = 0;
        constraint.gridy = 3;
        detailsPanel.add(personalID,constraint);

        lblpiva = new JLabel();
        lblpiva.setText("Partita IVA:");
        personPiva = new JTextField();
        constraint.gridx = 1;
        constraint.gridy = 2;
        detailsPanel.add(lblpiva,constraint);
        constraint.gridx = 1;
        constraint.gridy = 3;
        detailsPanel.add(personPiva,constraint);

        lblmail = new JLabel();
        lblmail.setText("E-mail:");
        personMail = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 4;
        detailsPanel.add(lblmail,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 5;
        detailsPanel.add(personMail,constraint);

        lblpec = new JLabel();
        lblpec.setText("PEC:");
        personPec = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 6;
        detailsPanel.add(lblpec,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 7;
        detailsPanel.add(personPec,constraint);

        constraint.gridwidth = 1;
        lblcodeSDI = new JLabel();
        lblcodeSDI.setText("Codice SDI:");
        personCodeSDI = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 8;
        detailsPanel.add(lblcodeSDI,constraint);
        constraint.gridx = 0;
        constraint.gridy = 9;
        detailsPanel.add(personCodeSDI,constraint);

        lbltelephone = new JLabel();
        lbltelephone.setText("Telefono:");
        personTelephone = new JTextField();
        constraint.gridx = 1;
        constraint.gridy = 8;
        detailsPanel.add(lbltelephone,constraint);
        constraint.gridx = 1;
        constraint.gridy = 9;
        detailsPanel.add(personTelephone,constraint);

        lbladdress = new JLabel();
        lbladdress.setText("Indirizzo:");
        personAddress = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 10;
        detailsPanel.add(lbladdress,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 11;
        detailsPanel.add(personAddress,constraint);

        lblshipping = new JLabel();
        lblshipping.setText("Indirizzo di spedizione:");
        personShipping = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 12;
        detailsPanel.add(lblshipping,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 13;
        detailsPanel.add(personShipping,constraint);

        return detailsPanel;
    }

    /**
     * Metodo che crea il form per inserire i campi di un'azienda
     */
    private JPanel companyForm(){
        //creiamo e settiamo il layout con un grid bag layout
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;

        /*
        nelle rihe di codice a seguire creiamo le label e i textfield che idnetificano gli attributi
        di una riparazione, tramite il constraint specifichiamo la posizione.
        */
        lblname = new JLabel();
        lblname.setText("Denominazione:");
        companyName = new JTextField(15);
        constraint.gridx = 0;
        constraint.gridy = 0;
        detailsPanel.add(lblname,constraint);
        constraint.gridx = 0;
        constraint.gridy = 1;
        detailsPanel.add(companyName,constraint);

        lblpiva = new JLabel();
        lblpiva.setText("Partita IVA:");
        companyPiva = new JTextField(15);
        constraint.gridx = 1;
        constraint.gridy = 0;
        detailsPanel.add(lblpiva,constraint);
        constraint.gridx = 1;
        constraint.gridy = 1;
        detailsPanel.add(companyPiva,constraint);

        lblmail = new JLabel();
        lblmail.setText("E-mail:");
        companyMail = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 2;
        detailsPanel.add(lblmail,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 3;
        detailsPanel.add(companyMail,constraint);

        lblpec = new JLabel();
        lblpec.setText("PEC:");
        companyPec = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 4;
        detailsPanel.add(lblpec,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 5;
        detailsPanel.add(companyPec,constraint);

        constraint.gridwidth = 1;
        lblcodeSDI = new JLabel();
        lblcodeSDI.setText("Codice SDI:");
        companyCodeSDI = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 6;
        detailsPanel.add(lblcodeSDI,constraint);
        constraint.gridx = 0;
        constraint.gridy = 7;
        detailsPanel.add(companyCodeSDI,constraint);

        lbltelephone = new JLabel();
        lbltelephone.setText("Telefono:");
        companyTelephone = new JTextField();
        constraint.gridx = 1;
        constraint.gridy = 6;
        detailsPanel.add(lbltelephone,constraint);
        constraint.gridx = 1;
        constraint.gridy = 7;
        detailsPanel.add(companyTelephone,constraint);

        lbladdress = new JLabel();
        lbladdress.setText("Indirizzo:");
        companyAddress = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 8;
        detailsPanel.add(lbladdress,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 9;
        detailsPanel.add(companyAddress,constraint);

        lblshipping = new JLabel();
        lblshipping.setText("Indirizzo di spedizione:");
        companyShipping = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 10;
        detailsPanel.add(lblshipping,constraint);
        constraint.gridwidth = 2;
        constraint.gridx = 0;
        constraint.gridy = 11;
        detailsPanel.add(companyShipping,constraint);

        return detailsPanel;
    }

    /**
     * Metodo che renderizza i bottoni per le varie operazioni
     */
    private void createButtonPanel() {
        //creiamo il pannello
        buttonPanel = new JPanel();

        //creiamo il bottone "Inserisci" che al click aggiunge un cliente in base al tipo
        insert = ButtonFactory.createButton(SettingName.GUIINSERT, e -> {
            CustomerBuilder builder;
            if(mode.equals(SettingName.PERSONTYPE)){
                builder = new PersonBuilder();
                builder.addName(personName.getText());
                builder.addSurname(surname.getText());
                builder.addPersonalID(personalID.getText());
                builder.addPIVA(personPiva.getText());
                builder.addCodeSDI(personCodeSDI.getText());
                builder.addMail(personMail.getText());
                builder.addPEC(personPec.getText());
                builder.addTelephone(personTelephone.getText());
                builder.addAddress(personAddress.getText());
                builder.addShippingAddress(personShipping.getText());
            }else{
                builder = new CompanyBuilder();
                builder.addName(companyName.getText());
                builder.addPIVA(companyPiva.getText());
                builder.addCodeSDI(companyCodeSDI.getText());
                builder.addMail(companyMail.getText());
                builder.addPEC(companyPec.getText());
                builder.addTelephone(companyTelephone.getText());
                builder.addAddress(companyAddress.getText());
                builder.addShippingAddress(companyShipping.getText());
            }

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
