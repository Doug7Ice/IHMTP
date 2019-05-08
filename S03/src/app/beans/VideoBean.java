package app.beans;

import java.util.ArrayList;

public class VideoBean {
    private String title;
    private double lenght;
    private ArrayList<Annotation> listAnnotations;

    /**
     * Main constructor of videoBean
     * @param title of the video
     * @param listAnnotations An ArrayList of Annotation
     */
    public VideoBean(String title, ArrayList<Annotation> listAnnotations) {
        this.title = title;
        this.lenght = lenght;
        this.listAnnotations = listAnnotations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLenght() {
        return lenght;
    }

    public void setLenght(double lenght) {
        this.lenght = lenght;
    }

    public ArrayList<Annotation> getListAnnotations() {
        return listAnnotations;
    }

    public void setListAnnotations(ArrayList<Annotation> listAnnotations) {
        this.listAnnotations = listAnnotations;
    }
}
