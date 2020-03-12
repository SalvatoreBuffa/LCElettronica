package GUI.Activity;


import Core.Activity.Activity;
import Core.Common.SettingName;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe che crea un frame che visualizza un grafico entrate/uscite di una tipologia di attività
 * Viene implementata l'interfaccia 'JFrame':{@link javax.swing.JFrame}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */


public class ChartFrame extends JFrame {
    //frame singleton
    private static JFrame frame;

    //rispettivamente la lista di tutte le attività, lista dei tipi di attività e lista delle attività del tipo selezionato
    private List<Activity> activities;
    private List<String> typesList;
    private List<Activity> activityList;

    //rispettivamente il tipo selezionato, entrate e uscite di un set di attività e il grafico
    private String selectedType;
    private double in, out;
    private HistogramPanel chart;

    //rispettivamente il pannello principale del frame, il pannello con la combobox per scegliere il tipo di attività
    //il pannello del grafico e il pannello con il report (totale = in-out)
    private JPanel mainPanel;
    private JPanel choosePanel;
    private JPanel chartPanel;
    private JPanel reportPanel;

    //combobox con tutti i tipi di attività
    private JComboBox<String> typeCombo;

    //label con la stringa contenente il report con il totale
    private JLabel lblreport;

    //metodo static per creare un frame singleton, se è già presente lo elimina e lo ricrea
    //utile per evitare di aprire più finestre contemporaneamente
    public static void buildFrame(List<Activity> activities){
        if( frame == null){
            frame = new ChartFrame(activities);
        }else{
            frame.dispose();
            frame = new ChartFrame(activities);
        }
    }

    /**
     * Costruttore
     * @param activities la lista di tutte le attività presenti
     */
    private ChartFrame(List<Activity> activities){
        //creiamo il frame
        super(SettingName.GUISHOW);
        //frame.setIconImage(new ImageIcon(SettingName.ICONPATH).getImage());
        //associamo le attività
        this.activities = activities;

        //settiamo il layout del frame con un border layout
        BorderLayout layout = new BorderLayout();
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        add(mainPanel);

        //creiamo la lista delle tipologie
        typesList = new ArrayList<>();
        for(Activity a: activities){
            if(!typesList.contains(a.get(SettingName.ACTIVITYTYPE).toLowerCase())){
                typesList.add(a.get(SettingName.ACTIVITYTYPE).toLowerCase());
            }
        }

        //inizializziamo la lista delle attività di una certa tipologia
        activityList = new ArrayList<>();

        //renderizziamo il pannello contenete il report finale
        renderReportPanel();

        //renderizziamo il grafico
        renderChartPanel();

        //renderizziamo le combobox per selezionare il tipo di attività interessate
        renderChoosePanel();

        //aggiungiamo al pannello principale
        mainPanel.add(choosePanel, BorderLayout.NORTH);
        mainPanel.add(chartPanel, BorderLayout.CENTER);
        mainPanel.add(reportPanel, BorderLayout.SOUTH);

        //terminiamo l'operazione di creazione del frame
        finish();
    }

    /**
     * Metodo che renderizza le combobox di scelta del tipo di attività di cui si vuole analizzare il report
     */
    private void renderChoosePanel(){
        //inizializzo il pannello e la combobox
        choosePanel = new JPanel();
        typeCombo = new JComboBox<>();

        //di default seleziono il primo tipo e aggiorno tutti i componenti
        selectedType = typesList.get(0);
        refreshComboItems();
        refreshReportLabel();
        refreshChartPanel();

        //aggiungo i tipi nella lista come elementi della combobox
        for(String s: typesList){
           typeCombo.addItem(s);
        }

        //ogni volta che seleziono una nuova entry dalla lista, aggiorno il tipo selezionato,
        //svuoto la lista con le attività del vecchio tipo e aggiorno tutti i componenti
        typeCombo.addActionListener(e -> {
            selectedType = typeCombo.getSelectedItem().toString();
            activityList.clear();
            refreshComboItems();
            refreshReportLabel();
            refreshChartPanel();
        });

        //aggiungo la combo al pannello
        choosePanel.add(typeCombo);
    }

    /**
     * Metodo che renderizza il pannello con il grafico
     */
    private void renderChartPanel(){
        //creo il pannello
        chartPanel = new JPanel();

        //creo il grafico
        chart = new HistogramPanel();

        //aggiungo il grafico
        chartPanel.add(chart);
    }

    /**
     * Metodo che renderizza la label con il report
     */
    private void renderReportPanel(){
        //creo il pannello
        reportPanel = new JPanel();

        //creo la label
        lblreport = new JLabel();
        lblreport.setFont(new Font(SettingName.FONT, Font.BOLD, 18));

        //aggiungo la label
        reportPanel.add(lblreport);
    }

    /**
     * Metodo che aggiorna gli elementi della combo box in base al tipo
     */
    private void refreshComboItems() {
        for (Activity a : activities) {
            if (a.get(SettingName.ACTIVITYTYPE).toLowerCase().equals(selectedType)) {
                activityList.add(a);
            }
        }
    }

    /**
     * Metodo che aggiorna il grafico
     */
    private void refreshChartPanel(){
        chart.setIn(in);
        chart.setOut(out);

        chart.layoutHistogram();
    }

    /**
     * Metodo che aggiorna la label di report
     */
    private void refreshReportLabel(){
        double total;
        in = 0;
        out = 0;

        for(Activity a: activityList){
            if(Boolean.parseBoolean(a.get(SettingName.ACTIVITYREVENUE))){
                out += Double.parseDouble(a.get(SettingName.ACTIVITYPRICE));
            }else{
                in += Double.parseDouble(a.get(SettingName.ACTIVITYPRICE));
            }
        }

        total = in-out;
        if(total > 0){
            lblreport.setForeground(Color.GREEN);
        }else{
            lblreport.setForeground(Color.RED);
        }

        lblreport.setText("Il netto dell'attività "+selectedType+" è "+total);
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
