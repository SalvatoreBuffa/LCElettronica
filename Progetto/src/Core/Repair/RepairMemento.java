package Core.Repair;

import Core.Common.Memento;


public class RepairMemento implements Memento<Repair> {

    private Repair state;
    private String operation;

    /**
     * Costruttore che inizializza l'operazione di undo
     * @param state il repair prima dell'operazione
     * @param operation il tipo di operazione (inserimento, cancellazione, modifica)
     */
    public RepairMemento(Repair state, String operation){
        this.state = state;
        this.operation = operation;
    }

    /**
     * @return restituisce il repair prima di aver effettuato l'operazione
     */
    @Override
    public Repair getState() {
        return this.state;
    }
    /**
     * @return Il tipo di operazione applicata al repair (inserimento, cancellazione, modifica)
     */
    public String getOperation(){
        return this.operation;
    }
}
