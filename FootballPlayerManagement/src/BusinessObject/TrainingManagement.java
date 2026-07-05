package BusinessObject;

import DataObjects.*;
import Entities.*;
import Utilities.*;

import java.time.LocalDate;
import java.util.List;

public class TrainingManagement {
    private TrainingDAO trainingDAO;
    private TrainingAttendanceDAO attendanceDAO;
    private PlayerDAO playerDAO;

    public TrainingManagement(TrainingDAO tdao, TrainingAttendanceDAO adao, PlayerDAO pdao) {
        this.trainingDAO = tdao;
        this.attendanceDAO = adao;
        this.playerDAO = pdao;
    }

    public void trainingMenu() throws Exception {
        boolean stop = false;
        while (!stop) {
            Menu.printMenu("TRAINING|1.Create Session|2.Record Attendance|3.View History|4.View Attendance Details|5.Back|Select:");
            int c = Menu.getUserChoice();
            switch (c) {
                case 1: createTraining(); break;
                case 2: recordAttendance(); break;
                case 3: viewHistory(); break;
                case 4: viewAttendanceDetails(); break;
                case 5: stop = true; break;
                   default: System.out.println("Invalid");
                }
        }
    }

    private void createTraining() throws Exception {
        String id = DataInput.getStringNonEmpty("Training ID (TRxxx): ");
        
  // Kiểm tra duplicate ID
   if (trainingDAO.getById(id) != null) {
        System.out.println("Error: Training ID already exists!");
        return;
    }
  
          
        LocalDate date = DataInput.getDateNonEmpty("Date (dd/MM/yyyy): ");
        String loc = DataInput.getStringNonEmpty("Location: ");
        String topic = DataInput.getStringNonEmpty("Topic: ");
        Training t = new Training(id, date, loc, topic);
        trainingDAO.add(t);
        trainingDAO.save();
        System.out.println("Training created.");
    }

    private void recordAttendance() throws Exception {
        String tid = DataInput.getStringNonEmpty("Training ID: ");
        if (trainingDAO.getById(tid) == null) {
            System.out.println("Training not found.");
            return;
        }
        List<Player> active = playerDAO.getActivePlayers();
        if (active.isEmpty()) { System.out.println("No active players."); return; }
        System.out.println("Enter absent player IDs (comma separated). Others will be marked present.");
        String absentStr = DataInput.getString("Absent IDs: ");
        String[] absentIds = absentStr.split(",");
        
        for (String aid : absentIds) {
    boolean found = false;
    for (Player p : active) {
        if (p.getId().equalsIgnoreCase(aid.trim())) {
            found = true;
            break;
        }
    }
    if (!found) {
        System.out.println("Error: Player ID " + aid.trim() + " 5"
                + "");
        return;
    }
}
        
        
        for (Player p : active) {
            boolean absent = false;
            for (String aid : absentIds) {
                if (aid.trim().equalsIgnoreCase(p.getId())) { absent = true; break; }
            }
            attendanceDAO.addOrUpdate(new TrainingAttendance(tid, p.getId(), absent));
        }
        attendanceDAO.save();
        System.out.println("Attendance recorded.");
    }

    private void viewHistory() throws Exception {
        List<Training> list = trainingDAO.getAll();
        if (list.isEmpty()) { System.out.println("No trainings."); return; }
        System.out.printf("%-8s | %-12s | %-20s | %-30s%n", "ID", "Date", "Location", "Topic");
        for (Training t : list) t.displayInfo();
    }
    
    
private void viewAttendanceDetails() throws Exception {
    String tid = DataInput.getStringNonEmpty("Enter Training ID: ");
    Training t = trainingDAO.getById(tid);
    if (t == null) {
        System.out.println("Training not found.");
        return;
    }
    List<TrainingAttendance> list = attendanceDAO.getByTrainingId(tid);
    if (list.isEmpty()) {
        System.out.println("No attendance records for this training.");
        return;
    }
    System.out.println("\n========== ATTENDANCE DETAILS ==========");
    System.out.printf("%-10s | %-20s | %-10s%n", "PlayerID", "Name", "Status");
    System.out.println("----------------------------------------");
    for (TrainingAttendance ta : list) {
        Player p = playerDAO.getPlayerById(ta.getPlayerId());
        String name = (p != null) ? p.getName() : "Unknown";
        String status = ta.isAbsent() ? "Absent" : "Present";
        System.out.printf("%-10s | %-20s | %-10s%n", ta.getPlayerId(), name, status);
    }
    System.out.println("========================================");
}    
    
}