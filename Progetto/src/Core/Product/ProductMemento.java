package Core.Product;

import Core.Common.Memento;

public class ProductMemento implements Memento<Product> {

    private Product state;
    private String operation;

    /**
     * Costruttore che inizializza l'operazione di undo
     * @param state il product prima dell'operazione
     * @param operation il tipo di operazione (inserimento, cancellazione, modifica)
     */
    public ProductMemento(Product state, String operation){
        this.state = state;
        this.operation = operation;
    }

    /**
     * @return restituisce il product prima di aver effettuato l'operazione
     */
    @Override
    public Product getState() {
        return this.state;
    }
    /**
     * @return Il tipo di operazione applicata al product (inserimento, cancellazione, modifica)
     */
    public String getOperation(){
        return this.operation;
    }


}
