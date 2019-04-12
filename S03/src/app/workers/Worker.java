package app.workers;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Worker implements WorkerItf {
    private final static String FILEPATH = "C:/Users/anthonyc.alonsolo/Documents/GitHub/IHMTP/src/";
    private final static double duration = 5000;
    private static boolean isFirstAnnotation = false;
    private IoMaster io;

    public Worker() {
        this.io = new IoMaster();

    }

    @Override
    public void writeAnnotation(double timestampMillis, String annotation) {
        DateFormat df = new SimpleDateFormat("yyyy_MM_dd___HH_mm_ss");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        String annotationHeader = "[";
        String annotationFooter = "]";

        String newAnnotation =
                "{" +
                        "\"timestamp\":{" +
                        "\"millis\":" + timestampMillis +
                        "}," +
                        "\"duration\":{" +
                        "\"millis\":" +
                        "}," +
                        "\"annocation\":" + annotation +
                        "}";
        if (!isFirstAnnotation) {
            newAnnotation+=",";
        }else{
            isFirstAnnotation = false;
        }

        //ArrayList<String> encrypted = encrypt(io.lireFichier(FILEPATH, args[0]), args[1].toUpperCase());
        String outputFileName = "mon_journal_intime_proteger_" + reportDate + ".txt";
        //io.ecrireFichier(encrypted, FILEPATH, outputFileName);
    }

    /**
     * @param videoName
     * @return
     */
    private ArrayList<String> readAnnotation(String videoName) {
        ArrayList<String> annocations = new ArrayList<>();
        io.lireFichier(FILEPATH, videoName);
        return annocations;
    }

}
