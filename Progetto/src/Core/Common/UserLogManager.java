package Core.Common;

/**
 * Concrete Subject del logger, ogni volta che viene notificato comunica al logger che deve scrivere un messaggio
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class
UserLogManager {
    static LogObserver logger;

    public static void initLogger(){
        if(logger == null)
            logger = new UserLogger();
    }

    public static void notify(String level, String message) {
        logger.update(level, message);
    }

    public  static void close(){
        logger.close();
    }
}
