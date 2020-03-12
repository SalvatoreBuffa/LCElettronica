package GUI.Product;



import Core.Common.SettingName;
import Core.Product.ProductBuilder;
import GUI.Common.ButtonFactory;
import GUI.Common.ItemTableModel;

import javax.swing.*;
import java.awt.*;
/**
 * Classe che crea un frame di inserimento di un prodotto
 * Viene implementata l'interfaccia 'JFrame':{@link javax.swing.JFrame}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
public class ProductInsertFrame extends JFrame {
    //frame singleton e il modello su cui desideriamo efettuare l'operazione di inserimento
    private static JFrame frame;
    private ItemTableModel model;

    //rispettivamente il panello principale, quello con il form e quello con il bottone
    private JPanel mainPanel;
    private JPanel detailsPanel;
    private JPanel buttonPanel;

    //bottone per l'inserimento
    private JButton insert;

    //labels e Text fields con i vari attributti del prodotto
    private JLabel lbltitle;
    private JTextField title;

    private JLabel lblbrand;
    private JTextField brand;

    private JLabel lblmodel;
    private JTextField txtmodel;

    private JLabel lbldescription;
    private JTextArea description;

    private JLabel lblquantity;
    private JTextField quantity;

    private JLabel lblpurchase;
    private JTextField purchase;

    private JLabel lblselling;
    private JTextField selling;

    //metodo static per creare un frame singleton, se è già presente lo elimina e lo ricrea
    //utile per evitare di aprire più finestre contemporaneamente
    public static void buildFrame(ItemTableModel model){
        if( frame == null){
            frame = new ProductInsertFrame(model);
        }else{
            frame.dispose();
            frame = new ProductInsertFrame(model);
        }
    }

    /**
     * Costruttore
     * @param model Il modello su cui inserire un prodotto
     */
    private ProductInsertFrame(ItemTableModel model){
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
        productForm();

        //renderizziamo i bottni con le operazioni
        createButtonPanel();

        //terminiamo l'operazione di creazione del frame
        finish();
    }

    /**
     * Metodo che crea il form per inserire i campi di un prodotto
     */
    private void productForm(){
        //creiamo e settiamo il layout con un grid bag layout
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;

        /*
        nelle rihe di codice a seguire creiamo le label e i textfield che idnetificano gli attributi
        di una riparazione, tramite il constraint specifichiamo la posizione.
        */
        lbltitle = new JLabel();
        lbltitle.setText("Titolo:");
        title = new JTextField(15);
        constraint.gridx = 0;
        constraint.gridy = 0;
        detailsPanel.add(lbltitle,constraint);
        constraint.gridx = 0;
        constraint.gridy = 1;
        detailsPanel.add(title,constraint);

        lblbrand = new JLabel();
        lblbrand.setText("Marca:");
        brand = new JTextField(15);
        constraint.gridx = 1;
        constraint.gridy = 0;
        detailsPanel.add(lblbrand,constraint);
        constraint.gridx = 1;
        constraint.gridy = 1;
        detailsPanel.add(brand,constraint);

        lblmodel = new JLabel();
        lblmodel.setText("Modello:");
        txtmodel = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 2;
        detailsPanel.add(lblmodel,constraint);
        constraint.gridx = 0;
        constraint.gridy = 3;
        detailsPanel.add(txtmodel,constraint);

        lblquantity = new JLabel();
        lblquantity.setText("Quantità:");
        quantity = new JTextField();
        constraint.gridx = 1;
        constraint.gridy = 2;
        detailsPanel.add(lblquantity,constraint);
        constraint.gridx = 1;
        constraint.gridy = 3;
        detailsPanel.add(quantity,constraint);

        constraint.gridwidth = 2;
        lbldescription = new JLabel();
        lbldescription.setText("Descrizione:");
        description = new JTextArea();
        description.setRows(4);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setBorder(title.getBorder());
        JScrollPane sp = new JScrollPane(description);
        constraint.gridx = 0;
        constraint.gridy = 4;
        detailsPanel.add(lbldescription,constraint);
        constraint.gridheight= 3;
        constraint.gridx = 0;
        constraint.gridy = 5;
        detailsPanel.add(sp,constraint);

        constraint.gridwidth = 1;
        constraint.gridheight= 1;
        lblpurchase = new JLabel();
        lblpurchase.setText("Prezzo d'acquisto:");
        purchase = new JTextField();
        constraint.gridx = 0;
        constraint.gridy = 9;
        detailsPanel.add(lblpurchase,constraint);
        constraint.gridx = 0;
        constraint.gridy = 10;
        detailsPanel.add(purchase,constraint);

        lblselling = new JLabel();
        lblselling.setText("Prezzo di vendita:");
        selling = new JTextField();
        constraint.gridx = 1;
        constraint.gridy = 9;
        detailsPanel.add(lblselling,constraint);
        constraint.gridx = 1;
        constraint.gridy = 10;
        detailsPanel.add(selling,constraint);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);
    }

    /**
     * Metodo che renderizza i bottoni per le varie operazioni
     */
    private void createButtonPanel() {
        //creiamo il pannello
        buttonPanel = new JPanel();
        //creiamo il bottone "Inserisci" che al click aggiunge un prodotto
        insert = ButtonFactory.createButton(SettingName.GUIINSERT, e -> {
            ProductBuilder builder = new ProductBuilder();
            builder.addTitle(title.getText());
            builder.addBrand(brand.getText());
            builder.addModel(txtmodel.getText());
            builder.addDescription(description.getText());
            try {
                builder.addQuantity(Integer.parseInt(quantity.getText()));
            }catch (NumberFormatException ex){
                builder.addQuantity(0);
            }
            try{
                builder.addPuchasePrice(Double.parseDouble(purchase.getText()));
            }catch (NumberFormatException ex){
                builder.addPuchasePrice(0);
            }
            try{
                builder.addSellingPrice(Double.parseDouble(selling.getText()));
            }catch (NumberFormatException ex){
                builder.addSellingPrice(0);
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
