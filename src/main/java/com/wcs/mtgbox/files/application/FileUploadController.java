package com.wcs.mtgbox.files.application;

import com.wcs.mtgbox.files.domain.entity.Media;
import com.wcs.mtgbox.files.domain.service.IStorageService;
import com.wcs.mtgbox.files.infrastructure.exception.StorageFileNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Tag(name = "File management", description = "API to handle user's files and media")
@Controller
public class FileUploadController {

    private final IStorageService storageService;

    @Autowired
    public FileUploadController(IStorageService storageService) {
        this.storageService = storageService;
    }
    @PostMapping("/api/v1/upload")
    @Operation(summary = "Upload a file", description = "Allows a user to upload a file to the server")

    public ResponseEntity<Media> handleFileUpload(@RequestParam("file") MultipartFile file) {
        Media media = storageService.store(file);

        return ResponseEntity.status(201).body(media);
    }
    @GetMapping("/files")
    @Operation(summary = "Serve a file", description = "Allows a user to download a file from the server")
    @ResponseBody
    public ResponseEntity<Resource> serveFile() {

        Resource file = storageService.loadAsResource("maison.png");

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
