package Core.Common;



import java.io.File;

/**
 * Classe utilizzata per definire i nomi di sistema
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class SettingName {
    public static String TITLE = "LC Elettronica Manager";
    public static String FONT = "Times New Roman";
    public static String ICONPATH = "res/logo.png";
    public static String PDFPATH = "res/template.pdf";

    public static String OPERATIONCREATE = "crea";
    public static String OPERATIONDELETE = "elimina";
    public static String OPERATIONUPDATE = "modifica";

    public static String CUSTOMERMODE = "Clienti";
    public static String CUSTOMERPIVA = "PIVA";
    public static String CUSTOMERADDRESS = "indirizzo";
    public static String CUSTOMERSHIPPINGADDRESS = "indirizzo spedizione";
    public static String CUSTOMERTELEPHONE = "Telefono";
    public static String CUSTOMERMAIL = "mail";
    public static String CUSTOMERPEC = "PEC";

    public static String COMPANYTYPE = "azienda";
    public static String COMPANYNAME = "nome";
    public static String COMPANYCODESDI = "Codice Identificativo";

    public static String PERSONTYPE = "person";
    public static String PERSONNAME = "nome";
    public static String PERSONSURNAME = "cognome";
    public static String PERSONPERSONALID = "Codice Fiscale";
    public static String PERSONCODESDI = "Codice Identificativo";

    public static String PRODUCTMODE = "Prodotto";
    public static String PRODUCTTITLE = "Titolo";
    public static String PRODUCTDESCRIPTION = "Descrizione";
    public static String PRODUCTQUANTITY = "Quantità";
    public static String PRODUCTSELLINGPRICE = "Prezzo vendita";
    public static String PRODUCTPURCHUASEPRICE = "Prezzo acquisto";
    public static String PRODUCTBRAND = "Marca";
    public static String PRODUCTMODEL = "Modello";

    public static String ACTIVITYMODE = "Attività";
    public static String ACTIVITYDESCRIPTION = "Descrizione";
    public static String ACTIVITYTYPE = "Tipo";
    public static String ACTIVITYPRICE = "Prezzo";
    public static String ACTIVITYREVENUE = "Uscita";
    public static String ACTIVITYIN = "Entrate";
    public static String ACTIVITYOUT = "Uscite";
    public static String ACTIVITYDATE = "Data";

    public static String REPAIRMODE = "Riparazione";
    public static String REPAIRCODE = "Titolo";
    public static String REPAIRNUMBER = "Numero";
    public static String REPAIRPICKUPDATE = "Data presa in carico";
    public static String REPAIRBRAND = "Marca";
    public static String REPAIRMODEL = "Modello";
    public static String REPAIRSERIALNUMBER = "Numero Seriale";
    public static String REPAIRPURCHUASEDATE = "Data acquisto";
    public static String REPAIRBRAKE = "Guasto";
    public static String REPAIRNOTE = "Nota";
    public static String REPAIRPRICE = "Prezzo";
    public static String REPAIRPREVENTIVE = "Preventivo";
    public static String REPAIRCUSTOMER = "Cliente";

    public static String GUIEDIT = "Abilitare modifica?";
    public static String GUIDELETE = "Elimina";
    public static String GUIUPDATE = "Applica";
    public static String GUIINSERT = "Inserisci";
    public static String GUIIMPORT = "Importa dati";
    public static String GUIEXPORT = "Esporta dati";
    public static String GUIUNDO = "Annulla";
    public static String GUISHOWACTIVITY = "Mostra grafico";
    public static String GUIDOWNLOAD = "Scarica contratto";
    public static String GUIRADIOPERSON = "Persone";
    public static String GUIRADIOCOMPANY = "Aziende";
    public static String GUICUSTOMERS = "Clienti";
    public static String GUIREPAIRS = "Riparazioni";
    public static String GUIPRODUCTS = "Prodotti";
    public static String GUIACTIVITIES = "Attività";
    public static String GUISEARCH = "Ricerca";
    public static String GUIDETAILS = "Dettagli";
    public static String GUISHOW = "Mostra";
    public static String GUITEXTLISTSUBSTITUTION = "Sei sicuro? La lista corrente verrà sostituita";
    public static String GUIWARNING = "Attenzione";
    public static String GUIABORTOPERATION = "Operazione annullata";
    public static String GUIFILENOTSELECTED = "File non selezionato";
    public static String GUIERROR = "Errore";
    public static String GUIYES = "Sì";
    public static String GUINO = "No";
    public static String GUIAREYOUSURE = "Sei sicuro di voler uscire?";
    public static String GUISYSTEM = "Sistema";
    public static String GUIEXPORTDATA = "Nell'esportazione si perderà il riferimento al cliente \ndella riparazione, non usare questa funzionalità \nper un backup dai dati.";



    public static String BACKUPDEFAULTPATH = System.getProperty("user.home")+ File.separator +"Documents"+File.separator+TITLE+File.separator+"Data"+File.separator;
    public static String BACKUPCUSTOMERNAME = "customer";
    public static String BACKUPPRODUCTNAME = "product";
    public static String BACKUPACTIVITYNAME = "activity";
    public static String BACKUPREPAIRNAME = "repair";

    public static String LOGGERPATH = System.getProperty("user.home")+ File.separator +"Documents"+File.separator+TITLE+File.separator+"Logs";
    public static String LOGNAME = "log.txt";
    public static String LOGINFO = "INFORMATION";
    public static String LOGERROR = "ERROR";

}
