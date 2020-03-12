package Core.Activity;

import Core.Common.SettingName;
/**
 * Classe utilizzata per creare un oggetto'Activity':{@link Core.Activity.Activity}
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */

public class ActivityBuilder {

    private Activity activity;

    public ActivityBuilder(){
        activity = new Activity();
    }

    /**
     * Metodo che permette l'inserimento del campo descrizione all'interno della attività
     * @param description descrizione da aggiungere alla attività
     */
    public void addDescription(String description){
        activity.add(SettingName.ACTIVITYDESCRIPTION, description);
    }

    /**
     * Metodo che permette l'inserimento del campo Tipo all'interno della attività
     * @param type tipo da aggiungere alla attività
     */
    public void addType(String type){
        activity.add(SettingName.ACTIVITYTYPE, type);
    }

    /**
     * Metodo che permette l'inserimento del campo prezzo all'interno della attività
     * @param price prezzo da aggiungere alla attività
     */
    public void addPrice(double price){
        activity.add(SettingName.ACTIVITYPRICE, Double.toString(price));
    }
    /**
     * Metodo che permette l'inserimento del campo segno all'interno della attività, ossia se l'attività prevede una entrata o una uscita
     * @param sign segno da aggiungere alla attività
     */
    public void addSign(boolean sign){
        activity.add(SettingName.ACTIVITYREVENUE, Boolean.toString(sign));
    }
    /**
     * Metodo che permette l'inserimento del campo data all'interno della attività
     * @param date data da aggiungere alla attività
     */
    public void addDate(String date){
        activity.add(SettingName.ACTIVITYDATE, date.toString());
    }

    /**
     * Metodo che effettuata la costruzione di una nuova attività la restituisce
     * @return attività creata con i metodi precedenti
     */
    public Activity getResult(){
        return this.activity;
    }
}
