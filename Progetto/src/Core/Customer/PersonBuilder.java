package Core.Customer;

import Core.Common.SettingName;
/**
 * Classe utilizzata per creare un oggetto'Customer':{@link Core.Customer.Customer} di tipo Persona
 * Implementa la interfaccia 'CustomerBuilder':{@link Core.Customer.CustomerBuilder}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class PersonBuilder implements CustomerBuilder {

    private Customer customer;
    public PersonBuilder() {
        customer = new Customer(SettingName.PERSONTYPE);
    }
    @Override
    public void addName(String name) {
        customer.add(SettingName.PERSONNAME, name);
    }
    @Override
    public void addSurname(String surname) {
        customer.add(SettingName.PERSONSURNAME, surname);
    }

    @Override
    public void addPersonalID(String personalID) {
        customer.add(SettingName.PERSONPERSONALID, personalID);
    }

    @Override
    public void addPIVA(String PIVA) {
        customer.add(SettingName.CUSTOMERPIVA, PIVA);
    }

    @Override
    public void addAddress(String address) {
        customer.add(SettingName.CUSTOMERADDRESS, address);
    }

    @Override
    public void addMail(String mail) {
        customer.add(SettingName.CUSTOMERMAIL, mail);
    }
    @Override
    public void addPEC(String pec) {
        customer.add(SettingName.CUSTOMERPEC, pec);
    }
    @Override
    public void addCodeSDI(String codeSD) {
        customer.add(SettingName.PERSONCODESDI, codeSD);
    }

    @Override
    public void addTelephone(String telephone) {
        customer.add(SettingName.CUSTOMERTELEPHONE, telephone);
    }
    @Override
    public void addShippingAddress(String address) {
        customer.add(SettingName.CUSTOMERSHIPPINGADDRESS, address);
    }
    @Override
    public Customer getResult() {
        return customer;
    }


}