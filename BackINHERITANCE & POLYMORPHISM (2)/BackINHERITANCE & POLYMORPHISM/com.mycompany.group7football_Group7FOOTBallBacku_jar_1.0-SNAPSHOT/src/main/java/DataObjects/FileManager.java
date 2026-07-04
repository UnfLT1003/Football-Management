package DataObjects;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public List<String> readDataFromFile() throws Exception {
        File f = new File(fileName);
        if (!f.exists()) {
            // Dùng ArrayList.emptyList() thay vì List.of() vì Java 8 không hỗ trợ
            return new ArrayList<>();
        }
        return Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
    }

    public void saveDataToFile(String data) throws Exception {
        Files.write(new File(fileName).toPath(), data.getBytes(StandardCharsets.UTF_8));
    }
}