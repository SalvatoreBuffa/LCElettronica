import Core.Common.SettingName;
import Core.Common.UserLogManager;
import GUI.Frame;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.logging.Level;


public class Main {

    public static void main(String[] args) {
        UserLogManager.initLogger();
        File backupPath = new File(SettingName.BACKUPDEFAULTPATH);
        if(!backupPath.isDirectory()){
            String message = "Nella cartella "+ FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\"+SettingName.TITLE+"\n" +
                    "saranno memorizzati i file di sistema dell'applicazione.\n" +
                    "Per evitare perdite di dati o malfunzionamenti\n" +
                    "non eliminare la cartella.";
            JOptionPane.showMessageDialog(null, message, "Informazioni", JOptionPane.INFORMATION_MESSAGE);

            if(backupPath.mkdirs()){
                UserLogManager.notify(SettingName.LOGINFO, "Creata cartella data in "+ SettingName.BACKUPDEFAULTPATH);
            }
        }
        UserLogManager.notify(SettingName.LOGINFO, "Avvio Applicazione");
        Frame.init();
    }
}
