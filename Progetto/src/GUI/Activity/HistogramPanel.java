package GUI.Activity;



import Core.Common.SettingName;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pannello che disegna un istogramma entrate/uscite
 * Viene implementata la classe 'JPanel':{@link javax.swing.JPanel}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class HistogramPanel extends JPanel {
    //i due sottopannelli che identificano le barre e le labels
    private JPanel barPanel;
    private JPanel labelPanel;

    //le due barre, una per le entrate e una per le uscite
    //viene utilizzata la classe interna 'Bar'
    private Bar inBar;
    private Bar outBar;
    private List<Bar> bars = new ArrayList<>();

    /**
     * Costruttore
     */
    public HistogramPanel() {
        //setto i bordi del pannello principale
        setBorder( new EmptyBorder(10, 10, 10, 10) );
        setLayout( new BorderLayout() );

        //creo le colonne
        addHistogramColumns();

        //creo il pannello delle barre
        barPanel = new JPanel( new GridLayout(1, 0, 10, 0) );
        Border outer = new MatteBorder(1, 1, 1, 1, Color.BLACK);
        Border inner = new EmptyBorder(10, 10, 0, 10);
        Border compound = new CompoundBorder(outer, inner);
        barPanel.setBorder( compound );

        //creo il pannello delle labels
        labelPanel = new JPanel( new GridLayout(1, 0, 10, 0) );
        labelPanel.setBorder( new EmptyBorder(5, 10, 0, 10) );

        //aggiungo i due sottopannelli a quello principale
        add(barPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.PAGE_END);
    }

    /**
     * Metodo che crea le due barre, e le aggiunge alla lista delle barre
     */
    private void addHistogramColumns() {
        inBar =  new Bar(SettingName.ACTIVITYIN, 0, Color.GREEN);
        outBar = new Bar(SettingName.ACTIVITYOUT, 0, Color.RED);
        bars.add(inBar);
        bars.add(outBar);
    }

    /**
     * Metodo che setta il valore che deve rappresentare la barra delle entrate
     * @param in Valore da rappresentare
     */
    public void setIn(double in){
        inBar.value = in;
    }

    /**
     * Metodo che setta il valore che deve rappresentare la barra delle uscite
     * @param out Valore da rappresentare
     */
    public void setOut(double out){
        outBar.value = out;
    }

    /**
     * Metodo che disegna l'istogramma
     */
    public void layoutHistogram() {
        //resettiamo i due sottopannelli
        barPanel.removeAll();
        labelPanel.removeAll();

        //troviamo il massimo tra le entrate e le uscite
        double maxValue = Math.max(inBar.getValue(), outBar.getValue());

        //per entrambe la barre nella lista, le disegniamo come se fossero label con un'icona
        for (Bar bar: bars) {
            JLabel label = new JLabel(bar.getValue() + "");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setVerticalAlignment(JLabel.BOTTOM);
            double barHeight = (bar.getValue() * 200) / maxValue;
            //icona definita da noi in un'altra classe interna, che è il rettangolo della barra
            Icon icon = new ColorIcon(bar.getColor(), 50, (int) Math.ceil(barHeight));
            label.setIcon( icon );
            barPanel.add( label );

            JLabel barLabel = new JLabel( bar.getLabel() );
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add( barLabel );
        }
    }

    /**
     * Classe interna che modellizza il concetto di barra dell'istogramma
     * @author Buffa Salvatore
     * @author Domingo Emanuele
     * @version 1.0
     * @since 1.0
     */
    private class Bar {
        //ogni barra avrà una label, un valore da rappresentare e un colore
        private String label;
        private double value;
        private Color color;

        /**
         * Costruttore
         * @param label label della barra
         * @param value valore della barra
         * @param color colore della barra
         */
        public Bar(String label, double value, Color color) {
            this.label = label;
            this.value = value;
            this.color = color;
        }

        /**
         * Getter della label
         * @return la label della barra
         */
        public String getLabel() {
            return label;
        }

        /**
         * Getter del valore
         * @return il valore della barra
         */
        public double getValue() {
            return value;
        }

        /**
         * Getter del colore
         * @return il colore della barra
         */
        public Color getColor() {
            return color;
        }
    }

    /**
     * Classe interna che disegna il rettagolo della barra come icona
     * implementa l'interfaccia 'Icon':{@link javax.swing.Icon}
     * @author Buffa Salvatore
     * @author Domingo Emanuele
     * @version 1.0
     * @since 1.0
     */
    private class ColorIcon implements Icon {
        //il rettangolo è costituito da colore, altezza e larghezza
        private Color color;
        private int width;
        private int height;

        /**
         * Costruttore
         * @param color colore del rettangolo
         * @param width larghezza del rettangolo
         * @param height altezza del rettangolo
         */
        public ColorIcon(Color color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        /**
         * concretizzazione del getter della larghezza
         * @return la larghezza del rettangolo
         */
        @Override
        public int getIconWidth() {
            return width;
        }

        /**
         * concretizzazione del getter dell'altezza
         * @return l'altezza del rettangolo
         */
        @Override
        public int getIconHeight() {
            return height;
        }

        /**
         * concretizzazione del metodo 'paintIcon':{@link javax.swing.Icon#paintIcon(Component, Graphics, int, int)} che disegna il rettangolo
         */
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
    }

}
