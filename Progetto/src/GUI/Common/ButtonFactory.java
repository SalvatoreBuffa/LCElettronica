package GUI.Common;



import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Classe factory per creare bottoni con listner
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class ButtonFactory {

    /**
     * Metodo statico che crea un'istanza di un bottone e la restituisce
     * @param text testo del bottone
     * @param action evento al click
     * @return bottone
     */
    public static JButton createButton(String text, ActionListener action){
        JButton button = new JButton(text);
        button.addActionListener(action);

        return button;
    }
}
