package Core.Activity;

import Core.Common.Memento;
/**
 * Classe utilizzata per aggiungere la funzionalità di Undo
 * Implementa la interfaccia 'Memento'{@link Core.Common.Memento}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
public class ActivityMemento implements Memento<Activity> {

    private Activity state;
    private String operation;

    /**
     * Costruttore che inizializza l'operazione di undo
     * @param state l'attività prima dell'operazione
     * @param operation il tipo di operazione (inserimento, cancellazione, modifica)
     */
    public ActivityMemento(Activity state, String operation){
        this.state = state;
        this.operation = operation;
    }

    /**
     * @return l'attività prima di aver effettuato l'operazione
     */
    @Override
    public Activity getState() {
        return this.state;
    }

    /**
     * @return Il tipo di operazione applicata alla attività (inserimento, cancellazione, modifica)
     */
    public String getOperation(){
        return this.operation;
    }
}
