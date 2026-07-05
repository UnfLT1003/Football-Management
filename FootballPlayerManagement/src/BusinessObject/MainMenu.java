package BusinessObject;
import java.util.*;
import DataObjects.*;
import javax.xml.transform.OutputKeys;

public class MainMenu {
        public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                boolean running = true;
                
                TrainingManagement trainingManagement = new TrainingManagement(tdao, adao, pdao);
                MatchManagement matchManagement = new MatchManagement(mdao, pdao, pldao);
                ReportManagement reportManagement = new ReportManagement();
                PlayerDAO playerDAO = new PlayerDAO(fileName);
                PlayerManagement playerManagement = new PlayerManagement(playerDAO);
                while (running) {
                        System.out.println("=====================================");
                        System.out.println("\nFOOTBALL PLAYER MANAGEMENT SYSTEM");
                        System.out.println("=====================================");
                        System.out.println("1. Manage Players");
                        System.out.println("2. Training and Match Management");
                        System.out.println("3. Contract and Salary Management");
                        System.out.println("4. Reports");
                        System.out.println("5. Exit");
                        System.out.println("Choose an option: ");
                        
                        String choice = scanner.nextLine();
                        
                        switch(choice) {
                                case "1":
                                        playerManagement.processMenu(fileName);
                                        break;
                                case "2":
                                        trainingManagement.trainingMenu(tdao, adao, pdao);
                                        matchManagement.matchMenu(mdao, pdao, pldao);
                                        break;
                                case "3":
                                        //Contract and Salary Functions
                                        break;
                                case "4":
                                        reportManagement.displayReportMenu(scanner, players, matchPerformance, salaryManagement, month, year);
                                        break;
                                case "5":
                                        running = false;
                                        System.out.println("Bye Bye!");
                                        break;
                                default:
                                        System.out.println("Invalid Option!");
                        }
                }
                scanner.close();
        }
}
