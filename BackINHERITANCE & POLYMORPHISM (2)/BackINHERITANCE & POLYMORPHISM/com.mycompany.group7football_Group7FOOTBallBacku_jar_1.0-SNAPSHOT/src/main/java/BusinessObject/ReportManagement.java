package BusinessObject;

import DataObjects.PlayerDAO;
import Entities.Player;
import Utilities.DataInput;
import Utilities.Menu;

import java.util.List;

public class ReportManagement {
    private PlayerDAO playerDAO;

    public ReportManagement(PlayerDAO playerDAO) { this.playerDAO = playerDAO; }

    public void processMenu() throws Exception {
        boolean stop = false;
        while (!stop) {
            Menu.printMenu("REPORTS|1.Top Goal Scorers|2.Salary Summary Report|3.Back|Select:");
            int c = Menu.getUserChoice();
            switch (c) {
                case 1: topGoalScorers(); break;
                case 2: salarySummaryReport(); break;
                case 3: stop = true; break;
                default: System.out.println("Invalid");
            }
        }
    }

    private void topGoalScorers() {
        List<Player> players = playerDAO.getPlayers();
        players.sort((a,b) -> Integer.compare(b.getGoalsScored(), a.getGoalsScored()));
        System.out.println("\n=== ALL-TIME TOP GOAL SCORERS ===");
        System.out.printf("%-3s | %-8s | %-20s | %-10s | %-5s%n", "Rank", "ID", "Name", "Goals", "Assists");
        int rank = 1;
        for (Player p : players) {
            System.out.printf("%-3d | %-8s | %-20s | %-10d | %-5d%n",
                    rank++, p.getId(), p.getName(), p.getGoalsScored(), p.getAssists());
        }
    }

    private void salarySummaryReport() throws Exception {
        int month = DataInput.getIntegerNumber("Month (1-12): ", 1, 12);
        int year = DataInput.getIntegerNumber("Year: ");
        List<Player> players = playerDAO.getPlayers();
        System.out.printf("\n=== SALARY SUMMARY REPORT - %d/%d ===\n", month, year);
        System.out.printf("%-8s | %-20s | %-12s | %-15s%n", "ID", "Name", "Type", "Total Salary");
        double total = 0;
        for (Player p : players) {
            int performancePoints = 0;
            double sal = p.calculateMonthlySalary(performancePoints);
            if (p.getStatus().equalsIgnoreCase("Active")) total += sal;
            System.out.printf("%-8s | %-20s | %-12s | %-15.2f%n",
                    p.getId(), p.getName(), p.getPlayerType(), sal);
        }
        System.out.printf("GRAND TOTAL: %.2f USD%n", total);
    }
}