package Core.Common;



import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
/**
 * Interfaccia creata per definire i metodi creazione e ripristino dei Backup
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */


public interface Backup<T> {

    /**
     * Metodo creato per il ripristino della lista mediante file
     * @param fileName percorso del file di backup
     * @return lista contenenti gli elementi all'interno del file
     */
    default List<T> restoreList(String fileName){
        List<T> obj = new ArrayList<>();
        try{
            FileInputStream fileIn = new FileInputStream(SettingName.BACKUPDEFAULTPATH + fileName + ".dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = (List<T>) in.readObject();
            in.close();
            fileIn.close();
            obj = restoreListID(obj);
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
            return obj;
        }
    }

    /**
     * Metodo utilizzato per ripristinare ID durante il caricamento della lista dal file di backup
     * @param listItem lista da modificare
     * @return lista modificata
     */
    default List<T> restoreListID(List<T> listItem){
        /**
         * Questo metodo è stato necessario, perchè creando il backup attraverso la serealizzazione
         * molto spesso venivano confusi gli ID durante la deserealizzazione per questo motivo questo
         * metodo non fa altro per ogni attività assegnare un ID incrementale a partire da 1
         */
        ArrayList<T> newList = new ArrayList<>();
        try {
            int countID = 0;
            for(T item: listItem){
                countID += 1;
                /**
                 * Il metodo getClass() permette a runtime di ricavare la classe dell'oggetto item, mentre
                 * il metodo getMethod() prende a parametro il nome del metodo da istanziare e i parametri di tale metodo
                 * in questo caso il metodo setID prende a parametro un int. Utilizzando i reflect possiamo risparmiare circa
                 * 20/30 righe di codice nel dover effettuare un if per ogni tipo (Activity, Repair, Product, Customer), inoltre
                 * questo strumento ci permette anche eventuali modifiche future, perchè nel caso volessimo aggiungere ulteriori classi
                 * quali ProductSpecial non sarà necessario modificare questa classe perchè runtime riconosce la classe e richiama
                 * il metodo setID
                 */
                Method setID = item.getClass().getMethod("setID", int.class);
                /**
                 * il metodo invoke della Classe Method permette di richiamare il metodo passando a parametro l'oggetto in questione ed
                 * un intero, setID.invoke(item, countID); corrisponde a fare item.setID(countID) con item di tipo product, customer, etc..
                 */
                setID.invoke(item, countID);
                newList.add(item);
            }
            return newList;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
        }
        return newList;
    }


    /**
     * Metodo che permette la creazione del file di backup a partire dalla lista
     * @param managerName La classe manager che contiene tutte le operazioni per manipolare la lista
     * @param method il metodo che si vuole utilizzare per passare a parametro la lista
     * @param fileName il percorso dove salvare il file di backup
     */
    default void createBackup(String managerName, String method, String fileName){
        try {
            /**
             * Rispetto al metodo precedente che consisteva nel richiamare solo un metodo dell'oggetto,
             * in questo caso abbiamo la necessita per prima cosa di ricavare la classe manager contenente
             * il metodo che restituisce la lista<T> e successivamente così come fatto nel metodo precedente
             * si istanzia un oggetto della classe Method che contiene il metodo che vogliamo richiamare.
             * Le seguenti linee di codice corrispondono a fare:
             * List<Customer> = CustomerManager.getList();
             * Dove classManager contiene CustomerManager,
             * getMethod contiene getList
             * Ed infine scriviamo l'oggetto all'interno del file
             */
            Class classManager = Class.forName(managerName);
            UserLogManager.notify(SettingName.LOGINFO, "Creazione backup di "+fileName);
            Method getMethod = classManager.getDeclaredMethod(method);
            List<Item> item = (List<Item>) getMethod.invoke(null); //null perché static
            FileOutputStream fileOut = new FileOutputStream(SettingName.BACKUPDEFAULTPATH + fileName + ".dat");
            System.out.println(SettingName.BACKUPDEFAULTPATH + fileName + ".dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(item);
            out.close();
            fileOut.close();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
        }
    }

    /**
     * Metodo che permette la cancellazione di un file
     * @param path percorso del file da eliminare
     * @return true se il file è stato cancellato, false se il file non è stato cancellato
     */
    default boolean deleteFile(String path){
        File f = new File(path);
        if(f.exists()) return f.delete();
        return false;
    }
}