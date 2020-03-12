
package Core.Repair;

import Core.Common.Item;
import Core.Common.SettingName;
import Core.Customer.Customer;
import Core.Product.ProductMemento;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;
/**
 * Classe utilizzata per rappresentare una riparazione
 * Tale riparazione viene rappresentato mediante l'utilizzo delle {@link HashMap}
 * Nello specifico delle {@link LinkedHashMap} che permettono di aggiungere nuovi campo per una specifica riparazione  in modo ordinato
 * Implementa le interfacce 'Item' : {@link Item} e 'Serializable' : {@link java.io.Serializable}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
public class Repair implements Item, java.io.Serializable {

    /**
     * ID univoco che viene assegnato ad ogni riparazione
     */
    private int ID;
    private static int IDCOUNTER = 0;
    /**
     * viene memorizzato anche un customer visto che una riparazione è associata ad un customer
     */
    private Customer customer;
    /**
     * attributes è una LinkedHashMap che contiene tutti i campi di una riparazione nel formato [Campo, Valore]
     */
    private HashMap<String, String> attributes = new LinkedHashMap();

    /**
     * Costruttore che per ogni nuova riparazione assegna un ID incrementale
     */
    public Repair(){
        IDCOUNTER++;
        ID = IDCOUNTER;
    }

    /**
     * Costruttore privato che ci permette di fare la clonazione profonda di una riparazione
     * Quest'ultima utilizzata all'interno della classe 'RepairMemento' : {@link RepairMemento}
     * @param ID da assegnare alla nuova riparazione
     * @param attributes lista da assegnare alla nuova riparazione
     */
    private Repair(int ID, HashMap<String, String> attributes){
        this.ID = ID;
        this.attributes = attributes;
    }

    /**
     * Metodo ereditato dall'interfaccia 'Item' : {@link Item}, permette di aggiungere un nuovo campo all'interno della riparazione
     * @param key nome del campo da aggiungere
     * @param value valore da assegnare al campo
     */
    @Override
    public void add(String key, String value) {
        this.attributes.put(key, value);
    }

    /**
     * Metodo ereditato dall'interfaccia 'Item' : {@link Item}, permette di modificare un campo già esistente all'interno della riparazione
     * @param key nome del campo da modificare
     * @param value valore da sostituire al campo
     */
    @Override
    public void replace(String key, String value) {
        this.attributes.replace(key, value);
    }

    /**
     * Metodo che permette la clonazione profonda di una riparazione
     * @param c riparazione da clonare
     * @return una copia della riparazione passata a parametro
     */
    public static Repair clone(Repair c){
        return new Repair(c.ID, (HashMap<String, String>) c.attributes.clone());
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item' : {@link Item} che dato un campo restituisce il corrispondente valore
     * @param key campo della riparazione
     * @return valore assegnato a tale campo
     */
    @Override
    public String get(String key) {
        return this.attributes.get(key);
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} restituisce ID di una riparazione
     * @return ID riparazione
     */
    @Override
    public int getID() {
        return this.ID;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} imposta ID di una riparazione
     * @param id ID da assegnare ad una riparazione
     */
    @Override
    public void setID(int id){
        this.ID = id;
    }

    /**
     * Metodo che restituisce il customer associato alla riparazione
     * @return customer
     */
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * Metodo che modifica il customer associato alla riparazione
     * @param newCustomer customer da assegnare alla riparazione
     */
    public void setCustomer(Customer newCustomer){
        this.customer = newCustomer;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} imposta idCounter di una riparazione
     * @param id IDCounter da assegnare ad una riparazione
     */
    @Override
    public void setIDCounter(int id) {
        IDCOUNTER = id;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} restituisce IDCounter di un repair
     * @return IDCounter di un repair
     */
    @Override
    public int getIDCounter() {
        return IDCOUNTER;
    }

    /**
     * @return idCounter dell'oggetto repair in questione
     */
    //mi serve per poter accedere dall'insert frame al counter (per il numero XXX/2020) senza avere istanza repair
    public static int getCounter() {
        /**
         * Questo metodo viene utilizzato all'interno dell'interfaccia grafica per poter ricavare idCounter dell'oggetto
         * senza la necessità di avere una istanza di tipo Repair, così da poter generare il numero di riparazione del formato XXX/AAAA
         */
        return IDCOUNTER;
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
     * @return stringa contenenti tutti i valori di una riparazione separati dal carattere ';'
     */
    public String export(){
        String s = "";
        if(customer.getType().equals(SettingName.PERSONTYPE)){
            s += customer.get(SettingName.PERSONNAME)+" "+customer.get(SettingName.PERSONSURNAME)+";";
        }else{
            s += customer.get(SettingName.COMPANYNAME)+";";
        }
        for( String att : attributes.keySet()){
            s += attributes.get(att)+";";
        }
        return s;
    }
}
