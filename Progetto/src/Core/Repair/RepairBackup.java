package Core.Repair;

import Core.Common.Backup;
import Core.Common.SettingName;
import Core.Common.UserLogManager;

import java.io.*;
import java.util.List;
import java.util.logging.Level;


public class RepairBackup implements Backup<Repair> {

    /**
     * Metodo che effettua il recupero da file di una lista di repair
     * @return lista vuota di repair se il file non esiste, altrimenti restituisce la lista di repair recuperata da file
     */
    public static List<Repair> restoreList(){
        /**
         * Rispetto agli altri restoreList, all'interno di questo restoreList si fa uso anche del file repairID,
         * questo perchè è necessario memorizzare di volta in volta IDCounter per generare sempre fatture differenti.
         */
        List<Repair> list = new RepairBackup().restoreList(SettingName.BACKUPREPAIRNAME);
        if(!list.isEmpty()) {
            File f = new File(SettingName.BACKUPDEFAULTPATH + "repairID.dat");
            try {
                /**
                * Se il file esisto imposto IDCounter dell'ultimo elemento pari al valore contenuto all'interno del file repairID.dat
                 * */
                if (f.exists()) {
                    FileInputStream fin = new FileInputStream(SettingName.BACKUPDEFAULTPATH + "repairID.dat");
                    int id = fin.read();
                    list.get(list.size() - 1).setIDCounter(id);
                    list.get(list.size() - 1).setID(id);
                    fin.close();
                } else {
                    list.get(list.size() - 1).setIDCounter((list.size() - 1) + 1);
                    list.get(list.size() - 1).setID((list.size() - 1) + 1);
                }
                return list;
            } catch (IOException e) {
                UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
            }
        }
        return list;
    }
    public static void createBackup(){
        Backup i = new RepairBackup();
        /**
         * Nel caso in cui durante l'esecuzione, vengono rimossi tutti gli elementi dalla lista e si interrrompe l'esecuzione, eventuali file precedenti
         * vengono rimossi, mentre se la lista non è vuota si procede alla creazione del file di backup
         */
        if(RepairManager.getList().isEmpty()){
            i.deleteFile(SettingName.BACKUPDEFAULTPATH+SettingName.BACKUPREPAIRNAME+".dat");
        }else {
            i.createBackup("Core.Repair.RepairManager", "getList", SettingName.BACKUPREPAIRNAME);
            try {
                FileOutputStream fout = new FileOutputStream(SettingName.BACKUPDEFAULTPATH+"repairID.dat");
                int id = RepairManager.getList().get(RepairManager.getList().size()-1).getIDCounter();
                fout.write(id);
                fout.close();
            }catch (IOException e) {
                UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
            }
        }
    }
}
