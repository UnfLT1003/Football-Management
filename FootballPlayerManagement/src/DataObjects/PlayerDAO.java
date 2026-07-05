package DataObjects;

import Entities.Player;
import java.util.ArrayList;
import java.util.List;


public final class PlayerDAO {

    private final List<Player> players = new ArrayList<>();
    private final FileManager fm;

    public PlayerDAO(String fileName) throws Exception {
        this.fm = new FileManager(fileName);
        load();
    }

    private void load() throws Exception {
        players.clear();
        for (String line : fm.readDataFromFile()) {
            players.add(Player.fromCsvLine(line));
        }
    }

    public List<Player> getAll() {
        return new ArrayList<>(players);
    }

   
    public List<Player> getActivePlayers() {
        List<Player> result = new ArrayList<>();
        for (Player p : players) {
            if (p.isActive()) result.add(p);
        }
        return result;
    }

    public Player getPlayerById(String id) {
        for (Player p : players) {
            if (p.getId().equalsIgnoreCase(id.trim())) return p;
        }
        return null;
    }

    
    public void add(Player p) throws Exception {
        if (getPlayerById(p.getId()) != null) {
            throw new Exception("Error: Player ID '" + p.getId() + "' already exists.");
        }
        if (isShirtNumberTakenByActive(p.getShirtNumber(), null)) {
            throw new Exception(
                "Error: Shirt number " + p.getShirtNumber()
                + " is already used by another active player.");
        }
        players.add(p);
    }

    
    public void updatePlayer(Player p) throws Exception {
        Player existing = getPlayerById(p.getId());
        if (existing == null) {
            players.add(p);
        } else if (existing != p) {
            players.remove(existing);
            players.add(p);
        }
       
    }

   
    public void deactivate(String id) throws Exception {
        Player p = getPlayerById(id);
        if (p == null) {
            throw new Exception("Error: Player ID '" + id + "' not found.");
        }
        p.setStatus(Player.STATUS_INACTIVE);
    }

 
    public boolean isShirtNumberTakenByActive(int shirtNumber, String excludeId) {
        for (Player p : players) {
            if (!p.isActive()) continue;
            if (excludeId != null && p.getId().equalsIgnoreCase(excludeId)) continue;
            if (p.getShirtNumber() == shirtNumber) return true;
        }
        return false;
    }

    public void save() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            sb.append(players.get(i).toString());
            if (i < players.size() - 1) sb.append("\n");
        }
        fm.saveDataToFile(sb.toString());
    }

    public void savePlayerListToFile() throws Exception {
        save();
    }
}