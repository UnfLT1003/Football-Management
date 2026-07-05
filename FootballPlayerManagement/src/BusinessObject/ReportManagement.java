package BusinessObject;
import java.util.*;
import Entities.Player;
import BusinessObject.PlayerManagement;

public class ReportManagement {
        //1. Generate Salary Report
        public void generateSalarySummary(List<Player> players, SalaryManagement salaryManagement, int month, int year) {
                System.out.println("\n-----SALARY SUMMARY REPORT-----");
                System.out.println(month + ("/") + year);
                System.out.printf("%-10s %-20s %-15s %-15s %-10s %-10s\n", "ID", "Name", "Type", "Base Salary", "Bonus", "Total");
                
                double totalMonthlyCost = 0;
                for (Player p : players) {
                        double totalSalary = salaryManagement.calculateMonthlySalary(month, year);
                        double bonus = salaryManagement.calculatePerformanceBonus(month, year);
                        totalMonthlyCost += totalSalary;
                System.out.printf("%-10s %-20s %-15s %-15.0f %-15.0f %-10.0f\n", p.getId(), p.getFullName(), p.getPlayerType(), p.getBaseSalary(), bonus, totalSalary);
                }
                System.out.println("Total Monthly Salary Cost: " + totalMonthlyCost + " VND");
        }
        
        
        //2. Generate All-time Top Goal Scorers Report
        public void generateTopGoalScorers(List<Player> players, List<MatchManagement> matchPerformance) {
                Map<String, Integer> goalMap = new HashMap<>();
                for (MatchManagement mm : matchPerformance) {
                        goalMap.put(mm.getPlayer(), goalMap.getOrDefault(mm.getPlayerId(), 0)+ mm.getGoals());
                }
                
                List<Player> sortedPlayers = new ArrayList<>(players);
                sortedPlayers.sort((p1, p2) -> {
                int goals1 = goalMap.getOrDefault(p1, getId(), 0);
                int goals2 = goalMap.getOrDefault(p2, getId(), 0);
                return Integer.compare(goals2, goals1);
                });
                
                System.out.println("\n-----ALL-TIME TOP GOAL SCORERS-----");
                System.out.printf("%-5s, %-10s, %-20s, %-10s\n", "Rank", "ID", "Name", "Goals");
                for (int i = 0; i < sortedPlayers.size(); i++) {
                        Player p = sortedPlayers.get(i);
                        System.out.printf("%-5s, %-10s, %-20s, %-10s\n", (i + 1), p.getId(), p.getName(), goalMap.getOrDefault(p.getId(), 0));
                }
        }
        
        
        //3. Report Menu
        public void displayReportMenu(Scanner scanner, List<Player> players, List<MatchManagement> matchPerformance, SalaryManagement salaryManagement, int month, int year) {
                boolean backToMain = false;
                while (!backToMain) {
                        System.out.println("\n-----REPORT MENU-----");
                        System.out.println("1. Salary Summary Report");
                        System.out.println("2. All-time Top Goal Scorers");
                        System.out.println("3. Back to Main Menu");
                        System.out.println("Select An Option: ");
                        
                        String choices = scanner.nextLine();
                        switch (choices){
                                case "1":
                                        generateSalarySummary(players, salaryManagement, month, year);
                                        break;
                                case "2":
                                        generateTopGoalScorers(players, matchPerformance);
                                        break;
                                case "3":
                                        backToMain = true;
                                        break;
                                default:
                                        System.out.println("Invalid Input!");
                        }
                }
        }
}
