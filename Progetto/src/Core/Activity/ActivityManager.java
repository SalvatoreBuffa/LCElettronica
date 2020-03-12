package Core.Activity;

import Core.Common.CudManager;
import Core.Common.SettingName;
import Core.Common.UserLogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe utilizzata per la manipolazione delle attività, ossia tutte le operazioni di inserimento, modifica, cancellazione, undo, ricerca
 * Vengono implementate le interfacce 'CudManager':{@link Core.Common.CudManager} e 'Serializable':{@link java.io.Serializable}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class ActivityManager implements CudManager<Activity>, java.io.Serializable {
    private static List<Activity> activity;
    private ActivityMemento prevActivity;

    public ActivityManager(){
        activity = new ArrayList<>();
        prevActivity = new ActivityMemento(null, "");
    }

    /**
     * Metodo ereditato dalla interfaccia 'CudManager':{@link CudManager} permette l'inserimento di una nuova attività
     * @param item attività da inserire
     */
    @Override
    public void create(Activity item) {
        UserLogManager.notify(SettingName.LOGINFO, "Aggiunta attività "+item);
        saveState(item, SettingName.OPERATIONCREATE);
        activity.add(item);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CudManager':{@link CudManager} permette la modifica di una attività
     * @param OldActivityID ID della attività che si vuole modificare
     * @param newActivity nuova attività che si vuole sovrascrivere
     */
    @Override
    public void update(int OldActivityID, Activity newActivity){
        /**
         * All'interno di questo metodo viene effettuata una clonazione profonda, perchè
         * effettuando un semplice riferimento e salvato all'interno del memento, nel momento in cui
         * si effettua una modifica o si cancella l'elemento, viene modificato anche l'elemento all'interno del memento
         * */
        Activity a = searchElement(OldActivityID);
        UserLogManager.notify(SettingName.LOGINFO, "Modificata attività"+a+" con "+newActivity);
        Activity oldActivity = Activity.clone(a);
        saveState(oldActivity, SettingName.OPERATIONUPDATE);
        newActivity.setID(OldActivityID);
        activity.set(activity.indexOf(a), newActivity);
    }

    /**
     * Metodo ereditato dalla interaccia 'CudManager':{@link CudManager} permette la cancellazione di una attività
     * @param ID ID della attività da cancellare
     */
    @Override
    public void delete(int ID) {
        Activity a = searchElement(ID);
        UserLogManager.notify(SettingName.LOGINFO, "Cancellata attività "+a);
        saveState(a, SettingName.OPERATIONDELETE);
        activity.remove(a);
    }

    /**
     * Metodo ereditato dalla interaccia 'CudManager':{@link CudManager} permette la ricerca di una attività mediante ID
     * @param ID ID della attività da cercare
     * @return attività o null se non presente
     */
    @Override
    public Activity searchElement(int ID) {
        return activity.stream().filter(a -> a.getID() == ID).findFirst().orElse(null);
    }
    /**
     * Metodo che restituisce la lista delle attività
     * @return lista delle attività presenti nel manager
     */
    public static List<Activity> getList() {
         return activity;
    }

    /**
     * Metodo che permette l'inserimento di una nuova lista sostituendo la vecchia
     * @param list lista da sostituire a quella attuale
     */
    public void setList(List<Activity> list){
        activity = list;
    }
/*
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Attività: \n");
        for( Activity a : activity){
            s.append(a.toString()).append("\n");
        }
        return s.toString();
    }*/

    @Override
    public String toString() {
        String s = activity.stream().map(a -> a.toString() + "\n").collect(Collectors.joining("", "Attività: \n", ""));
        return s;
    }

    /**
     * Metodo che permette undo della operazione di inserimento, ovvero l'eliminazione della attività
     */
    @Override
    public void restoreCreate(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristino inserimento di "+this.prevActivity.getState());
        activity.removeIf(c -> c.getID() == this.prevActivity.getState().getID());
    }

    /**
     * Metodo che permette undo della operazione di cancellazione, ovvero l'inserimento della attività
     */
    @Override
    public void restoreDelete(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinata rimozione di "+this.prevActivity.getState());
        activity.add(this.prevActivity.getState());
    }

    /**
     * Metodo che permette undo della operazione di modifica
     */
    @Override
    public void restoreUpdate(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinata modifica di "+this.prevActivity.getState());
        activity.set(activity.indexOf(searchElement(this.prevActivity.getState().getID())), this.prevActivity.getState());
    }

    /**
     * Metodo che restituisce l'ultima operazione eseguita
     * @return l'attività originale prima di eseguire l'operazione
     */
    public ActivityMemento getMemento(){
        return this.prevActivity;
    }

    /**
     * Metodo che salva lo stato dell'ultima operazione eseguita
     * @param activity l'attività da salvare prima della modifica
     * @param operation l'operazione eseguita sull'attività (inserimento, cancellazione, modifica)
     */
    public void saveState(Activity activity, String operation){
        this.prevActivity = new ActivityMemento(activity, operation);
    }

    /**
     * Metodo che permette l'esportazione della lista di attività all'interno di un file CSV
     *          *Rispettare la sintassi
     *          *Campo1;Campo2;...;CampoN;
     *          *Valore1;Valore2;...;ValoreN;
     *          *Nel caso in cui uno dei campi non è presente inserire un semplice carattere ';'
     *          *Campo1;Campo2;...;CampoN;
     *          *Valore1;;Valore3;;...;ValoreN;
     * @param fileName percorso del file CSV
     */
    public void exportData(String fileName){
        ActivityCSVBuffer export = new ActivityCSVBuffer();
        export.createCSVFile(this.activity, fileName);
    }

    /**
     * Metodo che permette l'importazione della lista attività a partire da un file CSV
     *      * Rispettare la sintassi
     *      * Campo1;Campo2;...;CampoN;
     *      * Valore1;Valore2;...;ValoreN;
     *      * Nel caso in cui uno dei campi non è presente inserire un semplice carattere ';'
     *      * Campo1;Campo2;...;CampoN;
     *      * Valore1;;Valore3;;...;ValoreN;
     * @param fileName percorso del file CSV
     */
    public void importData(String fileName){
        ActivityCSVBuffer importData = new ActivityCSVBuffer();
        activity = importData.readCSVFile(fileName);
    }
}
