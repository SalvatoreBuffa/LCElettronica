package Core.Common;

/**
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public interface Item {
    void add(String key, String value);
    void replace(String key, String value);
    String get(String key);
    int getID();
    void setID(int id);
    void setIDCounter(int id);
    int getIDCounter();

}
