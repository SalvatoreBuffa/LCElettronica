
package Core.Repair;

import Core.Common.CudManager;
import Core.Common.SettingName;
import Core.Common.UserLogManager;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe utilizzata per la manipolazione delle riparazioni, ossia tutte le operazioni di inserimento, modifica, cancellazione, undo, ricerca
 * Vengono implementate le interfacce 'CudManager':{@link Core.Common.CudManager} e 'Serializable':{@link java.io.Serializable}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
public class RepairManager implements CudManager<Repair>, java.io.Serializable {

    private static List<Repair> repairs;
    private RepairMemento prevRepair;

    public RepairManager(){
        repairs = new ArrayList<>();
        prevRepair = new RepairMemento(null, "");
    }

    /**
     * Metodo ereditato dalla interfaccia 'CudManager':{@link CudManager} permette l'inserimento di un nuovo repair
     * @param item repair da inserire
     */
    @Override
    public void create(Repair item) {
        UserLogManager.notify(SettingName.LOGINFO, "Aggiunta riparazione " + item);
        saveState(item, SettingName.OPERATIONCREATE);
        repairs.add(item);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CudManager':{@link CudManager} permette la modifica di un repair
     * @param oldRepairID ID del repair che si vuole modificare
     * @param newRepair nuovo repair che si vuole sovrascrivere
     */
    @Override
    public void update(int oldRepairID, Repair newRepair){
        Repair r = searchElement(oldRepairID);
        UserLogManager.notify(SettingName.LOGINFO, "Modificata riparazione " + r+" con " + newRepair);
        Repair oldRepair = Repair.clone(r);
        saveState(oldRepair, SettingName.OPERATIONUPDATE);
        newRepair.setID(oldRepairID);
        repairs.set(repairs.indexOf(r), newRepair);
    }

    /**
     * Metodo ereditato dalla interaccia 'CudManager':{@link CudManager} permette la cancellazione di un repair
     * @param ID ID del repair da cancellare
     */
    @Override
    public void delete(int ID) {
        Repair r = searchElement(ID);
        UserLogManager.notify(SettingName.LOGINFO, "Cancellata riparazione " + r);
        saveState(r, SettingName.OPERATIONDELETE);
        repairs.remove(r);
    }

    /**
     * Metodo ereditato dalla interaccia 'CudManager':{@link CudManager} permette la ricerca di un repair mediante ID
     * @param ID ID del repair da cercare
     * @return repair o null se non presente
     */
    @Override
    public Repair searchElement(int ID) {
        return repairs.stream().filter(r -> r.getID() == ID).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Riparazioni: \n");
        for( Repair r : repairs){
            s.append(r.toString()).append("\n");
        }
        return s.toString();
    }


    /**
     * Metodo che restituisce la lista dei repair
     * @return lista dei repair presenti nel manager
     */
    public static List<Repair> getList() {
        return repairs;
    }
    /**
     * Metodo che permette l'inserimento di una nuova lista sostituendo la vecchia
     * @param list lista da sostituire a quella attuale
     */
    public void setList(List<Repair> list){
        repairs = list;
    }
    /**
     * Metodo che salva lo stato dell'ultima operazione eseguita
     * @param repair repair da salvare prima della modifica
     * @param operation l'operazione eseguita sul repair (inserimento, cancellazione, modifica)
     */
    public void saveState(Repair repair, String operation){
        this.prevRepair = new RepairMemento(repair, operation);
    }

    /**
     * Metodo che permette undo della operazione di inserimento, ovvero l'eliminazione del repair
     */
    @Override
    public void restoreCreate(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinato inserimento di " + this.prevRepair.getState());
        repairs.removeIf(c -> c.getID() == this.prevRepair.getState().getID());
    }

    /**
     * Metodo che permette undo della operazione di cancellazione, ovvero l'inserimento del repair
     */
    @Override
    public void restoreDelete(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinata rimozione di " + this.prevRepair.getState());
        repairs.add(this.prevRepair.getState());
    }

    /**
     * Metodo che permette undo della operazione di modifica
     */
    @Override
    public void restoreUpdate(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinata modifica di " + this.prevRepair.getState());
        repairs.set(repairs.indexOf(searchElement(this.prevRepair.getState().getID())), this.prevRepair.getState());
    }

    /**
     * Metodo che restituisce l'ultima operazione eseguita
     * @return Repair originale prima di eseguire l'operazione
     */
    public RepairMemento getMemento(){
        return this.prevRepair;
    }

    /**
     * Metodo che permette l'esportazione della lista di repair all'interno di un file CSV
     *          *Rispettare la sintassi
     *          *Campo1;Campo2;...;CampoN;
     *          *Valore1;Valore2;...;ValoreN;
     *          *Nel caso in cui uno dei campi non è presente inserire un semplice carattere ';'
     *          *Campo1;Campo2;...;CampoN;
     *          *Valore1;;Valore3;;...;ValoreN;
     * @param fileName percorso del file CSV
     */
    public void exportData(String fileName){
        RepairCSVBuffer export = new RepairCSVBuffer();
        export.createCSVFile(this.repairs, fileName);
    }

    /**
     * Metodo che permette l'importazione della lista repair a partire da un file CSV
     *      * Rispettare la sintassi
     *      * Campo1;Campo2;...;CampoN;
     *      * Valore1;Valore2;...;ValoreN;
     *      * Nel caso in cui uno dei campi non è presente inserire un semplice carattere ';'
     *      * Campo1;Campo2;...;CampoN;
     *      * Valore1;;Valore3;;...;ValoreN;
     * @param fileName percorso del file CSV
     */
    public void importData(String fileName){
        RepairCSVBuffer importData = new RepairCSVBuffer();
        repairs = importData.readCSVFile(fileName);
    }

}
