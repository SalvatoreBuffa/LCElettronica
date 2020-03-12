package Core.Common;

/**
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public interface CudManager<T> {
     void create(T item);
     void update(int ID, T item) throws CloneNotSupportedException;
     void delete(int ID);
     T searchElement(int ID);
     void restoreCreate();
     void restoreUpdate();
     void restoreDelete();
     //void notify(String message);
}
