package Core.Activity;

import Core.Common.Item;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
 * Classe utilizzata per rappresentare una attività
 * Tale attività viene rappresentata mediante l'utilizzo delle {@link HashMap}
 * Nello specifico delle {@link LinkedHashMap} che permettono di aggiungere nuovi campo per una specifica attività in modo ordinato
 * Implementa le interfacce 'Item' : {@link Item} e 'Serializable' : {@link java.io.Serializable}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class Activity implements Item, java.io.Serializable {

    /**
     * ID univoco che viene assegnato ad ogni attivià
     */
    private int ID;
    /**
     * IdCounter incrementale che permette di assegnare un nuovo ID ad una nuova attività
     */
    private static int IDCOUNTER = 0;
    /**
     * attributes è una LinkedHashMap che contiene tutti i campi di una attività nel formato [ Campo, Valore ]
     */
    private HashMap<String, String> attributes = new LinkedHashMap();

    /**
     * Costruttore che per ogni nuova attività assegna un ID incrementale
     */
    public Activity(){
        IDCOUNTER++;
        ID = IDCOUNTER;
    }

    /**
     * Costruttore privato che ci permette di fare la clonazione profonda di una attività
     * Quest'ultima utilizzata all'interno della classe 'ActivityMemento' : {@link ActivityMemento}
     * @param ID da assegnare alla nuova attività
     * @param attributes lista da assegnare alla nuova attività
     */
    private Activity(int ID, HashMap<String, String> attributes){
        this.ID = ID;
        this.attributes = attributes;
    }

    /**
     * Metodo ereditato dall'interfaccia 'Item' : {@link Item}, permette di aggiungere un nuovo campo all'interno della attività
     * @param key nome del campo da aggiungere
     * @param value valore da assegnare al campo
     */
    @Override
    public void add(String key, String value) {
        this.attributes.put(key, value);
    }

    /**
     * Metodo ereditato dall'interfaccia 'Item' : {@link Item}, permette di modificare un campo già esistente all'interno della atività
     * @param key nome del campo da modificare
     * @param value valore da sostituire al campo
     */
    @Override
    public void replace(String key, String value) {
        this.attributes.replace(key, value);
    }

    /**
     * Metodo che permette la clonazione profonda di una attività
     * @param c attività da clonare
     * @return una copia della attività passata a parametro
     */
    public static Activity clone(Activity c){
        return new Activity(c.ID, (HashMap<String, String>) c.attributes.clone());
    }

    @Override
    public String toString(){
        String s = "";
        s += "ID:" + this.ID + " ";
        for( String att : attributes.keySet()){
            s += attributes.get(att)+" ";
        }
        return s;
    }

    /**
     * @return stringa contenenti tutti i valori di una attività separati dal carattere ';'
     */
    public String export(){
        String s = "";
        for( String att : attributes.keySet()){
            s += attributes.get(att)+";";
        }
        return s;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item' : {@link Item} che dato un campo restituisce il corrispondente valore
     * @param key campo della attività
     * @return valore assegnato a tale campo
     */
    @Override
    public String get(String key) {
        return this.attributes.get(key);
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} restituisce ID di una attività
     * @return ID attività
     */
    @Override
    public int getID(){
        return this.ID;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} imposta ID di una attività
     * @param id ID da assegnare ad una attività
     */
    @Override
    public void setID(int id){
        this.ID = id;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} imposta IDCounter di una attività
     * @param id IDCounter da assegnare ad una attività
     */
    @Override
    public void setIDCounter(int id) {
        IDCOUNTER = id;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} restituisce IDCounter di una attività
     * @return IDCounter di una attività
     */
    @Override
    public int getIDCounter() {
        return IDCOUNTER;
    }
}
