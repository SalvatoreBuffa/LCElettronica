package Core.Customer;


import Core.Common.*;

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

public class CustomerCSVBuffer implements CSVWriter<Customer>,CSVReader<Customer>{
    private String type;
    private String fileName;
    private BufferedWriter fileWriter;
    private BufferedReader fileReader;

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
     * Metodo ereditato dalla interfaccia 'CSVWriter':{@link CSVWriter} permette la scrittura all'interno del file CSV del header, formato dai vari campi
     * @throws IOException eccezione nel caso in cui non esiste il file
     */
    @Override
    public void createHeader(String type) throws IOException {
        String header;
        if(type.equals(SettingName.PERSONTYPE)){
            header = SettingName.PERSONNAME+";"+SettingName.PERSONSURNAME+";"+SettingName.PERSONPERSONALID+";"+SettingName.CUSTOMERPIVA+";"
                    +SettingName.PERSONCODESDI +";"+SettingName.CUSTOMERMAIL+";"+SettingName.CUSTOMERPEC+";"+SettingName.CUSTOMERTELEPHONE+";"
                    +SettingName.CUSTOMERADDRESS+";"+SettingName.CUSTOMERSHIPPINGADDRESS+";"+"\n";
        }else{
            header = SettingName.COMPANYNAME+";"+SettingName.CUSTOMERPIVA+";" +SettingName.COMPANYCODESDI +";"+SettingName.CUSTOMERMAIL
                    +";"+SettingName.CUSTOMERPEC+";"+SettingName.CUSTOMERTELEPHONE+";" +SettingName.CUSTOMERADDRESS+";"
                    +SettingName.CUSTOMERSHIPPINGADDRESS+";"+"\n";
        }
        this.fileWriter.write(header);
    }

    /**
     * Metodo ereditato dalla interfaccia 'CSVWriter':{@link CSVWriter} permette la scrittura all'interno del file CSV della lista di 'customer':{@link Customer}
     * @param data lista di 'customer':{@link Customer} da scrivere all'interno del file
     * @throws IOException nel caso in cui il file non esiste
     */
    @Override
    public void createBody(List<Customer> data) throws IOException{
        StringBuilder field = new StringBuilder();
        for(Customer c: data){
            /**
             * Replace fondamentale nel caso in cui all'interno di un campo è presente un carattere ';', in quel caso viene sostituito da uno spazio
             */
            c.replace(";", " ");
            if(c.getType().equals(this.type)){
                field.append(c.export()+"\n");
            }
        }
        this.fileWriter.write(field.toString());
    }

    /**
     * Metodo ereditato dalla interfaccia 'CSVReader':{@link CSVReader} permette la lettura del file CSV
     * @return lista di 'Customer':{@link Customer} contenuta all'interno del file CSV
     * @throws IOException nel caso in cui il file non esiste
     */
    @Override
    public List<Customer> readBody() throws IOException {
        List<Customer> data = new ArrayList<>();
        /**
         * Lettura header file CSV, quindi: Campo1;Campo2;Campo3; ...
         */
        String header = this.fileReader.readLine();
        String[] attributes = header.split(";");
        String currentLine = "";
        /**
         * Per ogni linea del file CSV viene aggiunta una nuova attività
         */
        while((currentLine = this.fileReader.readLine()) != null) {
            String[] items = currentLine.split(";");
            Customer c = new Customer(this.type);
            int count;
            /**
             * All'interno di questo for vengono aggiunti tutti quei campi che posseggono valori
             */
            for(count = 0; count < items.length; count++){
                c.add(attributes[count], items[count]);
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
                c.add(attributes[count], "");
                count++;
            }
            data.add(c);
        }
        return data;
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
     * Metodo ereditato dalla interfaccia 'CSVReader':{@link CSVReader} permette la chiusura del file CSV
     * @throws IOException nel caso in cui il file non esiste
     */
    @Override
    public void closeReader() throws IOException {
        this.fileReader.close();
    }


    /**
     * Metodo che permette la scritta del file CSV
     * @param customers lista contenente i customer da inserire all'interno del file
     * @param fileName nome del percorso dove salvare il file CSV
     * @param type tipo di customer (Cliente, Azienda)
     */
    public void createCSVFile(List<Customer> customers, String fileName, String type){
        this.type = type;
        try {
            openWriter(fileName);
            createHeader(type);
            createBody(customers);
            closeWriter();
        } catch (IOException e) {
            UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
        }
    }

    /**
     * Metodo che permette la lettura del file CSV
     * @param fileName nome del percorso contenente il file CSV
     * @param type tipo di customer (Cliente, Azienda)
     * @return lista di 'customer':{@link Customer} generata a partire dal file CSV, null se il file non esiste
     */
    public List<Customer> readCSVFile(String fileName, String type){
        this.type = type;
        List<Customer> data = null;
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
