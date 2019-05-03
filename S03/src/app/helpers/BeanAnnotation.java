package app.helpers;

/**
 *
 * @author Anthony Claude Florent Alonso Lopez
 */
public class BeanAnnotation {
    private String annotation;
    private Duration duration;
    private Timestamp timestamp;

    public BeanAnnotation(String annotation) {
        this.duration = new Duration(5000);
        this.annotation = annotation;
        this.timestamp = new Timestamp(0);
    }

    public BeanAnnotation(Duration duration, String annotation) {
        this.duration = duration;
        this.annotation = annotation;
        this.timestamp = new Timestamp(0);
    }
    
    public BeanAnnotation(Duration duration, String annotation, Timestamp timestamp) {
        this.duration = duration;
        this.annotation = annotation;
        this.timestamp = timestamp;
    }

    public String toStringJson (){
        return objectMapper.writeValueAsString(this);
    }
}
