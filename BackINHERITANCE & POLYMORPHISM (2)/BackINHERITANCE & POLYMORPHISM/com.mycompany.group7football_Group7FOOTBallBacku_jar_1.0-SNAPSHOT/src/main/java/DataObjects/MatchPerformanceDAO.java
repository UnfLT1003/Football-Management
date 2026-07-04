
package DataObjects;

import Entities.MatchPerformance;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MatchPerformanceDAO {
    private final List<MatchPerformance> perfs = new ArrayList<>();
    private final FileManager fm;

    public MatchPerformanceDAO(String file) throws Exception {
        fm = new FileManager(file);
        load();
    }

    private void load() throws Exception {
        perfs.clear();
        for (String line : fm.readDataFromFile()) {
            String[] p = line.split(",");
            if (p.length >= 7) {
                MatchPerformance mp = new MatchPerformance(p[0].trim(), p[1].trim(),
                        Integer.parseInt(p[2].trim()), Integer.parseInt(p[3].trim()),
                        Integer.parseInt(p[4].trim()), Integer.parseInt(p[5].trim()),
                        Integer.parseInt(p[6].trim()));
                perfs.add(mp);
            }
        }
    }

    public List<MatchPerformance> getByMatchId(String matchId) {
        return perfs.stream().filter(mp -> mp.getMatchId().equalsIgnoreCase(matchId)).collect(Collectors.toList());
    }

    public List<MatchPerformance> getByPlayerId(String playerId) {
        return perfs.stream().filter(mp -> mp.getPlayerId().equalsIgnoreCase(playerId)).collect(Collectors.toList());
    }

    public void addOrUpdate(MatchPerformance mp) {
        perfs.removeIf(ex -> ex.getMatchId().equalsIgnoreCase(mp.getMatchId()) && ex.getPlayerId().equalsIgnoreCase(mp.getPlayerId()));
        perfs.add(mp);
    }

    public void save() throws Exception {
        String data = perfs.stream().map(MatchPerformance::toString).collect(Collectors.joining("\n"));
        fm.saveDataToFile(data);
    }
}