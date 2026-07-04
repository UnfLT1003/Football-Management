package BusinessObject;

import DataObjects.PlayerDAO;
import Entities.Player;
import Utilities.DataInput;
import Utilities.Menu;

import java.util.List;

public class SalaryManagement {
    private PlayerDAO playerDAO;

    public SalaryManagement(PlayerDAO playerDAO) { this.playerDAO = playerDAO; }

    public void processMenu() throws Exception {
        boolean stop = false;
        while (!stop) {
            Menu.printMenu("SALARY|1.Monthly Salary|2.Performance Bonus|3.Salary Summary|4.Validate Contracts|5.Back|Select:");
            int c = Menu.getUserChoice();
            switch (c) {
                case 1: calculateMonthlySalary(); break;
                case 2: calculatePerformanceBonus(); break;
                case 3: displaySalarySummary(); break;
                case 4: validateContracts(); break;
                case 5: stop = true; break;
                default: System.out.println("Invalid");
            }
        }
    }

    private void calculateMonthlySalary() throws Exception {
        int month = DataInput.getIntegerNumber("Month (1-12): ", 1, 12);
        int year = DataInput.getIntegerNumber("Year: ");
        List<Player> players = playerDAO.getPlayers();
        System.out.printf("\n=== Salary for %d/%d ===\n", month, year);
        System.out.printf("%-8s | %-20s | %-12s | %-15s | %-15s%n",
                "ID", "Name", "Type", "Base", "Total");
        for (Player p : players) {
            int performancePoints = 0;
            double total = p.calculateMonthlySalary(performancePoints);
            System.out.printf("%-8s | %-20s | %-12s | %-15.2f | %-15.2f%n",
                    p.getId(), p.getName(), p.getPlayerType(), p.getBaseSalary(), total);
        }
    }

    private void calculatePerformanceBonus() {
        List<Player> players = playerDAO.getPlayers();
        System.out.println("\n=== PERFORMANCE BONUS (All-time) ===");
        for (Player p : players) {
            double bonus = p.getGoalsScored() * 1000 + p.getAssists() * 500
                    - p.getYellowCards() * 500 - p.getRedCards() * 2000;
            if (bonus < 0) bonus = 0;
            System.out.printf("%-8s %-20s : %.2f USD%n", p.getId(), p.getName(), bonus);
        }
    }

    private void displaySalarySummary() {
        List<Player> players = playerDAO.getPlayers();
        double total = 0;
        System.out.println("\n=== SALARY SUMMARY ===");
        System.out.printf("%-8s | %-20s | %-12s | %-15s | %-12s%n",
                "ID", "Name", "Type", "Base Salary", "Status");
        for (Player p : players) {
            System.out.printf("%-8s | %-20s | %-12s | %-15.2f | %-12s%n",
                    p.getId(), p.getName(), p.getPlayerType(), p.getBaseSalary(), p.getStatus());
            if (p.getStatus().equalsIgnoreCase("Active")) total += p.getBaseSalary();
        }
        System.out.printf("Total monthly salary for active players: %.2f USD%n", total);
    }

    private void validateContracts() {
        List<Player> players = playerDAO.getPlayers();
        boolean ok = true;
        for (Player p : players) {
            if (p.getPlayerType().equalsIgnoreCase("StarPlayer") && p.getBaseSalary() < 1000) {
                System.out.println("WARNING: Star player " + p.getId() + " base salary < 1000");
                ok = false;
            }
            if (p.getPlayerType().equalsIgnoreCase("RegularPlayer") && p.getBaseSalary() < 500) {
                System.out.println("WARNING: Regular player " + p.getId() + " base salary < 500");
                ok = false;
            }
        }
        if (ok) System.out.println("All contracts are valid.");
    }
}