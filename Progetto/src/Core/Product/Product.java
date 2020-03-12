
package Core.Product;

import Core.Common.Item;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
 * Classe utilizzata per rappresentare un prodotto
 * Tale prodotto viene rappresentato mediante l'utilizzo delle {@link HashMap}
 * Nello specifico delle {@link LinkedHashMap} che permettono di aggiungere nuovi campo per uno specifico prodotto  in modo ordinato
 * Implementa le interfacce 'Item' : {@link Item} e 'Serializable' : {@link java.io.Serializable}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class Product implements Item, java.io.Serializable {
    /**
     * ID univoco che viene assegnato ad ogni prodotto
     */
    private int ID;
    /**
     * IdCounter incrementale che permette di assegnare un nuovo ID ad un nuovo prodotto
     */
    private static int IDCOUNTER = 0;
    /**
     * attributes è una LinkedHashMap che contiene tutti i campi di un prodotto nel formato [ Campo, Valore ]
     */
    private HashMap<String, String> attributes = new LinkedHashMap();

    /**
     * Costruttore che per ogni nuovo prodotto assegna un ID incrementale
     */
    public Product(){
        IDCOUNTER++;
        ID = IDCOUNTER;
    }

    /**
     * Costruttore privato che ci permette di fare la clonazione profonda di un prodotto
     * Quest'ultimo utilizzato all'interno della classe 'ProductMemento' : {@link ProductMemento}
     * @param ID da assegnare al nuovo prodotto
     * @param attributes lista da assegnare al nuovo prodotto
     */
    private Product(int ID, HashMap<String, String> attributes){
        this.ID = ID;
        this.attributes = attributes;
    }

    /**
     * Metodo ereditato dall'interfaccia 'Item' : {@link Item}, permette di aggiungere un nuovo campo all'interno del prodotto
     * @param key nome del campo da aggiungere
     * @param value valore da assegnare al campo
     */
    @Override
    public void add(String key, String value) {
        this.attributes.put(key, value);
    }
    /**
     * Metodo ereditato dall'interfaccia 'Item' : {@link Item}, permette di modificare un campo già esistente all'interno del prodotto
     * @param key nome del campo da modificare
     * @param value valore da sostituire al campo
     */
    @Override
    public void replace(String key, String value) {
        this.attributes.replace(key, value);
    }

    /**
     * Metodo che permette la clonazione profonda di un prodotto
     * @param c prodotto da clonare
     * @return una copia del prodotto passata a parametro
     */
    public static Product clone(Product c){
        return new Product(c.ID, (HashMap<String, String>) c.attributes.clone());
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
     * @return stringa contenenti tutti i valori di un prodotto separati dal carattere ';'
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
     * @param key campo del prodotto
     * @return valore assegnato a tale campo
     */
    @Override
    public String get(String key) {
        return this.attributes.get(key);
    }
    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} restituisce ID di un prodotto
     * @return ID prodotto
     */
    @Override
    public int getID(){
        return this.ID;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} imposta ID di un prodotto
     * @param id ID da assegnare ad un prodotto
     */
    @Override
    public void setID(int id){
        this.ID = id;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} imposta idCounter di un prodotto
     * @param idCounter IDCounter da assegnare ad un prodotto
     */
    @Override
    public void setIDCounter(int idCounter) {
        this.IDCOUNTER = idCounter;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} restituisce IDCounter di un prodotto
     * @return IDCounter di un prodotto
     */
    @Override
    public int getIDCounter() {
        return IDCOUNTER;
    }


}