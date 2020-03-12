package GUI.Common;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Classe che modellizza il pattern della data da scrivere nelle JFormattedTextField
 * estende la classe astratta 'AbstractFormatter':{@link javax.swing.JFormattedTextField.AbstractFormatter}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    //pattern da seguire e il relativo formatter
    private String datePattern = "dd/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    /**
     * Concretizzazione del metodo 'stringToValue':{@link javax.swing.JFormattedTextField.AbstractFormatter#stringToValue(String)}
     * che restituisce un oggetto formattato
     * @param text Stringa da formattare
     * @return Oggetto formatatto
     * @throws ParseException se la stringa non rispetta il pattern
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    /**
     * Concretizzazione del metodo 'valueToString':{@link javax.swing.JFormattedTextField.AbstractFormatter#valueToString(Object)}
     * che dato un oggetto restituisce la stringa formattata
     * @return Stringa formattata secondo il pattern a partire dall'oggetto
     */
    @Override
    public String valueToString(Object value) {
        if (value != null && value instanceof Calendar) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }

}