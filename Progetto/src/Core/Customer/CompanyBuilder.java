package Core.Customer;



import Core.Common.SettingName;
/**
 * Classe utilizzata per creare un oggetto'Customer':{@link Core.Customer.Customer} di tipo Azienda
 * Implementa la interfaccia 'CustomerBuilder':{@link Core.Customer.CustomerBuilder}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class CompanyBuilder implements CustomerBuilder {

    private Customer customer;
    /**
     * Default constructor
     */
    public CompanyBuilder() {
        customer = new Customer(SettingName.COMPANYTYPE);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CustomerBuilder':{@link CustomerBuilder}
     * permette di aggiungere il valore al campo nome dell'oggetto 'Customer':{@link Customer}
     * @param name valore da attribuire al campo nome dell'oggetto 'Customer':{@link Customer}
     */
    @Override
    public void addName(String name) {
        customer.add(SettingName.COMPANYNAME, name);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CustomerBuilder':{@link CustomerBuilder}
     * permette di aggiungere il valore al campo PIVA dell'oggetto 'Customer':{@link Customer}
     * @param PIVA valore da attribuire al campo PIVA dell'oggetto 'Customer':{@link Customer}
     */
    @Override
    public void addPIVA(String PIVA) {
        customer.add(SettingName.CUSTOMERPIVA, PIVA);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CustomerBuilder':{@link CustomerBuilder}
     * permette di aggiungere il valore al campo address dell'oggetto 'Customer':{@link Customer}
     * @param address valore da attribuire al campo address dell'oggetto 'Customer':{@link Customer}
     */
    @Override
    public void addAddress(String address) {
        customer.add(SettingName.CUSTOMERADDRESS, address);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CustomerBuilder':{@link CustomerBuilder}
     * permette di aggiungere il valore al campo mail dell'oggetto 'Customer':{@link Customer}
     * @param mail valore da attribuire al campo mail dell'oggetto 'Customer':{@link Customer}
     */
    @Override
    public void addMail(String mail) {
        customer.add(SettingName.CUSTOMERMAIL, mail);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CustomerBuilder':{@link CustomerBuilder}
     * permette di aggiungere il valore al campo PEC dell'oggetto 'Customer':{@link Customer}
     * @param pec valore da attribuire al campo PEC dell'oggetto 'Customer':{@link Customer}
     */
    @Override
    public void addPEC(String pec) {
        customer.add(SettingName.CUSTOMERPEC, pec);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CustomerBuilder':{@link CustomerBuilder}
     * permette di aggiungere il valore al campo codeSD dell'oggetto 'Customer':{@link Customer}
     * @param codeSD valore da attribuire al campo codeSD dell'oggetto 'Customer':{@link Customer}
     */
    @Override
    public void addCodeSDI(String codeSD) {
        customer.add(SettingName.COMPANYCODESDI, codeSD);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CustomerBuilder':{@link CustomerBuilder}
     * permette di aggiungere il valore al campo telephone dell'oggetto 'Customer':{@link Customer}
     * @param telephone valore da attribuire al campo telephone dell'oggetto 'Customer':{@link Customer}
     */
    @Override
    public void addTelephone(String telephone) {
        customer.add(SettingName.CUSTOMERTELEPHONE, telephone);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CustomerBuilder':{@link CustomerBuilder}
     * permette di aggiungere il valore al campo shippingAddress dell'oggetto 'Customer':{@link Customer}
     * @param address valore da attribuire al campo shippingAddress dell'oggetto 'Customer':{@link Customer}
     */
    @Override
    public void addShippingAddress(String address) {
        customer.add(SettingName.CUSTOMERSHIPPINGADDRESS, address);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CustomerBuilder':{@link CustomerBuilder}
     * restituisce l'oggetto 'Customer':{@link Customer} costruito mediante i metodi precedenti
     * @return oggetto 'Customer':{@link Customer}
     */
    @Override
    public Customer getResult() {
        return customer;
    }
}