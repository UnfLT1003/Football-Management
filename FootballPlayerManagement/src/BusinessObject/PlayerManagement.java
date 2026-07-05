package BusinessObject;

import DataObjects.PlayerDAO;
import Entities.Player;
import Utilities.DataInput;
import Utilities.Menu;

import java.util.*;

public class PlayerManagement {

    private final PlayerDAO playerDAO;

    public PlayerManagement(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public void processMenu() throws Exception {
        boolean stop = false;
        while (!stop) {
            Menu.printMenu("PLAYER MANAGEMENT|1.Add new player|2.Update player information|"
                    + "3.Deactivate player|4.View all players|5.Search players|"
                    + "6.View player detail|7.Back|Select:");
            int c = Menu.getUserChoice();
            switch (c) {
                case 1: addPlayer(); break;
                case 2: updatePlayer(); break;
                case 3: deactivatePlayer(); break;
                case 4: viewAllPlayers(); break;
                case 5: searchPlayers(); break;
                case 6: viewPlayerDetail(); break;
                case 7: stop = true; break;
                default: System.out.println("Invalid");
            }
        }
    }

       private void addPlayer() {
        try {
            System.out.println("--- Add New Player ---");
            String id = DataInput.getStringNonEmpty("Player ID: ");
            String name = DataInput.getStringNonEmpty("Full name: ");
            int age = DataInput.getIntegerNumber("Age: ", 16, 45);
            String nationality = DataInput.getStringNonEmpty("Nationality: ");
            String position = DataInput.getStringNonEmpty(
                    "Position (Goalkeeper/Defender/Midfielder/Forward): ");
            int shirt = DataInput.getIntegerNumber("Shirt number (1-99): ", 1, 99);
            double salary = DataInput.getDoubleNumber("Base salary: ", 0.01, Double.MAX_VALUE);
            String type = DataInput.getStringNonEmpty(
                    "Player type (Regular Player/Star Player): ");

            Player p = new Player(id, name, age, nationality, position, shirt, salary, type);
            playerDAO.add(p);
            playerDAO.save();
            System.out.println("Player added successfully!");
        } catch (Exception e) {
                        System.out.println(e.getMessage());
        }
    }

       private void updatePlayer() {
        try {
            System.out.println("--- Update Player Information ---");
            String id = DataInput.getStringNonEmpty("Player ID to update: ");
            Player p = playerDAO.getPlayerById(id);
            if (p == null) {
                System.out.println("Error: Player ID '" + id + "' not found.");
                return;
            }
            System.out.println(p.toDetailString());

            String position = DataInput.getStringNonEmpty("New position: ");
            int shirt = DataInput.getIntegerNumber("New shirt number (1-99): ", 1, 99);
            double salary = DataInput.getDoubleNumber("New base salary: ", 0.01, Double.MAX_VALUE);
            String status = DataInput.getStringNonEmpty("New status (Active/Inactive): ");

            boolean willBeActive = Player.STATUS_ACTIVE.equalsIgnoreCase(status);
                       if (willBeActive && playerDAO.isShirtNumberTakenByActive(shirt, p.getId())) {
                System.out.println(
                    "Error: Shirt number " + shirt + " is already used by another active player.");
                return;
            }

                        p.setPosition(position);
            p.setShirtNumber(shirt);
            p.setBaseSalary(salary);
            p.setStatus(status);

            playerDAO.updatePlayer(p);
            playerDAO.savePlayerListToFile();
            System.out.println("Player updated successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

       private void deactivatePlayer() {
        try {
            String id = DataInput.getStringNonEmpty("Player ID to deactivate: ");
            playerDAO.deactivate(id);
            playerDAO.savePlayerListToFile();
            System.out.println("Player deactivated successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

   
    private void viewAllPlayers() {
        List<Player> all = playerDAO.getAll();
        if (all.isEmpty()) {
            System.out.println("No players found.");
            return;
        }
        System.out.printf("%-8s | %-20s | %-12s | %-5s | %-15s | %-9s%n",
                "ID", "Name", "Position", "No", "Type", "Status");
        for (Player p : all) p.displayInfo();
    }

       private void searchPlayers() {
        System.out.println("Search by: 1.Name  2.Position  3.Nationality  4.Status");
        int type = Menu.getUserChoice();
        String keyword = DataInput.getStringNonEmpty("Enter keyword: ");

        List<Player> result = new java.util.ArrayList<>();
        for (Player p : playerDAO.getAll()) {
            boolean match = switch (type) {
                case 1 -> p.getFullName().toLowerCase().contains(keyword.toLowerCase());
                case 2 -> p.getPosition().toLowerCase().contains(keyword.toLowerCase());
                case 3 -> p.getNationality().toLowerCase().contains(keyword.toLowerCase());
                case 4 -> p.getStatus().equalsIgnoreCase(keyword);
                default -> false;
            };
            if (match) result.add(p);
        }

        if (result.isEmpty()) {
            System.out.println("No matching players found.");
        } else {
            for (Player p : result) p.displayInfo();
        }
    }

       private void viewPlayerDetail() {
        String id = DataInput.getStringNonEmpty("Player ID: ");
        Player p = playerDAO.getPlayerById(id);
        if (p == null) {
            System.out.println("Error: Player ID '" + id + "' not found.");
            return;
        }
        System.out.println(p.toDetailString());
    }
}
