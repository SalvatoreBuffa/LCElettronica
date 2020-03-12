package Core.Activity;

import Core.Common.Backup;
import Core.Common.SettingName;
import Core.Common.UserLogManager;

import java.util.List;
import java.util.logging.Level;
/**
 * Classe utilizzata per effettuare le operazioni di backup della lista di attività, ossia le operazioni di salvataggio e recupero dati
 * Tale classe implementa la interfaccia 'Backup':{@link Core.Common.Backup}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class ActivityBackup implements Backup<Activity> {

    /**
     * Metodo che effettua il recupero da file di una lista di attività
     * @return lista vuota di attività se il file non esiste, altrimenti restituisce la lista di attività recuperata da file
     */
    public static List<Activity> restoreList(){
        List<Activity> list = new ActivityBackup().restoreList(SettingName.BACKUPACTIVITYNAME);
        /**
         * Se la lista non è vuota, è necessario impostare IDCounter (quindi ID del prossimo elemento creato) pari all'ultimo ID presente
         * nella lista incrementato di uno
         */
        if(!list.isEmpty()) list.get(list.size()-1).setIDCounter(list.get(list.size()-1).getID()+1);
        return list;
    }

    /**
     * Metodo che permette la creazione di un file di backup nel caso in cui viene arrestata l'esecuzione del programma senza la corretta
     * esportazione delle informazioni
     */
    public static void createBackup(){
        Backup i = new ActivityBackup();
        /**
         * Nel caso in cui durante l'esecuzione, vengono rimossi tutti gli elementi dalla lista e si interrrompe l'esecuzione, eventuali file precedenti
         * vengono rimossi, mentre se la lista non è vuota si procede alla creazione del file di backup
         */
        if(ActivityManager.getList().isEmpty()){
        i.deleteFile(SettingName.BACKUPDEFAULTPATH+SettingName.BACKUPACTIVITYNAME+".dat");
        }else{
            i.createBackup("Core.Activity.ActivityManager", "getList", SettingName.BACKUPACTIVITYNAME);
        }
    }
}