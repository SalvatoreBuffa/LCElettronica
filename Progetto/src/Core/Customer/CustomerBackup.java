/**
 * Classe utilizzata per effettuare le operazioni di backup della lista di customer, ossia le operazioni di salvataggio e recupero dati
 * Tale classe implementa la interfaccia 'Backup':{@link Core.Common.Backup}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
package Core.Customer;

import Core.Common.Backup;
import Core.Common.SettingName;

import java.util.List;
/**
 * Classe utilizzata per effettuare le operazioni di backup della lista di customer, ossia le operazioni di salvataggio e recupero dati
 * Tale classe implementa la interfaccia 'Backup':{@link Core.Common.Backup}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class CustomerBackup implements Backup<Customer> {

    /**
     * Metodo che effettua il recupero da file di una lista di customer
     * @return lista vuota di customer se il file non esiste, altrimenti restituisce la lista di customer recuperata da file
     */
    public static List<Customer> restoreList() {
        List<Customer> list = new CustomerBackup().restoreList(SettingName.BACKUPCUSTOMERNAME);
        /**
         * Se la lista non è vuota, è necessario impostare IDCounter (quindi ID del prossimo elemento creato) pari all'ultimo ID presente
         * nella lista incrementato di uno
         */
        if(!list.isEmpty()) {
            list.get(list.size()-1).setIDCounter(list.get(list.size()-1).getID()+1);
        }
        return list;
    }

    /**
     * Metodo che permette la creazione di un file di backup nel caso in cui viene arrestata l'esecuzione del programma senza la corretta
     * esportazione delle informazioni
     */
    public static void createBackup(){
        Backup i = new CustomerBackup();
        /**
         * Nel caso in cui durante l'esecuzione, vengono rimossi tutti gli elementi dalla lista e si interrrompe l'esecuzione, eventuali file precedenti
         * vengono rimossi, mentre se la lista non è vuota si procede alla creazione del file di backup
         */
        if(CustomerManager.getList().isEmpty()){
            i.deleteFile(SettingName.BACKUPDEFAULTPATH+SettingName.BACKUPCUSTOMERNAME+".dat");
        }else{
        i.createBackup("Core.Customer.CustomerManager", "getList", SettingName.BACKUPCUSTOMERNAME);
        }
    }
}