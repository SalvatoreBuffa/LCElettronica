package GUI.Activity;



import Core.Activity.ActivityBuilder;
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
 * Classe che crea un frame di inserimento di un'attività
 * Viene implementata l'interfaccia 'JFrame':{@link javax.swing.JFrame}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class ActivityInsertFrame extends JFrame {
    //frame singleton e il modello su cui desideriamo efettuare l'operazione di inserimento
    private static JFrame frame;
    private ItemTableModel model;

    //rispettivamente il panello principale, quello con il form e quello con il bottone
    private JPanel mainPanel;
    private JPanel detailsPanel;
    private JPanel buttonPanel;

    //bottone per l'inserimento
    private JButton insert;

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
    public static void buildFrame(ItemTableModel model){
        if( frame == null){
            frame = new ActivityInsertFrame(model);
        }else{
            frame.dispose();
            frame = new ActivityInsertFrame(model);
        }
    }

    /**
     * Costruttore
     * @param model Il modello su cui inserire l'attività
     */
    private ActivityInsertFrame(ItemTableModel model){
        //creiamo il frame
        super(SettingName.GUIINSERT);

        //inizializziamo il modello
        this.model = model;

        //settiamo il layout del frame con un border layout
        BorderLayout layout = new BorderLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        add(mainPanel);

        //creiamo il form con tutti i campi
        activityForm();

        //renderizziamo i bottni con le operazioni
        createButtonPanel();

        //terminiamo l'operazione di creazione del frame
        finish();
    }

    /**
     * Metodo che crea il form per inserire i campi di un'attività
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
        di una riparazione, tramite il constraint specifichiamo la posizione.
        */
        type = new JTextField(15);

        lbldescription = new JLabel();
        lbldescription.setText(SettingName.ACTIVITYDESCRIPTION +":");
        description = new JTextArea();
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
        type = new JTextField(15);
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
        constraint.gridx = 1;
        constraint.gridy = 5;
        detailsPanel.add(lbldate,constraint);
        constraint.gridx = 1;
        constraint.gridy = 6;
        detailsPanel.add(date,constraint);

        lblprice = new JLabel();
        lblprice.setText(SettingName.ACTIVITYPRICE+":");
        price = new JTextField(15);
        constraint.gridx = 0;
        constraint.gridy = 7;
        detailsPanel.add(lblprice,constraint);
        constraint.gridx = 0;
        constraint.gridy = 8;
        detailsPanel.add(price,constraint);

        revenue = new JCheckBox(SettingName.ACTIVITYREVENUE+"?");
        constraint.gridx = 1;
        constraint.gridy = 8;
        detailsPanel.add(revenue,constraint);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);
    }

    /**
     * Metodo che renderizza i bottoni per le varie operazioni
     */
    private void createButtonPanel() {
        //creiamo il pannello
        buttonPanel = new JPanel();

        //creiamo il bottone "Inserisci" che al click aggiunge un'attività'
        insert = ButtonFactory.createButton(SettingName.GUIINSERT, e -> {
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
