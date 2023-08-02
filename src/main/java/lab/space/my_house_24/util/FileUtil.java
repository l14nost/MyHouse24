package lab.space.my_house_24.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUtil {
    private final static Path DIRECTORY = Paths.get("C:/Users/Amir Banov/IdeaProjects/MyHouse24/files");

    public static void saveFile(String filename, MultipartFile multipartFile) {
        if (!Files.exists(DIRECTORY)) {
            try {
                Files.createDirectory(DIRECTORY);
            } catch (IOException e) {
                log.error("Could Not Create Directory");
            }
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = DIRECTORY.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Could Not Save File");
        }
    }

    public static void deleteFile(String filename) {
        if (filename != null) {
            File file = new File(DIRECTORY + "/" + filename);
            if (file.exists()) {
                file.delete();
            } else {
                log.error("File Not Deleted  -  " + file);
            }
        } else log.warn("Filename Is Empty");
    }

    public static File getFile(String filename) {
        if (filename != null) {
            File file = new File(DIRECTORY + "/" + filename);
            if (file.exists()) {
                return file;
            } else {
                log.error("File Not return");
                return null;
            }
        }
        log.warn("Filename Is Empty");
        return null;
    }
}
