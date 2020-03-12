package GUI.Repair;



import Core.Customer.Customer;
/**
 * Classe che modellizza una entry della combobox di ricerca del cliente
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */
public class ComboItem {
    //una entrata della combobox ha un testo di riferimento e un cliente associato
    private String text;
    private Customer customer;

    /**
     * Costruttore di default che crea una entry vuota
     */
    public ComboItem(){
        this.text = "Nessuno";
        this.customer = null;
    }

    /**
     * Costruttore
     * @param s la stringa che sarà visualizzata nella entry
     * @param c il cliente associato alla stringa s
     */
    public ComboItem(String s, Customer c){
        this.text = s;
        this.customer = c;
    }

    /**
     * Getter del testo della entry
     * @return la stringa che identifica la entry
     */
    public String getText() {
        return text;
    }

    /**
     * Setter del testo
     * @param text Stringa da impostare
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter del cliente
     * @return il cliente associato alla entry
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Setter del testo
     * @param customer Cliente da associare alla barra
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Metodo che restituisce una stringa identificativa della barra
     * @return stringa che identifica la barra
     */
    @Override
    public String toString(){
        return this.text;
    }
}
