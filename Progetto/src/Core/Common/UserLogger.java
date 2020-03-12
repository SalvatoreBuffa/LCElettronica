package Core.Common;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Concrete Observer che alla notifica aggiorna il file di log
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */



public class UserLogger implements LogObserver {
    //percorso in cui generare il file di log e buffer per la scrittura
    private String filePath;
    private BufferedWriter buffer;

    /**
     * Costruttore
     */
    public UserLogger() {
        filePath = SettingName.LOGGERPATH;
        File txt = new File(filePath+File.separator+SettingName.LOGNAME);
        if(!txt.exists()){
            try {
                txt.getParentFile().mkdirs();
                if(txt.createNewFile()){
                    System.out.println("File creato in "+txt.getAbsolutePath());
                }
            }catch (IOException ex){
                System.out.println("File non creato in "+filePath);
            }
        }
        try {
            buffer = new BufferedWriter(new FileWriter(filePath + File.separator + SettingName.LOGNAME, true));
        } catch (IOException e) {
            System.out.println("Percorso del file di log non valido");
        }
    }

    /**
     * Metodo che scrive nel file di log
     * @param level Intestazione del messaggio
     * @param message Messaggio da scrivere
     */
    @Override
    public void update(String level, String message) {
        try {
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime timestamp = LocalDateTime.now();
            buffer.append(pattern.format(timestamp)+" "+level+": "+message+"\n");
            buffer.flush();
        }catch (IOException e){
            System.out.println("Errore nella scrittura nel file");
        }
    }

    /**
     * Metodo che chiude il buffer di scrittura
     */
    public void close(){
        try {
            buffer.close();
        }catch (IOException e){
            System.out.println("Errore nella chiusura del file");
        }
    }
}
