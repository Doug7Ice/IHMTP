package app.beans;

public class Annotation {
    private String text;
    private int duration;
    private double timestampMillis;
    private String videoName;

    public Annotation(String text, int duration, double timestampMillis, String videoName) {
        this.text = text;
        this.duration = duration;
        this.timestampMillis = timestampMillis;
        this.videoName = videoName;
    }

    public Annotation(String text, double timestampMillis, String videoName) {
        this.text = text;
        this.duration = 5000;
        this.timestampMillis = timestampMillis;
        this.videoName = videoName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getTimestampMillis() {
        return timestampMillis;
    }

    public void setTimestampMillis(double timestampMillis) {
        this.timestampMillis = timestampMillis;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    @Override
    public String toString() {
        return timestampMillis +" : "+ text;
    }
}
