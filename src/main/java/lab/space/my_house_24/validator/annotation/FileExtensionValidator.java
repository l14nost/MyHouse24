package lab.space.my_house_24.validator.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class FileExtensionValidator implements ConstraintValidator<FileExtension, Object> {

    private String[] allowedExtensions;

    @Override
    public void initialize(FileExtension constraintAnnotation) {
        allowedExtensions = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value instanceof List) {
            List<MultipartFile> files = (List<MultipartFile>) value;
            return isValidFileList(files);
        } else if (value instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) value;
            return isValidSingleFile(file);
        }

        return true;
    }

    private boolean isValidFileList(List<MultipartFile> files) {
        List<Boolean> checkList = new ArrayList<>();

        if (files.isEmpty()) {
            return true;
        }


        for (MultipartFile file : files) {
            if (file.isEmpty() && file.getOriginalFilename().isEmpty()){
                checkList.add(true);
                continue;
            }
            if (!isFileExtensionAllowed(file)) {
                checkList.add(false);
            }
        }
        for (boolean check:checkList){
            if (!check){
                return false;
            }
        }
        return true;
    }

    private boolean isValidSingleFile(MultipartFile file) {
        if (!file.getOriginalFilename().isEmpty()) {
            if (file.isEmpty() && !isFileExtensionAllowed(file)) {
                return false;
            }
        }
        return file.isEmpty() || isFileExtensionAllowed(file);
    }

    private boolean isFileExtensionAllowed(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename.length()>3) {
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            for (String allowedExtension : allowedExtensions) {
                if (allowedExtension.equalsIgnoreCase(fileExtension)) {
                    return true;
                }
            }
        }
        return false;
    }
}
