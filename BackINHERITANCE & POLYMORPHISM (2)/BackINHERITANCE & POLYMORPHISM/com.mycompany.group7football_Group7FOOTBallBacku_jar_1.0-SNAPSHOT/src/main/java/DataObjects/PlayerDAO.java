package DataObjects;

import Entities.Player;
import Entities.RegularPlayer;
import Entities.StarPlayer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class PlayerDAO {
    private final List<Player> playerList = new ArrayList<>();
    private final FileManager fileManager;

    public PlayerDAO(String fileName) throws Exception {
        this.fileManager = new FileManager(fileName);
        loadDataFromFile();
    }

    public void loadDataFromFile() throws Exception {
        playerList.clear();
        List<String> lines = fileManager.readDataFromFile();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (String line : lines) {
            try {
                String[] parts = line.split(",");
                if (parts.length < 14) continue;
                String id = parts[0].trim();
                String name = parts[1].trim();
                String pos = parts[2].trim();
                int jersey = Integer.parseInt(parts[3].trim());
                LocalDate dob = LocalDate.parse(parts[4].trim(), fmt);
                String nation = parts[5].trim();
                double mv = Double.parseDouble(parts[6].trim());
                int goals = Integer.parseInt(parts[7].trim());
                double salary = Double.parseDouble(parts[8].trim());
                String pType = parts[9].trim();
                String status = parts[10].trim();
                int assists = Integer.parseInt(parts[11].trim());
                int yc = Integer.parseInt(parts[12].trim());
                int rc = Integer.parseInt(parts[13].trim());

                Player p;
                if (pType.equalsIgnoreCase("StarPlayer")) {
                    p = new StarPlayer(id, name, pos, jersey, dob, nation, mv, goals,
                                       salary, status, assists, yc, rc);
                } else {
                    p = new RegularPlayer(id, name, pos, jersey, dob, nation, mv, goals,
                                          salary, status, assists, yc, rc);
                }
                playerList.add(p);
            } catch (Exception e) { /* bỏ qua dòng lỗi */ }
        }
    }

    public List<Player> getPlayers() {
        playerList.sort((a,b) -> a.getId().compareTo(b.getId()));
        return new ArrayList<>(playerList);
    }

    public Player getPlayerById(String id) {
        return playerList.stream().filter(p -> p.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public List<Player> getActivePlayers() {
        return playerList.stream().filter(p -> p.getStatus().equalsIgnoreCase("Active")).collect(Collectors.toList());
    }

    public void addPlayer(Player p) {
        playerList.add(p);
    }

    public void updatePlayer(Player p) throws Exception {
        Player old = getPlayerById(p.getId());
        if (old != null) {
            old.setName(p.getName());
            old.setPosition(p.getPosition());
            old.setJerseyNumber(p.getJerseyNumber());
            old.setDateOfBirth(p.getDateOfBirth());
            old.setNationality(p.getNationality());
            old.setMarketValue(p.getMarketValue());
            old.setGoalsScored(p.getGoalsScored());
            old.setBaseSalary(p.getBaseSalary());
            old.setStatus(p.getStatus());
            old.setAssists(p.getAssists());
            old.setYellowCards(p.getYellowCards());
            old.setRedCards(p.getRedCards());
        }
    }

    public void removePlayer(Player p) {
        playerList.removeIf(pl -> pl.getId().equalsIgnoreCase(p.getId()));
    }

    public void savePlayerListToFile() throws Exception {
        String data = playerList.stream().map(Player::toString).collect(Collectors.joining("\n"));
        fileManager.saveDataToFile(data);
    }

    public List<Player> search(Predicate<Player> pred) {
        return playerList.stream().filter(pred).collect(Collectors.toList());
    }
}