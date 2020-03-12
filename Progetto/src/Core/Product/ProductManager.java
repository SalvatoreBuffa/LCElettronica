
package Core.Product;

import Core.Common.CudManager;
import Core.Common.SettingName;
import Core.Common.UserLogManager;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe utilizzata per la manipolazione dei product, ossia tutte le operazioni di inserimento, modifica, cancellazione, undo, ricerca
 * Vengono implementate le interfacce 'CudManager':{@link Core.Common.CudManager} e 'Serializable':{@link java.io.Serializable}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class ProductManager implements CudManager<Product>, java.io.Serializable {

    private static List<Product> products;
    private ProductMemento prevProduct;

    public ProductManager(){
        products = new ArrayList<>();
        prevProduct = new ProductMemento(null, "");
    }

    /**
     * Metodo ereditato dalla interfaccia 'CudManager':{@link CudManager} permette l'inserimento di un nuovo product
     * @param item product da inserire
     */
    @Override
    public void create(Product item) {
        UserLogManager.notify(SettingName.LOGINFO, "Aggiunto prodotto " + item);
        saveState(item, SettingName.OPERATIONCREATE);
        products.add(item);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CudManager':{@link CudManager} permette la modifica di un product
     * @param oldProductID ID del customer che si vuole modificare
     * @param newProduct nuovo customer che si vuole sovrascrivere
     */
    @Override
    public void update(int oldProductID, Product newProduct){
        Product p = searchElement(oldProductID);
        UserLogManager.notify(SettingName.LOGINFO, "Modificato prodotto " + p +" con "+ newProduct);
        Product oldProduct = Product.clone(p);
        saveState(oldProduct, SettingName.OPERATIONUPDATE);
        newProduct.setID(oldProductID);
        products.set(products.indexOf(p), newProduct);
    }

    /**
     * Metodo ereditato dalla interaccia 'CudManager':{@link CudManager} permette la cancellazione di un product
     * @param ID ID del product da cancellare
     */
    @Override
    public void delete(int ID) {
        Product p = searchElement(ID);
        UserLogManager.notify(SettingName.LOGINFO, "Cancellato prodotto " + p);
        saveState(p, SettingName.OPERATIONDELETE);
        products.remove(p);
    }

    /**
     * Metodo ereditato dalla interaccia 'CudManager':{@link CudManager} permette la ricerca di un product mediante ID
     * @param ID ID del product da cercare
     * @return product o null se non presente
     */
    @Override
    public Product searchElement(int ID) {
        return products.stream().filter(p -> p.getID() == ID).findFirst().orElse(null);
    }


    /**
     * Metodo che restituisce la lista dei product
     * @return lista dei product presenti nel manager
     */
    public static List<Product> getList() {
        return products;
    }
    /**
     * Metodo che permette l'inserimento di una nuova lista sostituendo la vecchia
     * @param list lista da sostituire a quella attuale
     */
    public void setList(List<Product> list){
        products = list;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Prodotti: \n");
        for( Product p : products){
            s.append(p.toString()).append("\n");
        }
        return s.toString();
    }

    /**
     * Metodo che salva lo stato dell'ultima operazione eseguita
     * @param product product da salvare prima della modifica
     * @param operation l'operazione eseguita sul product (inserimento, cancellazione, modifica)
     */
    public void saveState(Product product, String operation){
        this.prevProduct = new ProductMemento(product, operation);
    }


    /**
     * Metodo che restituisce l'ultima operazione eseguita
     * @return l'attività originale prima di eseguire l'operazione
     */
    public ProductMemento getMemento(){
        return this.prevProduct;
    }

    /**
     * Metodo che permette undo della operazione di inserimento, ovvero l'eliminazione del product
     */
    @Override
    public void restoreCreate(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinato inserimento di " + this.prevProduct.getState());
        products.removeIf(c -> c.getID() == this.prevProduct.getState().getID());
    }

    /**
     * Metodo che permette undo della operazione di cancellazione, ovvero l'inserimento del product
     */
    @Override
    public void restoreDelete(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinata rimozione di " + this.prevProduct.getState());
        products.add(this.prevProduct.getState());
    }

    /**
     * Metodo che permette undo della operazione di modifica
     */
    @Override
    public void restoreUpdate(){
        UserLogManager.notify(SettingName.LOGINFO, "Ripristinata modifica di " + this.prevProduct.getState());
        products.set(products.indexOf(searchElement(this.prevProduct.getState().getID())), this.prevProduct.getState());
    }

    /**
     * Metodo che permette l'esportazione della lista di product all'interno di un file CSV
     *          *Rispettare la sintassi
     *          *Campo1;Campo2;...;CampoN;
     *          *Valore1;Valore2;...;ValoreN;
     *          *Nel caso in cui uno dei campi non è presente inserire un semplice carattere ';'
     *          *Campo1;Campo2;...;CampoN;
     *          *Valore1;;Valore3;;...;ValoreN;
     * @param fileName percorso del file CSV
     */
    public void exportData(String fileName){
        ProductCSVBuffer export = new ProductCSVBuffer();
        export.createCSVFile(this.products, fileName);
    }

    /**
     * Metodo che permette l'importazione della lista product a partire da un file CSV
     *      * Rispettare la sintassi
     *      * Campo1;Campo2;...;CampoN;
     *      * Valore1;Valore2;...;ValoreN;
     *      * Nel caso in cui uno dei campi non è presente inserire un semplice carattere ';'
     *      * Campo1;Campo2;...;CampoN;
     *      * Valore1;;Valore3;;...;ValoreN;
     * @param fileName percorso del file CSV
     */
    public void importData(String fileName){
        ProductCSVBuffer importData = new ProductCSVBuffer();
        products = importData.readCSVFile(fileName);
    }
}
