package com.wcs.mtgbox.files.domain.service;

import com.wcs.mtgbox.files.domain.entity.Media;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    void init();

    Media store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    String cleanFileName(String originalFileName);
}