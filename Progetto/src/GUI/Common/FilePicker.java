package GUI.Common;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Files;

/**
 * Classe che crea un frame con un 'JFileChooser':{@link javax.swing.JFileChooser} per aprire o salvare un file
 * @author Buffa Salvatore
 * @author Domingo Emanuele
 * @version 1.0
 * @since 1.0
 */


public class FilePicker {
    //il fram del chooser, l'oggetto chooser e il file da aprire o salvare
    JFrame frame;
    JFileChooser chooser;
    File file;

    /**
     * costruttore che crea il chooser e di default seleziona la cartella desktop
     */
    public FilePicker() {
        frame = new JFrame();
        //TODO prova questo per il settingname
        chooser = new JFileChooser(System.getProperty("user.home")+ File.separator +"Desktop");
    }

    /**
     * Metodo che setta la modalità del chooser, save se si vuole salvare un file, open se lo si vuole aprire
     * @param mode modalità del chooser, può essere 'save' o 'open'
     */
    public void setMode(String mode){
        if(mode.equals("save")){
            setSaveMode();
        }else {
            if (mode.equals("open")) {
                setOpenMode();
            }
        }
    }

    /**
     * Metodo che setta il chooser in modalità di salvataggio
     */
    private void setSaveMode(){
        chooser.setDialogTitle("Selezionare dove salvare il file");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int userSelection = chooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
    }

    /**
     * Metodo che setta il chooser in modalità di apertura
     */
    private void setOpenMode(){
        chooser.setDialogTitle("Selezionare un file da importare");
        chooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int userSelection = chooser.showOpenDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
    }

    /**
     * Metodo che restituisce il percorso del file selezionato o della directory in cui salvare il file
     * @return il percorso assoluto del file/directory selezionato/a
     */
    public String getPath(){
        if(file != null){
            return file.getAbsolutePath();
        }
        return null;
    }
}
