package BusinessObject;

import DataObjects.PlayerDAO;
import Entities.Player;
import Utilities.DataInput;
import Utilities.Menu;
import DataObjects.MatchDAO;
import DataObjects.MatchPerformanceDAO;

import java.util.List;

public class SalaryManagement {
    private PlayerDAO playerDAO;
    private MatchDAO matchDAO;
    private MatchPerformanceDAO perfDAO;

    public SalaryManagement(PlayerDAO playerDAO, MatchDAO matchDAO, MatchPerformanceDAO perfDAO) { 
        this.playerDAO = playerDAO; 
        this.matchDAO = matchDAO;
        this.perfDAO = perfDAO;
    }
    
    public void processMenu() throws Exception {
        boolean stop = false;
        while (!stop) {
            // Updated menu string to remove the standalone Performance Bonus option
            Menu.printMenu("SALARY|1.Monthly Salary|2.Salary Summary|3.Validate Contracts|4.Back|Select:");
            int c = Menu.getUserChoice();
            switch (c) {
                case 1: calculateMonthlySalary(); break;
                case 2: displaySalarySummary(); break;
                case 3: validateContracts(); break;
                case 4: stop = true; break;
                default: System.out.println("Invalid");
            }
        }
    }

    private void calculateMonthlySalary() throws Exception {
        System.out.println("---------- CALCULATE PLAYER SALARY ----------");
        
        int month = 0;
        int year = 0;
        
        while (true) {
            try {
                month = DataInput.getIntegerNumber("Enter Month: ");
                if (month < 1 || month > 12) {
                    System.out.println("Error: Month must be between 1 and 12.");
                    continue;
                }
                year = DataInput.getIntegerNumber("Enter Year: ");
                break;
            } catch (Exception e) {
                System.out.println("Error: Invalid input! Please enter a number.");
                new java.util.Scanner(System.in).nextLine();
            }
        }
        String targetId = DataInput.getString("Enter Player ID: "); 
        
        List<Player> players = playerDAO.getAll();
        Player foundPlayer = null;
        for (Player p : players) {
            if (p.getId().equalsIgnoreCase(targetId)) {
                foundPlayer = p;
                break;
            }
        }
        
        if (foundPlayer == null) {
            System.out.println("Player ID not found.");
            return;
        }
        
        double base = foundPlayer.getBaseSalary();
        
        double bonus = foundPlayer.getGoalsScored() * 1000 + foundPlayer.getAssists() * 500
                - foundPlayer.getYellowCards() * 500 - foundPlayer.getRedCards() * 2000;
        if (bonus < 0) bonus = 0;

        double total = base;
        if (foundPlayer.getPlayerType().equalsIgnoreCase("Star Player")) total *= 1.5;
        total += bonus;

        String type = foundPlayer.getPlayerType();

        System.out.println("Player: " + foundPlayer.getFullName());
        System.out.println("Type: " + type);
        System.out.printf("Base Salary: %.0f%n", base);
        
        System.out.println("Output:");
        System.out.println("Salary Summary:");
        System.out.printf("Base Salary: %.0f VND%n", base);
        System.out.printf("Performance Bonus: %.0f VND%n", bonus);
        System.out.printf("Total Salary: %.0f VND%n", total);
        System.out.println();
    }

    private void displaySalarySummary() throws Exception {
        int month = 0;
        int year = 0;
        
        while (true) {
            try {
                month = DataInput.getIntegerNumber("Enter Month: ");
                if (month < 1 || month > 12) {
                    System.out.println("Error: Month must be between 1 and 12.");
                    continue;
                }
                year = DataInput.getIntegerNumber("Enter Year: ");
                break;
            } catch (Exception e) {
                System.out.println("Error: Invalid input! Please enter a number.");
                new java.util.Scanner(System.in).nextLine();
            }
        }
        
        System.out.println("---------- SALARY SUMMARY REPORT ----------");
        System.out.printf("Month: %d/%d%n", month, year);
        System.out.printf("%-4s%-21s%-17s%-13s%-9s%s%n", "ID", "Name", "Type", "Base Salary", "Bonus", "Total");
        System.out.println("-------------------------------------------------------------------------");
        
        List<Player> players = playerDAO.getAll();
        double total = 0;
        for (Player p : players) {
            double base = p.getBaseSalary();
            
            double bonus = p.getGoalsScored() * 1000 + p.getAssists() * 500
                    - p.getYellowCards() * 500 - p.getRedCards() * 2000;
            if (bonus < 0) bonus = 0;

            double totalPlayerSalary = base;
            if (p.getPlayerType().equalsIgnoreCase("Star Player")) totalPlayerSalary *= 1.5;
            totalPlayerSalary += bonus;

            String type = p.getPlayerType();

            System.out.printf("%-4s%-21s%-17s%-13.0f%-9.0f%.0f%n", 
                    p.getId(), p.getFullName(), type, base, bonus, totalPlayerSalary);
            
            if (p.getStatus().equalsIgnoreCase(Player.STATUS_ACTIVE)) total += totalPlayerSalary;
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("Total Monthly Salary Cost: %.0f VND%n", total);
        System.out.println("Press ENTER to return...");
    }

    private void validateContracts() {
        List<Player> players = playerDAO.getAll();
        boolean ok = true;
        for (Player p : players) {
            if (p.getPlayerType().equalsIgnoreCase("Star Player") && p.getBaseSalary() < 1000) {
                System.out.println("WARNING: Star player " + p.getId() + " base salary < 1000");
                ok = false;
            }
            if (p.getPlayerType().equalsIgnoreCase("Regular Player") && p.getBaseSalary() < 500) {
                System.out.println("WARNING: Regular player " + p.getId() + " base salary < 500");
                ok = false;
            }
        }
        if (ok) System.out.println("All contracts are valid.");
    }
}