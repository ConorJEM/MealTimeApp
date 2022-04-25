package sta.cs5031p3.mealtimetinder.backend.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sta.cs5031p3.mealtimetinder.backend.service.ImageFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ImageFileServiceImpl implements ImageFileService {

    /**
     * Customized folder for image files.
     */
    @Value("#{'${spring.web.resources.static-locations}'.substring(5)}")
    private String imageFileLocation;

    @Override
    public String upload(MultipartFile file, String to) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String relativePath = "/" + to + "/" + fileName;
        File image = new File(imageFileLocation + relativePath);
        file.transferTo(image);
        return relativePath;
    }

    @Override
    public boolean validateImagePath(String path) {
        return Files.exists(Path.of(imageFileLocation + path));
    }


}
