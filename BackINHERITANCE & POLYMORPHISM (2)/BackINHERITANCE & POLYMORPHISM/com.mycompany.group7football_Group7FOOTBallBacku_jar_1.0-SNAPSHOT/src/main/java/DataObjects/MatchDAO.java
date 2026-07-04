package DataObjects;

import Entities.Match;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MatchDAO {
    private final List<Match> matches = new ArrayList<>();
    private final FileManager fm;

    public MatchDAO(String fileName) throws Exception {
        this.fm = new FileManager(fileName);
        load();
    }

    private void load() throws Exception {
        matches.clear();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> lines = fm.readDataFromFile();  // <<--- SỬA: fm.readDataFromFile() chứ không phải fmt
        for (String line : lines) {
            String[] p = line.split(",");  // <<--- SỬA: dùng dấu phẩy, không phải space
            if (p.length >= 4) {
                Match m = new Match(p[0].trim(), 
                                    LocalDate.parse(p[1].trim(), fmt), 
                                    p[2].trim(), 
                                    p[3].trim());
                matches.add(m);
            }
        }
    }

    public List<Match> getAll() { 
        return new ArrayList<>(matches); 
    }

    public Match getById(String id) {
        return matches.stream()
                      .filter(m -> m.getMatchId().equalsIgnoreCase(id))
                      .findFirst()
                      .orElse(null);
    }

    public void add(Match m) throws Exception {  // <<--- THÊM throws Exception
        matches.add(m);
    }

    public void update(Match m) throws Exception {  // <<--- THÊM throws Exception
        Match old = getById(m.getMatchId());
        if (old != null) {
            old.setDate(m.getDate());
            old.setOpponent(m.getOpponent());
            old.setMatchType(m.getMatchType());
        }
    }

    public void delete(Match m) {
        matches.removeIf(mt -> mt.getMatchId().equalsIgnoreCase(m.getMatchId()));
    }

    public void save() throws Exception {
        String data = matches.stream()
                             .map(Match::toString)
                             .collect(Collectors.joining("\n"));  // <<--- SỬA: joining("\n") chứ không phải toList()
        fm.saveDataToFile(data);
    }
}