package com.Project.CongNghePhanMem.Controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/image")
public class ImageController {
	private final String IMAGE_DIR = "src/main/resources/static/images/products";

    @GetMapping
    public ResponseEntity<Resource> downloadImage(@RequestParam("fname") String fileName) {
        try {
            Path imagePath = Paths.get(IMAGE_DIR).resolve(fileName);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
            } else {
                // Trả về ảnh mặc định nếu không tìm thấy
                Path defaultImage = Paths.get(IMAGE_DIR).resolve("no-image.png");
                resource = new UrlResource(defaultImage.toUri());
                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
