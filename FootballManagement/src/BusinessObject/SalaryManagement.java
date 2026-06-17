package BusinessObject;

import Entities.Player;
import Entities.Match;
import Entities.MatchPerformance;
import Utilities.DataInput;
import Utilities.DataValidation;
import Utilities.Menu;
import java.util.List;
import DataObjects.IPlayerDAO;
import DataObjects.IMatchDAO;
import DataObjects.IMatchPerformanceDAO;

/**
 * Contract and Salary Management Module
 * Handled tasks: S12, S13, and Menu back-routing.
 */
public class SalaryManagement {

    private final IPlayerDAO playerDAO;
    private final IMatchDAO matchDAO;
    private final IMatchPerformanceDAO matchPerformanceDAO;        

    // Constructor - Fixed parameter types, added missing commas, and matched variable casing
    public SalaryManagement(IPlayerDAO playerDAO, IMatchDAO matchDAO, IMatchPerformanceDAO matchPerformanceDAO) {
        this.playerDAO = playerDAO;
        this.matchDAO = matchDAO;
        this.matchPerformanceDAO = matchPerformanceDAO;
    }

    // Main Module Menu Loop
    public void salaryMenu() {
        boolean stop = false;
        try {
            do {
                Menu.printMenu("-----------Contract and Salary Management-----------"
                        + "|1.Calculate Single Player Salary"
                        + "|2.Generate Salary Summary Report"
                        + "|3.Back to main menu|Select :");
                
                int choice = Menu.getUserChoice(); // Fixed: Capitalized 'U'
                switch (choice) { // Fixed: Changed 'choices' to 'choice'
                    case 1:
                        calculateSinglePlayerSalary();
                        break;
                    case 2:
                        salarySummaryReport();
                        break;
                    case 3:
                        stop = true; // Kills loop to go back to main menu
                        break;
                    default:
                        System.out.println(">>choice invalid");
                        break;
                }
            } while (!stop);
        } catch (Exception e) {
            System.out.println(">>Error: " + e.getMessage());
        }
    }

    //------------------------------------------------------------------------------
    // Task S12: Calculate Single Player Salary
    public void calculateSinglePlayerSalary() {
        try {
            System.out.println("\n----------- CALCULATE PLAYER SALARY -----------");
            int month = promptForValidInt("Enter Month: ", 1, 12);
            int year = promptForValidInt("Enter Year: ", 2000, 2100);
            String id = DataInput.getString("Enter Player ID: ");
            
            // Fixed: Added the missing assignment to fetch the player object from DAO
            Player player = playerDAO.getPlayerById(id); 
            
            if (player == null) {
                System.out.println(">>The player not found.");
                return;
            }
            
            int points = calculateMonthlyPoints(player.getId(), month, year);
            double totalSalary = player.calculateMonthlySalary(points);
            double bonus = totalSalary - player.getBaseSalary();
            
            System.out.println("Player: " + player.getName());
            System.out.println("Type: " + player.getPlayerType());
            System.out.format("Base Salary: %.0f%n", player.getBaseSalary());
            System.out.println("Monthly Performance Points: " + points);
            System.out.println("Output:");
            System.out.println("Salary Summary:");
            System.out.format("Base Salary: %.0f VND%n", player.getBaseSalary());
            System.out.format("Performance Bonus: %.0f VND%n", bonus);
            System.out.format("Total Salary: %.0f VND%n", totalSalary);
            
        } catch (Exception e) { 
            System.out.println(">>Error: " + e.getMessage());
        }
    }
    public void salarySummaryReport() {
        try{
            System.out.println("\n----------- SALARY SUMMARY REPORT -----------");
            int month = promptForValidInt("Enter month: ", 1, 12);
            int year = promtForValidInt("Enter year: ", 2000, 2100);
            
            List<Player> players = playerDAO.getPlayers();
            if (players == null || players.isEmpty()) {
                System.out.println(">>No players found in the system.");
                return;
    }
            System.out.format("Month: %02d/%d%n", month, year);
            System.out.format("%-5s %-20s %-16s %-12s %-10s %-12s%n", "ID", "Name", "Type", "Base Salary", "Bonus", "Total");
            
}