package DataObjects;

import Entities.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class TeamDAO {
    private final List<Team> teamList = new ArrayList<>();
    private final FileManager fileManager;

    public TeamDAO(String fileName) throws Exception {
        this.fileManager = new FileManager(fileName);
        loadDataFromFile();
    }

    public void loadDataFromFile() throws Exception {
        teamList.clear();
        List<String> lines = fileManager.readDataFromFile();
        for (String line : lines) {
            String[] p = line.split(",");
            if (p.length >= 4) {
                Team t = new Team(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim());
                teamList.add(t);
            }
        }
    }

    public List<Team> getAllTeams() { 
        return new ArrayList<>(teamList); 
    }

    public Team getTeamById(String id) {
        return teamList.stream()
                       .filter(t -> t.getTeamId().equalsIgnoreCase(id))  // <<--- SỬA: getTeamId() chứ không phải getId()
                       .findFirst()
                       .orElse(null);
    }

    public void addTeam(Team t) throws Exception {  // <<--- THÊM throws
        teamList.add(t);
    }

    public void updateTeam(Team t) throws Exception {  // <<--- THÊM throws
        Team old = getTeamById(t.getTeamId());
        if (old != null) {
            old.setTeamName(t.getTeamName());
            old.setCoach(t.getCoach());
            old.setStadium(t.getStadium());
        }
    }

    public void removeTeam(Team t) {
        teamList.removeIf(te -> te.getTeamId().equalsIgnoreCase(t.getTeamId()));  // <<--- SỬA: dùng biến te khác
    }

    public void saveToFile() throws Exception {
        String data = teamList.stream()
                              .map(Team::toString)
                              .collect(Collectors.joining("\n"));
        fileManager.saveDataToFile(data);
    }
}