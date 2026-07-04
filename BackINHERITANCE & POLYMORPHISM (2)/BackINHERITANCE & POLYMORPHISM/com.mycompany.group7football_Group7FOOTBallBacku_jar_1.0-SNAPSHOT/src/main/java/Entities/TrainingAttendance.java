
package Entities;

public class TrainingAttendance {
    private String trainingId;
    private String playerId;
    private boolean absent; // true = vắng, false = có mặt

    public TrainingAttendance(String trainingId, String playerId, boolean absent) {
        this.trainingId = trainingId;
        this.playerId = playerId;
        this.absent = absent;
    }

    public String getTrainingId() { return trainingId; }
    public String getPlayerId() { return playerId; }
    public boolean isAbsent() { return absent; }
    public void setAbsent(boolean absent) { this.absent = absent; }

    @Override
    public String toString() {
        return String.format("%s,%s,%b", trainingId, playerId, absent);
    }
}