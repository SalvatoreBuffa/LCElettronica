package Core.Customer;

import Core.Common.*;
/**
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class CustomerMemento implements Memento<Customer>{

    private Customer state;
    private String operation;

    /**
     * Costruttore che inizializza l'operazione di undo
     * @param c il customer prima dell'operazione
     * @param operation il tipo di operazione (inserimento, cancellazione, modifica)
     */
    public CustomerMemento(Customer c, String operation){
        this.state = c;
        this.operation = operation;
    }

    /**
     * @return restituisce il customer prima di aver effettuato l'operazione
     */
    @Override
    public Customer getState() {
        return this.state;
    }
    /**
     * @return Il tipo di operazione applicata al customer (inserimento, cancellazione, modifica)
     */
    public String getOperation(){
        return this.operation;
    }
}
