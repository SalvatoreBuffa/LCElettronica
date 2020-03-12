
package Core.Repair;

import Core.Common.SettingName;
import Core.Common.UserLogManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilizzata per generare il PDF della fattura per una determinata riparazione a partire da un template fornito dal cliente
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class PDFGenerate{

    private String fileName;

    public PDFGenerate(String filename, String pathPDFTemplate, Repair repair){
            this.fileName = filename;
            File pdf = new File(pathPDFTemplate);
            try {
                PDDocument doc = PDDocument.load(pdf);
                PDPage page = doc.getPage(0);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, false);
                contentStream.beginText();
                contentStream.newLineAtOffset(399, 762);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.setLeading(20);
                LocalDate localDate = LocalDate.now();
                contentStream.showText(DateTimeFormatter.ofPattern("dd/MM/yyy").format(localDate));
                contentStream.endText();
                contentStream.close();
                doc.save(new File(this.fileName));
                doc.close();
            } catch (IOException e) {
                UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
            }
            //codice riparazione
            createDocument(468.5f, 690, repair.get(SettingName.REPAIRCODE));
            //Numero
            createDocument(90, 657, repair.get(SettingName.REPAIRNUMBER));
            //data ritiro
            createDocument(230, 657, repair.get(SettingName.REPAIRPICKUPDATE));
            //riparazione di
            //createDocument(385, 658, "BHO");
            if(repair.getCustomer() != null){
                //nominativo
                String n = repair.getCustomer().getType().equals("person") ? repair.getCustomer().get(SettingName.PERSONNAME)+" "+repair.getCustomer().get(SettingName.PERSONSURNAME) : repair.getCustomer().get(SettingName.COMPANYNAME);
                createDocument(117, 626, n);
                //codice fiscale
                String cf = repair.getCustomer().getType().equals("person") ? repair.getCustomer().get(SettingName.PERSONPERSONALID) : "-";
                createDocument(117, 612, cf);
                //PIVA
                createDocument(101, 598, repair.getCustomer().get(SettingName.CUSTOMERPIVA));
                //mail
                createDocument(80, 583, repair.getCustomer().get(SettingName.CUSTOMERMAIL));
                //PEC
                createDocument(72, 569, repair.getCustomer().get(SettingName.CUSTOMERPEC));
                //Telefono
                createDocument(91, 554, repair.getCustomer().get(SettingName.CUSTOMERTELEPHONE));
                //Indirizzo
                createDocument(91, 540, repair.getCustomer().get(SettingName.CUSTOMERADDRESS));
                //Indirizzo spedizione
                createDocument(160, 525, repair.getCustomer().get(SettingName.CUSTOMERSHIPPINGADDRESS));
            }
            //PREVENTIVO
            String p = Boolean.parseBoolean(repair.get(SettingName.REPAIRPREVENTIVE)) ? "X" : "";
            createDocument(552,285, p);
            //NUMERO DI SERIE
            createDocument(385, 496, repair.get(SettingName.REPAIRSERIALNUMBER));
            //MODELLO
            createDocument(216,496, repair.get(SettingName.REPAIRMODEL));
            //MARCA
            createDocument(76,496, repair.get(SettingName.REPAIRBRAND));
            //DATA ACQUISTO
            createDocument(140, 463.5f, repair.get(SettingName.REPAIRPURCHUASEDATE));
            //GUASTO
            formatText(repair.get(SettingName.REPAIRBRAKE), 85, 452);
            //NOTE
            formatText(repair.get(SettingName.REPAIRNOTE), 80, 385.5f);
            //PREZZO
            createDocument(435,285, repair.get(SettingName.REPAIRPRICE));
        UserLogManager.notify(SettingName.LOGINFO, "Creato correttamente PDF della riparazione " + repair.get(SettingName.REPAIRNUMBER));
        }

    /**
     * Metodo che scrive all'interno di un pdf
     * @param x ascisse del file passato a parametro
     * @param y ordinata del file passato a parametro
     * @param text testo da scrivere all'interno del file
     */
        private void createDocument(float x, float y, String text){
            File pdf = new File(this.fileName);
            try {
                PDDocument doc = PDDocument.load(pdf);
                PDPage page = doc.getPage(0);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, false);
                contentStream.beginText();
                contentStream.newLineAtOffset(x, y);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.setLeading(20);
                contentStream.showText(text);
                contentStream.endText();
                contentStream.close();
                doc.save(new File(this.fileName));
                doc.close();
            } catch (IOException e) {
                UserLogManager.notify(SettingName.LOGERROR, e.getMessage());
            }

        }

    /**
     * Metodo che permette di formattare il testo nel caso di testo molto lungo
     * @param text testo da formattare
     * @param x ascissa del file
     * @param y ordinata del file
     */
        private void formatText(String text, float x,  float y){
            /**
             * Questo metodo permette di formattare il testo nel caso di testo molto lungo,
             * nello specifico nel momento in cui lo testo presenta più di 10 parole,
             * esso viene scomposto all'interno di un array e stampato riga per riga
             */
            String[] words = text.split("\\s+");
            String s = "";
            if(words.length < 10) createDocument(x, y-6, text);
            else{
                for(int i = 0; i < words.length; i++){
                    if(i % 10 == 0){
                        createDocument(x, y, s);
                        y = y - 5;
                        s = "";
                    }
                    s += words[i] + " ";
                }
                y = y - 6;
                createDocument(x, y, s);
            }
        }

    public static void main(String[] args) {
        //PDFGenerate a = new PDFGenerate("prova.pdf","pdfTemplate.pdf");
    }
}
