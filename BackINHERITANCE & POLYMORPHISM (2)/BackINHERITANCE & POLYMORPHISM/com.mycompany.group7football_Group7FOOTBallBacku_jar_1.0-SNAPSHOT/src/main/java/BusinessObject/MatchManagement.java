package BusinessObject;

import DataObjects.*;
import Entities.*;
import Utilities.*;

import java.time.LocalDate;
import java.util.List;

public class MatchManagement {
    private MatchDAO matchDAO;
    private MatchPerformanceDAO perfDAO;
    private PlayerDAO playerDAO;

    public MatchManagement(MatchDAO mdao, MatchPerformanceDAO pdao, PlayerDAO plDAO) {
        this.matchDAO = mdao;
        this.perfDAO = pdao;
        this.playerDAO = plDAO;
    }

    public void processMenu() throws Exception {
        boolean stop = false;
        while (!stop) {
            Menu.printMenu("MATCH|1.Create Match|2.Add/Update Performance|3.View History|4.Back|Select:");
            int c = Menu.getUserChoice();
            switch (c) {
                case 1: createMatch(); break;
                case 2: addUpdatePerformance(); break;
                case 3: viewHistory(); break;
                case 4: stop = true; break;
                default: System.out.println("Invalid");
            }
        }
    }

    private void createMatch() throws Exception {
        String id = DataInput.getStringNonEmpty("Match ID (Mxxx): ");
        
        
    // Kiểm tra duplicate ID
         if (matchDAO.getById(id) != null) {
        System.out.println("Error: Match ID already exists!");
        return;
    }
        
        LocalDate date = DataInput.getDateNonEmpty("Date (dd/MM/yyyy): ");
        String opp = DataInput.getStringNonEmpty("Opponent: ");
        String type = DataInput.getStringNonEmpty("Match type: ");
        Match m = new Match(id, date, opp, type);
        matchDAO.add(m);
        matchDAO.save();
        System.out.println("Match created.");
    }

    private void addUpdatePerformance() throws Exception {
        String mid = DataInput.getStringNonEmpty("Match ID: ");
        if (matchDAO.getById(mid) == null) { System.out.println("Match not found."); return; }
        String pid = DataInput.getStringNonEmpty("Player ID: ");
        Player p = playerDAO.getPlayerById(pid);
        if (p == null) { System.out.println("Player not found."); return; }
        int goals = DataInput.getIntegerNumber("Goals: ", 0, 99);
        int assists = DataInput.getIntegerNumber("Assists: ", 0, 99);
        int yc = DataInput.getIntegerNumber("Yellow cards: ", 0, 99);
        int rc = DataInput.getIntegerNumber("Red cards: ", 0, 99);
        int minutes = DataInput.getIntegerNumber("Minutes played: ", 0, 120);
        MatchPerformance mp = new MatchPerformance(mid, pid, goals, assists, yc, rc, minutes);
        perfDAO.addOrUpdate(mp);
        perfDAO.save();
        // Cập nhật tổng hợp vào Player
        p.setGoalsScored(p.getGoalsScored() + goals);
        p.setAssists(p.getAssists() + assists);
        p.setYellowCards(p.getYellowCards() + yc);
        p.setRedCards(p.getRedCards() + rc);
        playerDAO.updatePlayer(p);
        playerDAO.savePlayerListToFile();
        System.out.println("Performance recorded.");
    }

    private void viewHistory() throws Exception {
        List<Match> matches = matchDAO.getAll();
        if (matches.isEmpty()) { System.out.println("No matches."); return; }
        System.out.printf("%-8s | %-12s | %-25s | %-15s%n", "ID", "Date", "Opponent", "Type");
        for (Match m : matches) m.displayInfo();
        String mid = DataInput.getStringAllowEmpty("Enter Match ID to see details (or Enter): ");
        if (mid != null && !mid.isEmpty()) {
            List<MatchPerformance> perfs = perfDAO.getByMatchId(mid);
            if (perfs.isEmpty()) System.out.println("No performance data.");
            else {
                System.out.println("\nPerformance for " + mid);
                System.out.printf("%-10s | %-8s | %-8s | %-5s | %-5s | %-8s%n",
                        "PlayerID", "Goals", "Assists", "YC", "RC", "Mins");
                for (MatchPerformance mp : perfs) {
                    System.out.printf("%-10s | %-8d | %-8d | %-5d | %-5d | %-8d%n",
                            mp.getPlayerId(), mp.getGoals(), mp.getAssists(),
                            mp.getYellowCards(), mp.getRedCards(), mp.getMinutesPlayed());
                }
            }
        }
    }
}
