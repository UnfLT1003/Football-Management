
package DataObjects;

import Entities.TrainingAttendance;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class TrainingAttendanceDAO {
    private final List<TrainingAttendance> list = new ArrayList<>();
    private final FileManager fm;

    public TrainingAttendanceDAO(String file) throws Exception {
        fm = new FileManager(file);
        load();
    }

    private void load() throws Exception {
        list.clear();
        for (String line : fm.readDataFromFile()) {
            String[] p = line.split(",");
            if (p.length >= 3) {
                list.add(new TrainingAttendance(p[0].trim(), p[1].trim(), Boolean.parseBoolean(p[2].trim())));
            }
        }
    }

    public List<TrainingAttendance> getByTrainingId(String trainingId) {
        return list.stream().filter(a -> a.getTrainingId().equalsIgnoreCase(trainingId)).collect(Collectors.toList());
    }

    public void addOrUpdate(TrainingAttendance a) {
        list.removeIf(ex -> ex.getTrainingId().equalsIgnoreCase(a.getTrainingId()) && ex.getPlayerId().equalsIgnoreCase(a.getPlayerId()));
        list.add(a);
    }

    public void save() throws Exception {
        String data = list.stream().map(TrainingAttendance::toString).collect(Collectors.joining("\n"));
        fm.saveDataToFile(data);
    }
}