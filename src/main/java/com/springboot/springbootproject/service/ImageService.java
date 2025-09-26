package com.springboot.springbootproject.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageService {

    private final Path uploadDir = Paths.get("uploads");

    public Resource loadImage(String filename) {
        try {
            Path filePath = uploadDir.resolve(filename).normalize();
            log.info("Resolved file path: {}", filePath);

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                log.warn("File not found: {}", filename);
                return null;
            }
        } catch (MalformedURLException e) {
            log.error("Invalid file path: {}", filename, e);
            return null;
        }
    }

    public String getContentType(String filename) {
        try {
            Path filePath = uploadDir.resolve(filename).normalize();
            String contentType = Files.probeContentType(filePath);
            return (contentType != null) ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE;
        } catch (IOException e) {
            log.error("Could not determine file type: {}", filename, e);
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }
}
