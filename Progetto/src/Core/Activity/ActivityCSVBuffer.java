package Core.Activity;

import Core.Common.CSVReader;
import Core.Common.CSVWriter;
import Core.Common.SettingName;
import Core.Common.UserLogManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe utilizzata per la manipolazione dei fileCSV, ossia lettura e scrittura
 * Vengono implementate le interfacce 'CSVWriter': {@link Core.Common.CSVWriter} e 'CSVReader':{@link Core.Common.CSVReader}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */


public class ActivityCSVBuffer implements CSVWriter<Activity>, CSVReader<Activity> {

    private String fileName;
    private BufferedWriter fileWriter;
    private BufferedReader fileReader;

    /**
     * Metodo ereditato dalla interfaccia 'CSVReader':{@link CSVReader} permette l'apertura del file il lettura utilizzando la classe 'BufferedReader': {@link BufferedReader}
     * @param fileName nome del file
     * @throws IOException nel caso in cui il file non esiste
     */
    @Override
    public void openReader(String fileName) throws IOException {
        this.fileName = fileName;
        this.fileReader = new BufferedReader(new FileReader(this.fileName));
    }


    /**
     * Metodo ereditato dalla interfaccia 'CSVReader':{@link CSVReader} permette la lettura del file CSV
     * @return lista di 'attività':{@link Activity} contenuta all'interno del file CSV
     * @throws IOException nel caso in cui il file non esiste
     */
    @Override
    public List<Activity> readBody() throws IOException {
        List<Activity> data = new ArrayList<>();
        /**
         * Lettura header file CSV, quindi: Campo1;Campo2;Campo3; ...
         */
        String header = this.fileReader.readLine();
        String[] attributes = header.split(";");
        String currentLine = "";
        /**
         * Per ogni linea del file CSV viene aggiunta una nuova attività
         */
        while((currentLine = this.fileReader.readLine()) != null){
            String[] items = currentLine.split(";");
            Activity p = new Activity();
            int count;
            /**
             * All'interno di questo for vengono aggiunti tutti quei campi che posseggono valori
             */
            for(count = 0; count < items.length; count++){
                p.add(attributes[count], items[count]);
            }
            /**
             * Mentre i restanti campi che non posseggono valore vengono inizializzati a stringa vuota attraverso il while, ovvero,
             * un tipo esempio di file potrebbe essere:
             * Campo1;Campo2;Campo3
             * valore1;valore2;valore3;
             * valore1;;;
             * In quest'ultimo avremo campo1 = valore1;
             *                        campo2 = "";
             *                        campo3 = "";
             */
            while(count < attributes.length){
                p.add(attributes[count], "");
                count++;
            }
            data.add(p);
        }
        return data;
    }

    /**
     * Metodo ereditato dalla interfaccia 'CSVReader':{@link CSVReader} permette la chiusura del file CSV
     * @throws IOException nel caso in cui il file non esiste
     */
    @Override
    public void closeReader() throws IOException {
        this.fileReader.close();
    }

    /**
     * Metodo ereditato dalla interfaccia 'CSVWriter':{@link CSVWriter} permette la apertura del file CSV in scrittura
     * @param fileName percorso del file
     * @throws IOException eccezione nel caso in cui non esiste il file
     */
    @Override
    public void openWriter(String fileName) throws IOException {
        this.fileName = fileName;
        this.fileWriter = new BufferedWriter(new FileWriter(this.fileName, false));
    }

    /**
     * Metodo ereditato dalla interfaccia 'CSVWriter':{@link CSVWriter} permette la scrittura all'interno del file CSV del header, formato dai vari campi
     * @throws IOException eccezione nel caso in cui non esiste il file
     */
    @Override
    public void createHeader() throws IOException {
        String header= SettingName.ACTIVITYDESCRIPTION+";"+SettingName.ACTIVITYTYPE +";"+SettingName.ACTIVITYPRICE+";"
                +SettingName.ACTIVITYREVENUE+";"+SettingName.ACTIVITYMODE +";"+SettingName.ACTIVITYDATE+"\n";
        this.fileWriter.write(header);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CSVWriter':{@link CSVWriter} permette la scrittura all'interno del file CSV della lista di attività
     * @param data lista di attività da scrivere all'interno del file
     * @throws IOException nel caso in cui il file non esiste
     */
    @Override
    public void createBody(List<Activity> data) throws IOException {
        StringBuilder field = new StringBuilder();
        /**
         * Replace fondamentale nel caso in cui all'interno di un campo è presente un carattere ';', in quel caso viene sostituito da uno spazio
         */
        data.forEach(p -> {
            p.replace(";", " ");
            field.append(p.export() + "\n");
        });
        this.fileWriter.write(field.toString());
    }

    /**
     * Metodo ereditato dalla interfaccia 'CSVWriter':{@link CSVWriter} che permette la chiusura del file CSV
     * @throws IOException nel caso in cui il file non esiste
     */
    @Override
    public void closeWriter() throws IOException {
        this.fileWriter.close();
    }

    /**
     * Metodo che permette la scritta del file CSV
     * @param products lista contenente le attività da inserire all'interno del file
     * @param fileName nome del percorso dove salvare il file CSV
     */
    public void createCSVFile(List<Activity> products, String fileName){
        try {
            openWriter(fileName);
            createHeader();
            createBody(products);
            closeWriter();
        } catch (IOException e) {
            UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
        }
    }

    /**
     * Metodo che permette la lettura del file CSV
     * @param fileName nome del percorso contenente il file CSV
     * @return lista di attività generata a partire dal file CSV, null se il file non esiste
     */
    public List<Activity> readCSVFile(String fileName){
        List<Activity> data = null;
        try {
            openReader(fileName);
            data = readBody();
            closeReader();
            return data;
        } catch (IOException e) {
            UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
        }
        return data;
    }

}
