
package Core.Customer;

import Core.Common.Item;
import Core.Common.SettingName;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
 * Classe utilizzata per rappresentare un Customer
 * Tale customer viene rappresentato mediante l'utilizzo delle {@link HashMap}
 * Nello specifico delle {@link LinkedHashMap} che permettono di aggiungere nuovi campo per una specifico customer in modo ordinato
 * Implementa le interfacce 'Item' : {@link Item} e 'Serializable' : {@link java.io.Serializable}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */


public class Customer implements Item, java.io.Serializable{

    /**
     * IdCounter incrementale che permette di assegnare un nuovo ID ad un nuovo customer
     */
    private static int IDCOUNTER = 0;
    /**
     * ID univoco che viene assegnato ad ogni customer
     */
    private int ID;
    /**
     * type è una stringa che permette di differenziare se il customer è di tipo Person o Company
     */
    private String type;
    /**
     * attributes è una LinkedHashMap che contiene tutti i campi di un customer nel formato [ Campo, Valore ]
     */
    private HashMap<String, String> attributes = new LinkedHashMap<>();

    /**
     * Costruttore che per ogni nuovo customer assegna un ID incrementale
     * @param type tipo di customer (Cliente, Azienda)
     */
    public Customer(String type) {
        this.type= type;
        IDCOUNTER++;
        ID = IDCOUNTER;
    }

    /**
     * Costruttore privato che ci permette di fare la clonazione profonda di un customer
     * Quest'ultimo utilizzato all'interno della classe 'CustomerMemento' : {@link CustomerMemento}
     * @param ID da assegnare al nuovo customer
     * @param attributes lista da assegnare al nuovo customer
     */
    private Customer(int ID, String type, HashMap<String, String> attributes){
        this.ID = ID;
        this.type = type;
        this.attributes = attributes;
    }

    /**
     * Metodo ereditato dall'interfaccia 'Item' : {@link Item}, permette di aggiungere un nuovo campo all'interno del customer
     * @param key nome del campo da aggiungere
     * @param value valore da assegnare al campo
     */
    @Override
    public void add(String key, String value) {
        this.attributes.put(key, value);
    }

    /**
     * Metodo ereditato dall'interfaccia 'Item' : {@link Item}, permette di modificare un campo già esistente all'interno del customer
     * @param key nome del campo da modificare
     * @param value valore da sostituire al campo
     */
    @Override
    public void replace(String key, String value) {
        this.attributes.replace(key, value);
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item' : {@link Item} che dato un campo restituisce il corrispondente valore
     * @param key campo del customer
     * @return valore assegnato a tale campo
     */
    @Override
    public String get(String key) {
        return this.attributes.get(key);
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} restituisce ID di un customer
     * @return ID customer
     */
    @Override
    public int getID(){
        return this.ID;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} imposta ID di un customer
     * @param newID ID da assegnare ad un customer
     */
    @Override
    public void setID(int newID){
        this.ID = newID;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} imposta IDCounter di un customer
     * @param id IDCounter da assegnare ad un customer
     */
    @Override
    public void setIDCounter(int id) {
        IDCOUNTER = id;
    }

    /**
     * Metodo ereditato dalla interfaccia 'Item':{@link Item} restituisce IDCounter di un customer
     * @return IDCounter di un customer
     */
    @Override
    public int getIDCounter() {
        return IDCOUNTER;
    }

    /**
     * Metodo che permette la clonazione profonda di un customer
     * @param c customer da clonare
     * @return una copia del customer passata a parametro
     */
    public static Customer clone(Customer c){
        return new Customer(c.ID, c.getType(), (HashMap<String, String>) c.attributes.clone());
    }

    public String toString(){
        String s = "";
        s += "ID: " + this.ID + " ";
        for( String att : attributes.keySet()){
            s += attributes.get(att)+" ";
        }
        return s;
    }

    /**
     * @return stringa contenenti tutti i valori di un customer separati dal carattere ';'
     */
    public String export(){
        String s = "";
        for( String att : attributes.keySet()){
            s += attributes.get(att)+";";
        }
        return s;
    }

    /**
     * @return restituisce una stringa contenente il tipo del customer (Person, Company)
     */
    public String getType(){
        return this.type;
    }

    /**
     * Metodo per confrontare due clienti
     * @param c customer da confrontare
     * @return true se due customer sono uguali, false se sono diversi
     */
    public boolean isEqualTo(Customer c){
        boolean value = true;
        if(type.equals(SettingName.PERSONTYPE)){
            if(!this.get(SettingName.PERSONNAME).equals(c.get(SettingName.PERSONNAME))) value = false;
            if(!this.get(SettingName.PERSONSURNAME).equals(c.get(SettingName.PERSONSURNAME))) value = false;
            if(!this.get(SettingName.PERSONPERSONALID).equals(c.get(SettingName.PERSONPERSONALID))) value = false;
            if(!this.get(SettingName.PERSONCODESDI).equals(c.get(SettingName.PERSONCODESDI))) value = false;
        }else{
            if(!this.get(SettingName.COMPANYNAME).equals(c.get(SettingName.COMPANYNAME))) value = false;
            if(!this.get(SettingName.COMPANYCODESDI).equals(c.get(SettingName.COMPANYCODESDI))) value = false;
        }
        if(!this.get(SettingName.CUSTOMERPIVA).equals(c.get(SettingName.CUSTOMERPIVA))) value = false;
        if(!this.get(SettingName.CUSTOMERMAIL).equals(c.get(SettingName.CUSTOMERMAIL))) value = false;
        if(!this.get(SettingName.CUSTOMERPEC).equals(c.get(SettingName.CUSTOMERPEC))) value = false;
        if(!this.get(SettingName.CUSTOMERTELEPHONE).equals(c.get(SettingName.CUSTOMERTELEPHONE))) value = false;
        if(!this.get(SettingName.CUSTOMERADDRESS).equals(c.get(SettingName.CUSTOMERADDRESS))) value = false;
        if(!this.get(SettingName.CUSTOMERSHIPPINGADDRESS).equals(c.get(SettingName.CUSTOMERSHIPPINGADDRESS))) value = false;
        return value;
    }
}