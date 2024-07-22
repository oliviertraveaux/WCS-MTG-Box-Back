package com.wcs.mtgbox.files.domain.service;


import com.wcs.mtgbox.files.application.StorageProperties;
import com.wcs.mtgbox.files.domain.entity.Media;
import com.wcs.mtgbox.files.infrastructure.exception.StorageException;
import com.wcs.mtgbox.files.infrastructure.exception.StorageFileNotFoundException;
import com.wcs.mtgbox.files.infrastructure.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements IStorageService {
    private final Path rootLocation;

    private final MediaRepository mediaRepository;

    @Autowired
    public FileSystemStorageService(StorageProperties properties, MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;

        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Media store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            String cleanedFileName = this.cleanFileName(file.getOriginalFilename());
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(cleanedFileName))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }

            // Créez et configurez l'objet Media avec le nom de fichier nettoyé, faire DTO
            Media media = new Media();
            media.setFileName(cleanedFileName);

            this.mediaRepository.save(media);

            return media;

        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public String cleanFileName(String originalFileName) {
        // Remplace les espaces et caractères non-alphanumériques (à l'exception des points) par des tirets
        return originalFileName.replaceAll("[^a-zA-Z0-9.]", "-");
    }
}