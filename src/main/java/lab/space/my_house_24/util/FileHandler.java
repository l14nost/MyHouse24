package lab.space.my_house_24.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class FileHandler {

    private static final String upload = "C:/Users/Amir Banov/IdeaProjects/MyHouse24/files";
    public  static  String saveFile(MultipartFile image){
        File uploadDirGallery = new File(upload);
        if (!uploadDirGallery.exists()) {
            if (uploadDirGallery.mkdir()){
                log.info("Directory was create");
            }else log.error("Directory wasn't create");
        }
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "-" + image.getOriginalFilename();
        String resultName = upload +"/"+ fileName;
        try {
            image.transferTo(new File((resultName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    public static void deleteFile(String filename){
        if (filename!=null) {
            File fileDelete = new File(upload + "/" + filename);
            if (fileDelete.delete()) {
                log.info("File was delete:" + filename);
            } else {
                log.error("File wasn't delete");
            }
        }else log.error("File is null");
    }
}
