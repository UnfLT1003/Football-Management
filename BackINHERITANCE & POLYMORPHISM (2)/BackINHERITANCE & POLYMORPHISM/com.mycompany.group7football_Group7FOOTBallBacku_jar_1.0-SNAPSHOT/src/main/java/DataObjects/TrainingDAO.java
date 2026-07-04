package DataObjects;

import Entities.Training;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class TrainingDAO {
    private final List<Training> trainings = new ArrayList<>();
    private final FileManager fm;

    public TrainingDAO(String fileName) throws Exception {
        this.fm = new FileManager(fileName);
        load();
    }

    private void load() throws Exception {
        trainings.clear();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> lines = fm.readDataFromFile();
        for (String line : lines) {
            String[] p = line.split(",");
            if (p.length >= 4) {
                Training t = new Training(
                        p[0].trim(), 
                        LocalDate.parse(p[1].trim(), fmt), 
                        p[2].trim(), 
                        p[3].trim()
                );
                trainings.add(t);
            }
        }
    }

    public List<Training> getAll() { 
        return new ArrayList<>(trainings); 
    }

    public Training getById(String id) {
        return trainings.stream()
                       .filter(t -> t.getTrainingId().equalsIgnoreCase(id))  // <<--- SỬA: getTrainingId()
                       .findFirst()
                       .orElse(null);
    }

    public void add(Training t) throws Exception {  // <<--- THÊM throws Exception
        trainings.add(t);
    }

    public void update(Training t) throws Exception {  // <<--- THÊM throws Exception
        Training old = getById(t.getTrainingId());  // <<--- SỬA: getTrainingId()
        if (old != null) {
            old.setDate(t.getDate());
            old.setLocation(t.getLocation());
            old.setTopic(t.getTopic());
        }
    }

    public void delete(Training t) {
        trainings.removeIf(tr -> tr.getTrainingId().equalsIgnoreCase(t.getTrainingId()));  // <<--- SỬA: dùng biến tr khác
    }

    public void save() throws Exception {
        String data = trainings.stream()
                               .map(Training::toString)
                               .collect(Collectors.joining("\n"));
        fm.saveDataToFile(data);
    }
}