package Core.Customer;
/**
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public interface CustomerBuilder {

    void addName(String name);
    default void addSurname(String surname){
        return;
    }
    default void addPersonalID(String personalID){
        return;
    }
    void addPIVA(String PIVA);
    void addAddress(String address);
    void addMail(String mail);
    void addPEC(String pec);
    void addCodeSDI(String codeSD);
    void addTelephone(String telephone);
    void addShippingAddress(String shipping);
    Customer getResult();
}