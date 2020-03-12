package GUI.Product;



import Core.Common.Item;
import Core.Common.SettingName;
import Core.Product.Product;
import Core.Product.ProductBuilder;
import GUI.Common.ButtonFactory;
import GUI.Common.ItemTableModel;

import javax.swing.*;
import java.awt.*;
/**
 * Classe che crea un frame con i campi di un prodotto, con la possibilità di modificare o eliminare dalla lista
 * Viene implementata l'interfaccia 'JFrame':{@link javax.swing.JFrame}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
public class ProductDetailFrame extends JFrame {
    //prodotto di cui vogliamo vedere i dettagli, e il modello su cui desideriamo efettuare delle operazioni
    private Product product;
    private ItemTableModel model;

    //frame singleton
    private static JFrame frame;

    //rispettivamente il pannello principale, quello con i campi del prodotto e quello dei bottoni per le operazioni
    private JPanel mainPanel;
    private JPanel detailsPanel;
    private JPanel buttonPanel;

    //bottoni per le varie operazioni
    private JButton delete;
    private JButton update;
    //inizialmente la modifica è bloccata, sbloccabile con una check box
    private boolean isEditable = false;
    private JCheckBox edit;

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
    public static void buildFrame(Item i, ItemTableModel model){
        if( frame == null){
            frame = new ProductDetailFrame(i, model);
        }else{
            frame.dispose();
            frame = new ProductDetailFrame(i, model);
        }
    }

    /**
     * Costruttore
     * @param i elemento di cui visualizzare i dettagli
     * @param model modello su cui effettuare le operazioni di modifica o rimozione
     */
    private ProductDetailFrame(Item i, ItemTableModel model){
        //creiamo il frame
        super(SettingName.GUIDETAILS);

        //inizializziamo prodotto e modello
        this.product = (Product) i;
        this.model = model;

        //settiamo il layout del frame con un border layout
        BorderLayout layout = new BorderLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        add(mainPanel);

        //creiamo e settiamo il pannello con i campi del prodotto con un grid bag layout
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;

        //creiamo il form con tutti i campi
        productForm();

        //abilitiamo/disabilitiamo i campi in base a se è selezionata la modalità modifica
        areTextFiledsEnabled(isEditable);

        //renderizziamo i bottni con le operazioni
        createButtonPanel();

        //terminiamo l'operazione di creazione del frame
        finish();
    }

    /**
     * Metodo che crea il form per visualizzare le informazioni del prodotto
     */
    private void productForm(){
        //creiamo e settiamo il layout con un grid bag layout
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;
        /*
        nelle rihe di codice a seguire creiamo le label e i textfield che idnetificano gli attributi
        di un prodotto, tramite il constraint specifichiamo la posizione.
        */

        lbltitle = new JLabel();
        lbltitle.setText("Titolo:");
        title = new JTextField(product.get(SettingName.PRODUCTTITLE));
        title.setColumns(15);
        constraint.gridx = 0;
        constraint.gridy = 0;
        detailsPanel.add(lbltitle,constraint);
        constraint.gridx = 0;
        constraint.gridy = 1;
        detailsPanel.add(title,constraint);

        lblbrand = new JLabel();
        lblbrand.setText("Marca:");
        brand = new JTextField(product.get(SettingName.PRODUCTBRAND));
        brand.setColumns(15);
        constraint.gridx = 1;
        constraint.gridy = 0;
        detailsPanel.add(lblbrand,constraint);
        constraint.gridx = 1;
        constraint.gridy = 1;
        detailsPanel.add(brand,constraint);

        lblmodel = new JLabel();
        lblmodel.setText("Modello:");
        txtmodel = new JTextField(product.get(SettingName.PRODUCTMODEL));
        constraint.gridx = 0;
        constraint.gridy = 2;
        detailsPanel.add(lblmodel,constraint);
        constraint.gridx = 0;
        constraint.gridy = 3;
        detailsPanel.add(txtmodel,constraint);

        lblquantity = new JLabel();
        lblquantity.setText("Quantità:");
        quantity = new JTextField(product.get(SettingName.PRODUCTQUANTITY));
        constraint.gridx = 1;
        constraint.gridy = 2;
        detailsPanel.add(lblquantity,constraint);
        constraint.gridx = 1;
        constraint.gridy = 3;
        detailsPanel.add(quantity,constraint);

        constraint.gridwidth = 2;
        lbldescription = new JLabel();
        lbldescription.setText("Descrizione:");
        description = new JTextArea(product.get(SettingName.PRODUCTDESCRIPTION));
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
        purchase = new JTextField(product.get(SettingName.PRODUCTPURCHUASEPRICE));
        constraint.gridx = 0;
        constraint.gridy = 9;
        detailsPanel.add(lblpurchase,constraint);
        constraint.gridx = 0;
        constraint.gridy = 10;
        detailsPanel.add(purchase,constraint);

        lblselling = new JLabel();
        lblselling.setText("Prezzo di vendita:");
        selling = new JTextField(product.get(SettingName.PRODUCTSELLINGPRICE));
        constraint.gridx = 1;
        constraint.gridy = 9;
        detailsPanel.add(lblselling,constraint);
        constraint.gridx = 1;
        constraint.gridy = 10;
        detailsPanel.add(selling,constraint);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);
    }

    /**
     * Metodo che abilita o disabilità le text filed in base al valore del booleano a parametro
     * @param b se b è true abilità tutte le texfiled, le disabilita altrimenti
     */
    private void areTextFiledsEnabled(boolean b){
        this.title.setEnabled(b);
        this.brand.setEnabled(b);
        this.txtmodel.setEnabled(b);
        this.description.setEnabled(b);
        this.quantity.setEnabled(b);
        this.purchase.setEnabled(b);
        this.selling.setEnabled(b);
    }

    /**
     * Metodo che renderizza i bottoni per le varie operazioni
     */
    private void createButtonPanel(){
        //creiamo il pannello
        buttonPanel = new JPanel();

        //creiamo il bottone "Elimina" che al click rimuove un prodotto
        delete = ButtonFactory.createButton(SettingName.GUIDELETE, e -> {
            model.delete(product.getID());
            frame.dispose();
        });

        /*
        creiamo il bottone "Applica" che è abilitato/disabilitato in base al valore della checkbox.
        Al click crea un nuovo prodotto i cui parametri sono i valori
        correnti delle texfield, e aggiorniamo il prodotto corrente con uno nuovo
        */
        update = ButtonFactory.createButton(SettingName.GUIUPDATE, e -> {
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
            model.update(product.getID(), builder.getResult());
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
