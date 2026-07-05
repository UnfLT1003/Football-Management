
package Entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Training {
    private String trainingId;
    private LocalDate date;
    private String location;
    private String topic;

    public Training(String trainingId, LocalDate date, String location, String topic) throws Exception {
        setTrainingId(trainingId);
        setDate(date);
        setLocation(location);
        setTopic(topic);
    }

    public String getTrainingId() { return trainingId; }
    public void setTrainingId(String id) throws Exception {
        if (!id.matches("TR\\d{3}")) throw new Exception("Training ID must be TRxxx");
        this.trainingId = id.toUpperCase();
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) throws Exception {
        if (date == null) throw new Exception("Date required");
        this.date = date;
    }

    public String getLocation() { return location; }
    public void setLocation(String loc) throws Exception {
        if (loc == null || loc.trim().isEmpty()) throw new Exception("Location required");
        this.location = loc.trim();
    }

    public String getTopic() { return topic; }
    public void setTopic(String topic) throws Exception {
        if (topic == null || topic.trim().isEmpty()) throw new Exception("Topic required");
        this.topic = topic.trim();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%s,%s,%s,%s", trainingId, date.format(fmt), location, topic);
    }

    public void displayInfo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("| %-8s | %-12s | %-20s | %-30s |%n", trainingId, date.format(fmt), location, topic);
    }
}