package app.workers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Worker implements WorkerItf {
    private final static String FILEPATH = "C:/Users/anthonyc.alonsolo/Documents/GitHub/IHMTP/src/";
    private final static double duration = 5000;
    private static boolean isFirstAnnotation = false;
    private IoMaster io;

    public Worker() {
        this.io = new IoMaster();


    }

    private static void parseAnnotationObject(JSONObject annotation) {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) annotation.get("employee");

        //Get employee first name
        String firstName = (String) employeeObject.get("firstName");
        System.out.println(firstName);

        //Get employee last name
        String lastName = (String) employeeObject.get("lastName");
        System.out.println(lastName);

        //Get employee website name
        String website = (String) employeeObject.get("website");
        System.out.println(website);
    }

    @Override
    public void writeAnnotation(double timestampMillis, String annotation) {
        //First Employee
        //ArrayList<JSONObject> annotations = new ArrayList<>();

        JSONObject annotationDetails = new JSONObject();
        annotationDetails.put("annotation", annotation);
        JSONObject timestamp = new JSONObject();
        timestamp.put("millis", timestampMillis);
        JSONObject duration = new JSONObject();
        duration.put("millis", duration);

        annotationDetails.putAll(timestamp);
        annotationDetails.putAll(duration);

        //Write JSON file
        try (FileWriter file = new FileWriter("employees.json")) {

            file.write(annotationDetails.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param videoName
     * @return
     */
    private ArrayList<String> readAnnotation(String videoName) {
        JSONArray annotationList = null;
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(videoName + ".json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            annotationList = (JSONArray) obj;
            System.out.println(annotationList);

            //Iterate over employee array
            annotationList.forEach(annotation -> parseAnnotationObject((JSONObject) annotation));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return annotationList;
    }

}
