package Core.Common;

/**
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */


public interface LogObserver {
    void update(String level, String message);
    void close();
}
