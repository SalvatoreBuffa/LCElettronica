package GUI.Activity;


import Core.Activity.Activity;
import Core.Activity.ActivityBuilder;
import Core.Common.Item;
import Core.Common.SettingName;
import GUI.Common.ButtonFactory;
import GUI.Common.ItemTableModel;
import GUI.Common.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

/**
 * Classe che crea un frame con i campi di un'attività, con la possibilità di modificare o eliminare dalla lista
 * Viene implementata l'interfaccia 'JFrame':{@link javax.swing.JFrame}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */


public class ActivityDetailFrame extends JFrame {
    //attività di cui vogliamo vedere i dettagli, e il modello su cui desideriamo efettuare delle operazioni
    private Activity activity;
    private ItemTableModel model;

    //frame singleton
    private static JFrame frame;

    //rispettivamente il pannello principale, quello con i campi dell'attività e quello dei bottoni per le operazioni
    private JPanel mainPanel;
    private JPanel detailsPanel;
    private JPanel buttonPanel;

    //bottoni per le varie operazioni
    private JButton delete;
    private JButton update;
    //inizialmente la modifica è bloccata, sbloccabile con una check box
    private boolean isEditable = false;
    private JCheckBox edit;

    //labels e Text fields con i vari attributti dell'attività
    private JLabel lbldescription;
    private JTextArea description;

    private JLabel lbltype;
    private JTextField type;

    private JLabel lbldate;
    private JDatePickerImpl date;

    private JLabel lblprice;
    private JTextField price;

    private JCheckBox revenue;

    //metodo static per creare un frame singleton, se è già presente lo elimina e lo ricrea
    //utile per evitare di aprire più finestre contemporaneamente
    public static void buildFrame(Item i, ItemTableModel model){
        if( frame == null){
            frame = new ActivityDetailFrame(i, model);
        }else{
            frame.dispose();
            frame = new ActivityDetailFrame(i, model);
        }
    }

    /**
     * Costruttore
     * @param i elemento di cui visualizzare i dettagli
     * @param model modello su cui effettuare le operazioni di modifica o rimozione
     */
    private ActivityDetailFrame(Item i, ItemTableModel model){
        //creiamo il frame
        super(SettingName.GUIDETAILS);

        //inizializziamo attività e modello
        this.activity = (Activity) i;
        this.model = model;

        //creiamo e settiamo il layout del frame con un border layout
        BorderLayout layout = new BorderLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        add(mainPanel);

        //creiamo il form con tutti i campi
        activityForm();

        //abilitiamo/disabilitiamo i campi in base a se è selezionata la modalità modifica
        areTextFiledsEnabled(isEditable);

        //renderizziamo i bottni con le operazioni
        createButtonPanel();

        //terminiamo l'operazione di creazione del frame
        finish();
    }

    /**
     * Metodo che crea il form per visualizzare le informazioni dell'attività
     */
    private void activityForm(){
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
        di un'attività, tramite il constraint specifichiamo la posizione.
        */
        type = new JTextField(activity.get(SettingName.ACTIVITYTYPE));

        lbldescription = new JLabel();
        lbldescription.setText(SettingName.ACTIVITYDESCRIPTION +":");
        description = new JTextArea(activity.get(SettingName.ACTIVITYDESCRIPTION));
        description.setRows(4);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBorder(type.getBorder());
        JScrollPane sp = new JScrollPane(description);
        constraint.gridx = 0;
        constraint.gridy = 0;
        detailsPanel.add(lbldescription,constraint);
        constraint.gridwidth = 2;
        constraint.gridheight = 4;
        constraint.gridx = 0;
        constraint.gridy = 1;
        detailsPanel.add(sp,constraint);

        constraint.gridwidth = 1;
        constraint.gridheight = 1;

        lbltype = new JLabel();
        lbltype.setText(SettingName.ACTIVITYTYPE +":");
        constraint.gridx = 0;
        constraint.gridy = 5;
        detailsPanel.add(lbltype,constraint);
        constraint.gridx = 0;
        constraint.gridy = 6;
        detailsPanel.add(type,constraint);

        lbldate = new JLabel();
        lbldate.setText(SettingName.ACTIVITYDATE +":");
        UtilDateModel dateModelPurchase = new UtilDateModel();
        JDatePanelImpl datePanelPurchase = new JDatePanelImpl(dateModelPurchase, p);
        date = new JDatePickerImpl(datePanelPurchase, new DateLabelFormatter());
        date.setTextEditable(false);
        date.getJFormattedTextField().setText(activity.get(SettingName.ACTIVITYDATE));
        constraint.gridx = 1;
        constraint.gridy = 5;
        detailsPanel.add(lbldate,constraint);
        constraint.gridx = 1;
        constraint.gridy = 6;
        detailsPanel.add(date,constraint);

        lblprice = new JLabel();
        lblprice.setText(SettingName.ACTIVITYPRICE+":");
        price = new JTextField(activity.get(SettingName.ACTIVITYPRICE));
        constraint.gridx = 0;
        constraint.gridy = 7;
        detailsPanel.add(lblprice,constraint);
        constraint.gridx = 0;
        constraint.gridy = 8;
        detailsPanel.add(price,constraint);

        revenue = new JCheckBox(SettingName.ACTIVITYREVENUE+"?");
        revenue.setSelected(Boolean.parseBoolean(activity.get(SettingName.ACTIVITYREVENUE)));
        constraint.gridx = 1;
        constraint.gridy = 8;
        detailsPanel.add(revenue,constraint);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);
    }

    /**
     * Metodo che abilita o disabilità le text filed in base al valore del booleano a parametro
     * @param b se b è true abilità tutte le texfiled, le disabilita altrimenti
     */
    private void areTextFiledsEnabled(boolean b){
        this.description.setEnabled(b);
        this.type.setEnabled(b);
        this.date.setEnabled(b);
        this.date.getComponent(1).setEnabled(b);
        this.date.getJFormattedTextField().setText(activity.get(SettingName.ACTIVITYDATE));
        this.date.setTextEditable(false);
        this.price.setEnabled(b);
        this.revenue.setEnabled(b);
    }

    /**
     * Metodo che crea il pannello con i bottoni per le varie operazioni
     */
    private void createButtonPanel(){
        //creiamo il pannello
        buttonPanel = new JPanel();

        //creiamo il bottone "Elimina" che al click rimuove un'attività
        delete = ButtonFactory.createButton(SettingName.GUIDELETE, e -> {
            model.delete(activity.getID());
            frame.dispose();
        });

        /*
        creiamo il bottone "Applica" che è abilitato/disabilitato in base al valore della checkbox.
        Al click crea una nuova attività i cui parametri sono i valori
        correnti delle texfield, e aggiorniamo l'attività corrente con una nuova
        */
        update = ButtonFactory.createButton(SettingName.GUIUPDATE, e -> {
            ActivityBuilder builder = new ActivityBuilder();
            builder.addDescription(description.getText());
            builder.addType(type.getText());
            builder.addDate(date.getJFormattedTextField().getText());
            try {
                builder.addPrice(Double.parseDouble(price.getText()));
            }catch (NumberFormatException ex){
                builder.addPrice(0);
            }
            builder.addSign(revenue.isSelected());

            model.update(activity.getID(), builder.getResult());
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
