package GUI;

/**
 * Classe che crea il frame principale, con la toolbar per cambiare tra i vari pannelli
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

import Core.Activity.ActivityBackup;
import Core.Common.UserLogManager;
import Core.Customer.CustomerBackup;
import Core.Common.SettingName;
import Core.Product.ProductBackup;
import Core.Repair.RepairBackup;
import GUI.Activity.ActivityPanel;
import GUI.Common.ButtonFactory;
import GUI.Customer.CustomerPanel;
import GUI.Product.ProductPanel;
import GUI.Repair.RepairPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame {
    //la tipologia del pannello attivo (clienti, riparazioni, prodotti, attività)
    private String mode;

    //frame principale e toolbar
    private JFrame frame;
    private JToolBar toolBar;

    //pannello del frame principale, tramite il card panel passiamo da un pannello di un tipo all'altro
    private JPanel mainPanel;
    private JPanel cardPanel;
    private CardLayout cards;

    //bottoni della toolbar per cambiare pannello
    private JButton btnCustomer;
    private JButton btnRepair;
    private JButton btnProduct;
    private JButton btnActivity;

    /**
     * Metodo static che inizializza il frame
     */
    public static void init(){
        new Frame();
    }

    /**
     * Costruttore
     */
    private Frame(){
        //creiamo il frame
        frame = new JFrame();
        frame.setTitle(SettingName.TITLE);
        frame.setIconImage(new ImageIcon(SettingName.ICONPATH).getImage());

        //settiamo la modalità
        mode = SettingName.CUSTOMERMODE;

        //settiamo il layout del pannello principale
        BorderLayout layout = new BorderLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        frame.getContentPane().add(mainPanel);

        //renderizziamo la toolbar
        renderToolbar();

        //creiamo il card panel e aggiungiamo le 4 card, ognuna è il pannello della modalità di riferimento
        cardPanel = new JPanel();
        cards = new CardLayout();
        cardPanel.setLayout(cards);
        cardPanel.add(new CustomerPanel(), SettingName.CUSTOMERMODE);
        cardPanel.add(new RepairPanel(), SettingName.REPAIRMODE);
        cardPanel.add(new ProductPanel(), SettingName.PRODUCTMODE);
        cardPanel.add(new ActivityPanel(), SettingName.ACTIVITYMODE);

        //di default mostriamo la prima card, quella dei clienti
        cards.show(cardPanel, SettingName.CUSTOMERMODE);


        //aggiungiamo toolbar e card panel al pannello principale
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        //terminiamo la creazione del frame
        finish();
    }

    /**
     * Metodo che crea la toolbar
     */
    private void renderToolbar(){
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        renderToolbarButtons();
    }

    /**
     * Metodo che agigunge i bottoni alla toolbar
     */
    private void renderToolbarButtons(){
        btnCustomer = ButtonFactory.createButton(SettingName.GUICUSTOMERS, e -> {
            mode = SettingName.CUSTOMERMODE;
            cards.show(cardPanel, SettingName.CUSTOMERMODE);
            changeButton();
        });

        btnRepair = ButtonFactory.createButton(SettingName.GUIREPAIRS, e -> {
            mode = SettingName.REPAIRMODE;
            cards.show(cardPanel, SettingName.REPAIRMODE);
            changeButton();
        });

        btnProduct = ButtonFactory.createButton(SettingName.GUIPRODUCTS, e -> {
            mode = SettingName.PRODUCTMODE;
            cards.show(cardPanel, SettingName.PRODUCTMODE);
            changeButton();
        });

        btnActivity = ButtonFactory.createButton(SettingName.GUIACTIVITIES, e -> {
            mode = SettingName.ACTIVITYMODE;
            cards.show(cardPanel, SettingName.ACTIVITYMODE);
            changeButton();
        });

        changeButton();

        toolBar.add(btnCustomer);
        toolBar.add(btnRepair);
        toolBar.add(btnProduct);
        toolBar.add(btnActivity);
    }

    /**
     * Metodo che finalizza la creazione del frame con alcuni settaggi
     */
    private  void finish(){
        //non facciamo niente alla chiusura perché dobbiamo prima salvare i dati
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth()*0.8, screenSize.getHeight()*0.8);
        frame.setSize(screenSize);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            //se si vuole chiudere la finestra
            @Override
            public void windowClosing(WindowEvent e) {
                if(showCloseWindow() == 0){
                    //salviamo i dati
                    CustomerBackup.createBackup();
                    RepairBackup.createBackup();
                    ProductBackup.createBackup();
                    ActivityBackup.createBackup();

                    //chiudiamo il logger
                    UserLogManager.notify(SettingName.LOGINFO, "Chiusura applicazione");
                    UserLogManager.close();

                    //chiudiamo la finestra
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Metodo che mostra la finestra dove viene chiesto se si vuole davvero chiudere l'app
     * @return 0 se si clicca su 'Sì'
     */
    private int showCloseWindow(){
        String[] options = {SettingName.GUIYES, SettingName.GUINO};
        int x = JOptionPane.showOptionDialog(null, SettingName.GUIAREYOUSURE,
                SettingName.GUISYSTEM,
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        return x;
    }

    /**
     * Metodo che colora il testo del bottone della modalità selezionata di blu
     */
    private void changeButton(){
        if(mode.equals(SettingName.CUSTOMERMODE)){
            btnCustomer.setForeground(Color.BLUE);
            btnRepair.setForeground(Color.BLACK);
            btnProduct.setForeground(Color.BLACK);
            btnActivity.setForeground(Color.BLACK);
        }
        if(mode.equals(SettingName.REPAIRMODE)){
            btnCustomer.setForeground(Color.BLACK);
            btnRepair.setForeground(Color.BLUE);
            btnProduct.setForeground(Color.BLACK);
            btnActivity.setForeground(Color.BLACK);
        }
        if(mode.equals(SettingName.PRODUCTMODE)){
            btnCustomer.setForeground(Color.BLACK);
            btnRepair.setForeground(Color.BLACK);
            btnProduct.setForeground(Color.BLUE);
            btnActivity.setForeground(Color.BLACK);
        }
        if(mode.equals(SettingName.ACTIVITYMODE)){
            btnCustomer.setForeground(Color.BLACK);
            btnRepair.setForeground(Color.BLACK);
            btnProduct.setForeground(Color.BLACK);
            btnActivity.setForeground(Color.BLUE);
        }
    }
}
