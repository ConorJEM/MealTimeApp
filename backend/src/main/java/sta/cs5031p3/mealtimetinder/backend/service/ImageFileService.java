package sta.cs5031p3.mealtimetinder.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Provide services for files handling.
 * @author 200011181
 */

public interface ImageFileService {

    String DEFAULT_MEAL_PATH = "/meals/default.jpg";

    /**
     * Upload image to /<project-root-folder>/images/
     * @throws IOException
     */
    String upload(MultipartFile multipartFile, String to) throws IOException;

    /**
     * Check if file in this path exists.
     * @param path path of the file
     * @return true if the path is for a valid file, false otherwise.
     */
    boolean validateImagePath(String path);
}
