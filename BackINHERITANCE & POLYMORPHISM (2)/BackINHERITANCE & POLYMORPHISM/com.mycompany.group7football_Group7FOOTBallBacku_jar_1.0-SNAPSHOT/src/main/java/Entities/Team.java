
package Entities;

import Utilities.DataValidation;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamId;
    private String teamName;
    private String coach;
    private String stadium;
    private List<Player> players;

    public Team(String teamId, String teamName, String coach, String stadium) throws Exception {
        setTeamId(teamId);
        setTeamName(teamName);
        setCoach(coach);
        setStadium(stadium);
        this.players = new ArrayList<>();
    }

    public String getTeamId() { return teamId; }
    public void setTeamId(String id) throws Exception {
        if (!DataValidation.isValidTeamId(id))
            throw new Exception("Team ID must be Txxx");
        this.teamId = id.toUpperCase();
    }

    public String getTeamName() { return teamName; }
    public void setTeamName(String name) throws Exception {
        if (!DataValidation.checkStringWithFormat(name, "[A-Za-z0-9\\s]{2,50}"))
            throw new Exception("Team name 2-50 characters");
        this.teamName = toTitleCase(name);
    }

    public String getCoach() { return coach; }
    public void setCoach(String coach) throws Exception {
        if (!DataValidation.checkStringWithFormat(coach, "[A-Za-z\\s]{3,50}"))
            throw new Exception("Coach name 3-50 letters");
        this.coach = toTitleCase(coach);
    }

    public String getStadium() { return stadium; }
    public void setStadium(String stadium) throws Exception {
        if (!DataValidation.checkStringWithFormat(stadium, "[A-Za-z0-9\\s\\.]{3,100}"))
            throw new Exception("Stadium name 3-100 chars");
        this.stadium = toTitleCase(stadium);
    }

    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }

    public boolean addPlayer(Player p) {
        if (p == null) return false;
        for (Player exist : players) {
            if (exist.getJerseyNumber() == p.getJerseyNumber()) return false;
        }
        players.add(p);
        return true;
    }

    public boolean removePlayerById(String id) {
        return players.removeIf(p -> p.getId().equalsIgnoreCase(id));
    }

    private String toTitleCase(String s) {
        s = s.trim().replaceAll("\\s+", " ").toLowerCase();
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (w.length() > 0)
                sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", teamId, teamName, coach, stadium);
    }

    public void displayInfo() {
        System.out.printf("| %-8s | %-25s | %-20s | %-20s |%n", teamId, teamName, coach, stadium);
    }
}