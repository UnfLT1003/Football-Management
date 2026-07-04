
package BusinessObject;

import DataObjects.PlayerDAO;
import Entities.Player;
import Entities.RegularPlayer;
import Entities.StarPlayer;
import Utilities.DataInput;
import Utilities.Menu;
import java.time.LocalDate;
import java.util.List;

public class PlayerManagement {
    private PlayerDAO playerDAO;

    public PlayerManagement(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public void processMenuForPlayer() throws Exception {
        boolean stop = false;
        while (!stop) {
            Menu.printMenu("******PLAYER MANAGEMENT******|1.Add Player|2.Update Player|3.Deactivate Player|4.Search Players|5.Print All|6.Export|7.Back|Select:");
            int choice = Menu.getUserChoice();
            switch (choice) {
                case 1: addNewPlayer(); break;
                case 2: updatePlayer(); break;
                case 3: deactivatePlayer(); break;
                case 4: searchPlayers(); break;
                case 5: printAllPlayers(); break;
                case 6: exportToFile(); break;
                case 7: stop = true; break;
                default: System.out.println("Invalid");
            }
        }
    }

    private void addNewPlayer() throws Exception {
        System.out.println("--- ADD NEW PLAYER ---");
        String id = DataInput.getStringNonEmpty("Player ID (Pxxx): ");
        if (playerDAO.getPlayerById(id) != null) {
            System.out.println("ID already exists!");
            return;
        }
        String name = DataInput.getStringNonEmpty("Name: ");
        String pos = DataInput.getStringNonEmpty("Position (Goalkeeper/Defender/Midfielder/Forward): ");
        int jersey = DataInput.getIntegerNumber("Jersey number (1-99): ", 1, 99);
        LocalDate dob = DataInput.getDateNonEmpty("Date of birth (dd/MM/yyyy): ");
        String nation = DataInput.getStringNonEmpty("Nationality: ");
        double salary = DataInput.getDoubleNumber("Base salary (USD): ", 0);

        System.out.println("Player Type: 1. Regular Player   2. Star Player");
        int typeChoice = DataInput.getIntegerNumber("Choose (1-2): ", 1, 2);
        String status = "Active";

        Player p;
        if (typeChoice == 2) {
            p = new StarPlayer(id, name, pos, jersey, dob, nation, salary, status);
        } else {
            p = new RegularPlayer(id, name, pos, jersey, dob, nation, salary, status);
        }

        playerDAO.addPlayer(p);
        playerDAO.savePlayerListToFile();
        System.out.println("Player added.");
    }

    private void updatePlayer() throws Exception {
        String id = DataInput.getStringNonEmpty("Player ID to update: ");
        Player p = playerDAO.getPlayerById(id);
        if (p == null) { System.out.println("Not found."); return; }
        System.out.println("Leave blank to keep current value.");
        String newName = DataInput.getStringAllowEmpty("New name: ");
        if (newName != null && !newName.isEmpty()) p.setName(newName);
        String newPos = DataInput.getStringAllowEmpty("New position: ");
        if (newPos != null && !newPos.isEmpty()) p.setPosition(newPos);
        int newJersey = DataInput.getIntegerNumber("New jersey (0 to skip): ");
        if (newJersey > 0) p.setJerseyNumber(newJersey);
        double newSalary = DataInput.getDoubleNumber("New base salary (-1 to skip): ");
        if (newSalary >= 0) p.setBaseSalary(newSalary);
        String newStatus = DataInput.getStringAllowEmpty("New status (Active/Inactive): ");
        if (newStatus != null && !newStatus.isEmpty()) p.setStatus(newStatus);
        playerDAO.updatePlayer(p);
        playerDAO.savePlayerListToFile();
        System.out.println("Updated.");
    }

    private void deactivatePlayer() throws Exception {
        String id = DataInput.getStringNonEmpty("Player ID to deactivate: ");
        Player p = playerDAO.getPlayerById(id);
        if (p == null) { System.out.println("Not found."); return; }
        if (Menu.confirmAction("deactivate " + id)) {
            p.setStatus("Inactive");
            playerDAO.updatePlayer(p);
            playerDAO.savePlayerListToFile();
            System.out.println("Player deactivated.");
        }
    }

    private void searchPlayers() throws Exception {
        Menu.printMenu("Search by:|1.Name|2.Position|3.Nationality|4.Status|Select:");
        int opt = Menu.getUserChoice();
        String keyword = DataInput.getString("Enter keyword: ");
        List<Player> result = null;
        switch (opt) {
            case 1: result = playerDAO.search(p -> p.getName().toLowerCase().contains(keyword.toLowerCase())); break;
            case 2: result = playerDAO.search(p -> p.getPosition().equalsIgnoreCase(keyword)); break;
            case 3: result = playerDAO.search(p -> p.getNationality().toLowerCase().contains(keyword.toLowerCase())); break;
            case 4: result = playerDAO.search(p -> p.getStatus().equalsIgnoreCase(keyword)); break;
            default: System.out.println("Invalid");
        }
        if (result != null && !result.isEmpty()) printList(result);
        else System.out.println("No results.");
    }

    private void printAllPlayers() throws Exception {
        printList(playerDAO.getPlayers());
    }

    private void printList(List<Player> list) {
        System.out.printf("%-8s | %-20s | %-15s | %-5s | %-12s | %-10s%n",
                "ID", "Name", "Position", "No.", "Status", "Goals");
        for (Player p : list) {
            System.out.printf("%-8s | %-20s | %-15s | %-5d | %-12s | %-10d%n",
                    p.getId(), p.getName(), p.getPosition(), p.getJerseyNumber(),
                    p.getStatus(), p.getGoalsScored());
        }
    }

    private void exportToFile() throws Exception {
        playerDAO.savePlayerListToFile();
        System.out.println("Exported to Players.txt");
    }
}
