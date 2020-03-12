package Core.Customer;



import Core.Common.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe utilizzata per la manipolazione dei customer, ossia tutte le operazioni di inserimento, modifica, cancellazione, undo, ricerca
 * Vengono implementate le interfacce 'CudManager':{@link Core.Common.CudManager} e 'Serializable':{@link java.io.Serializable}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
public class CustomerManager implements CudManager<Customer>, java.io.Serializable {

    private static List<Customer> customers;
    private CustomerMemento prevCustomer;


    public CustomerManager(){

        customers = new ArrayList<>();
        prevCustomer = new CustomerMemento(null, "");
    }

    /**
     * Metodo ereditato dalla interfaccia 'CudManager':{@link CudManager} permette l'inserimento di un nuovo customer
     * @param item customer da inserire
     */
    @Override
    public void create(Customer item) {
        UserLogManager.notify(SettingName.LOGINFO, "Aggiunto cliente "+item);
        saveState(item, SettingName.OPERATIONCREATE);
        customers.add(item);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CudManager':{@link CudManager} permette la modifica di un customer
     * @param oldCustomerID ID del customer che si vuole modificare
     * @param newCustomer nuovo customer che si vuole sovrascrivere
     */
    @Override
    public void update(int oldCustomerID, Customer newCustomer){
        Customer c = searchElement(oldCustomerID);
        UserLogManager.notify(SettingName.LOGINFO, "Modificato cliente "+c+" con "+newCustomer);
        Customer oldCustomer = Customer.clone(c);
        saveState(oldCustomer, SettingName.OPERATIONUPDATE);
        newCustomer.setID(oldCustomerID);
        customers.set(customers.indexOf(c), newCustomer);
    }

    /**
     * Metodo ereditato dalla interaccia 'CudManager':{@link CudManager} permette la cancellazione di un customer
     * @param ID ID del customer da cancellare
     */
    @Override
    public void delete(int ID) {
        Customer c = searchElement(ID);
        UserLogManager.notify(SettingName.LOGINFO, "Rimosso cliente "+c);
        saveState(c, SettingName.OPERATIONDELETE);
        customers.remove(c);
    }

    /**
     * Metodo ereditato dalla interaccia 'CudManager':{@link CudManager} permette la ricerca di un customer mediante ID
     * @param ID ID del customer da cercare
     * @return customer o null se non presente
     */
    @Override
    public Customer searchElement(int ID) {
        return customers.stream().filter(c -> c.getID() == ID).findFirst().orElse(null);
    }

    /**
     * Metodo che restituisce la lista dei customer
     * @return lista dei customer presenti nel manager
     */
    public static List<Customer> getList(){
        return customers;
    }

    /**
     * Metodo che permette l'inserimento di una nuova lista sostituendo la vecchia
     * @param list sostituisce l'attuale lista
     */
    public void setList(List<Customer> list){
        customers = list;
    }
    @Override
    public String toString() {
        String s = customers.stream().map(c -> c.toString() + "\n").collect(Collectors.joining("", "Clienti: \n", ""));
        return s;
    }

    /**
     * Metodo che salva lo stato dell'ultima operazione eseguita
     * @param customer Customer da salvare prima della modifica
     * @param operation l'operazione eseguita sul customer (inserimento, cancellazione, modifica)
     */
    public void saveState(Customer customer, String operation){
        this.prevCustomer = new CustomerMemento(customer, operation);
    }

    /**
     * Metodo che permette undo della operazione di inserimento, ovvero l'eliminazione del customer
     */
    @Override
    public void restoreCreate(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinato inserimento di " + this.prevCustomer.getState());
        customers.removeIf(c -> c.getID() == this.prevCustomer.getState().getID());
    }

    /**
     * Metodo che permette undo della operazione di cancellazione, ovvero l'inserimento del customer
     */
    @Override
    public void restoreDelete(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinata rimozione di " + this.prevCustomer.getState());
        customers.add(this.prevCustomer.getState());
    }

    /**
     * Metodo che permette undo della operazione di modifica
     */
    @Override
    public void restoreUpdate(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinata modifica di " + this.prevCustomer.getState());
        customers.set(customers.indexOf(searchElement(this.prevCustomer.getState().getID())), this.prevCustomer.getState());
    }

    /**
     * Metodo che restituisce l'ultima operazione eseguita
     * @return l'attività originale prima di eseguire l'operazione
     */
    public CustomerMemento getMemento(){
        return this.prevCustomer;
    }

    /**
     * Metodo che permette l'esportazione della lista di customer all'interno di un file CSV
     *          *Rispettare la sintassi
     *          *Campo1;Campo2;...;CampoN;
     *          *Valore1;Valore2;...;ValoreN;
     *          *Nel caso in cui uno dei campi non è presente inserire un semplice carattere ';'
     *          *Campo1;Campo2;...;CampoN;
     *          *Valore1;;Valore3;;...;ValoreN;
     * @param fileName percorso del file CSV
     * @param type tipo di customer (Cliente, Azienda)
     */
    public void exportData(String fileName, String type) {
        CustomerCSVBuffer export = new CustomerCSVBuffer();
        export.createCSVFile(this.customers, fileName, type);
    }

    /**
     * Metodo che permette l'importazione della lista customer a partire da un file CSV
     *      * Rispettare la sintassi
     *      * Campo1;Campo2;...;CampoN;
     *      * Valore1;Valore2;...;ValoreN;
     *      * Nel caso in cui uno dei campi non è presente inserire un semplice carattere ';'
     *      * Campo1;Campo2;...;CampoN;
     *      * Valore1;;Valore3;;...;ValoreN;
     * @param fileName percorso del file CSV
     * @param type tipo di customer (Cliente, Azienda)
     */
    public void importData(String fileName, String type){
        CustomerCSVBuffer importData = new CustomerCSVBuffer();
        customers = importData.readCSVFile(fileName, type);
    }

}
