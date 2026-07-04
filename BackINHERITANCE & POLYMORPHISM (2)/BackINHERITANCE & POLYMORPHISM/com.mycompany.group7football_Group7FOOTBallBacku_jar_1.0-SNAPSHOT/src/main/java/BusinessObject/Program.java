
package BusinessObject;

import DataObjects.*;
import Utilities.DataInput;
import Utilities.Menu;

public class Program {
    public static void main(String[] args) {
        try {
            PlayerDAO playerDAO = new PlayerDAO("Players.txt");
            TrainingDAO trainingDAO = new TrainingDAO("Trainings.txt");
            TrainingAttendanceDAO attDAO = new TrainingAttendanceDAO("Attendances.txt");
            MatchDAO matchDAO = new MatchDAO("Matches.txt");
            MatchPerformanceDAO perfDAO = new MatchPerformanceDAO("Performances.txt");

            boolean stop = false;
            while (!stop) {
                System.out.println("\n************* FOOTBALL PLAYER MANAGEMENT SYSTEM *************");
                Menu.printMenu("1.Player Management|2.Training & Match Management|3.Contract & Salary Management|4.Reports|5.Exit|Select:");
                int choice = DataInput.getIntegerNumber();
                switch (choice) {
                    case 1:
                        PlayerManagement pm = new PlayerManagement(playerDAO);
                        pm.processMenuForPlayer();
                        break;
                    case 2:
                        boolean subStop = false;
                        while (!subStop) {
                            Menu.printMenu("--- Training & Match ---|1.Training Management|2.Match Management|3.Back to Main|Select:");
                            int sub = Menu.getUserChoice();
                            switch (sub) {
                                case 1:
                                    TrainingManagement tm = new TrainingManagement(trainingDAO, attDAO, playerDAO);
                                    tm.processMenu();
                                    break;
                                case 2:
                                    MatchManagement mm = new MatchManagement(matchDAO, perfDAO, playerDAO);
                                    mm.processMenu();
                                    break;
                                case 3:
                                    subStop = true;
                                    break;
                                default: System.out.println("Invalid");
                            }
                        }
                        break;
                    case 3:
                        SalaryManagement sm = new SalaryManagement(playerDAO);
                        sm.processMenu();
                        break;
                    case 4:
                        ReportManagement rm = new ReportManagement(playerDAO);
                        rm.processMenu();
                        break;
                    case 5:
                        stop = true;
                        System.out.println("Goodbye!");
                        break;
                    default: System.out.println("Invalid choice");
                }
            }
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}