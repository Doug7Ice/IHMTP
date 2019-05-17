package app.workers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * _____       ___  ___          _
 * |_   _|      |  \/  |         | |
 * | |  ___   | .  . | __ _ ___| |_ ___ _ __
 * | | / _ \  | |\/| |/ _` / __| __/ _ \ '__|
 * _| || (_) | | |  | | (_| \__ \ ||  __/ |
 * \___/\___/  \_|  |_/\__,_|___/\__\___|_|
 * <p>
 * <p>
 * _              ___        _   _                          ___  _                         _
 * | |            / _ \      | | | |                        / _ \| |                       | |
 * | |__  _   _  / /_\ \_ __ | |_| |__   ___  _ __  _   _  / /_\ \ | ___  _ __  ___  ___   | |     ___  _ __   ___ ____
 * | '_ \| | | | |  _  | '_ \| __| '_ \ / _ \| '_ \| | | | |  _  | |/ _ \| '_ \/ __|/ _ \  | |    / _ \| '_ \ / _ \_  /
 * | |_) | |_| | | | | | | | | |_| | | | (_) | | | | |_| | | | | | | (_) | | | \__ \ (_) | | |___| (_) | |_) |  __// /
 * |_.__/ \__, | \_| |_/_| |_|\__|_| |_|\___/|_| |_|\__, | \_| |_/_|\___/|_| |_|___/\___/  \_____/\___/| .__/ \___/___|
 * __/ |                                     __/ |                                             | |
 * |___/                                     |___/                                              |_|
 * <p>
 * La classe IoMaster contient 2 méthodes qui ont pour but de lire et écrire dans un fichiers autant de ligne que vous souhaitez.
 *
 * @author Anthony Claude Florent Alonso Lopez
 * @version 1.0.0
 * 2.0.0 delete file
 * @since 16.11.2018 16:11
 */
public class IoMaster {

    /**
     * La méthode ecrireFichier permet comme son nom l'indique d'écrire un fichier avec autant de ligne que l'on souhaite.
     *
     * @param list           Est une ArrayList (java.util) de String contant les lignes du fichier que vous souhaitez écrire. Un string équivaux à une ligne
     * @param filepath       Est un String qui contient au format (sans apostrophe) C:/Users/. Si null la valeur par défaut est C:/Users/anthonyc.alonsolo/Documents/
     * @param outputFileName Est un String qui contient le nom du fichier avec son extension. Si null la valeur par défaut est IoMasterOutput_yyyy_MM_dd___HH_mm_ss.txt
     * @return True si le fichier a été écris sans erreur. False si une erreur est survenue + System.out.println de l'erreur
     */
    public boolean ecrireFichier(ArrayList<String> list, String filepath, String outputFileName) {
        if (filepath == null) filepath = "C:/Users/anthonyc.alonsolo/Documents/";
        if (outputFileName == null) {
            DateFormat df = new SimpleDateFormat("yyyy_MM_dd___HH_mm_ss");
            Date today = Calendar.getInstance().getTime();
            String reportDate = df.format(today);
            outputFileName = "IoMasterOutput_" + reportDate + ".txt";
        }
        boolean result = false;
        if (list != null) {
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath + outputFileName, false), StandardCharsets.UTF_8))) {
                for (String line : list) {
                    if (line != null) {
                        bw.write(line);
                        bw.newLine();
                    }
                }
                bw.flush();
                result = true;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return result;
    }

    /**
     * Cette méthode permet de lire un fichier.
     *
     * @param filepath      La localisation du fichier
     * @param inputFileName Le nom du fichier que l'on souhaite lire
     * @return null si une erreur est survenue (Fichier non trouvé ou erreur -> System.out.println). Une ArrayList (java.util) de String contenant toutes les lignes du fichier.
     */
    public ArrayList<String> lireFichier(String filepath, String inputFileName) {
        ArrayList<String> result = null;
        if (new File(filepath + inputFileName).canRead()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath + inputFileName), StandardCharsets.UTF_8))) {
                result = new ArrayList<String>();
                String ligne;
                while ((ligne = br.readLine()) != null) {
                    result.add(ligne);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Cette méthode permet de supprimer un fichier
     * @param filepath      La localisation du fichier
     * @param inputFileName Le nom du fichier que l'on souhaite supprimer
     * @return true si tout c'est bien passé
     */
    public boolean supprimerFichier(String filepath, String inputFileName) {
        return new File(filepath + inputFileName).delete();
    }

}
