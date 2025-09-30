package com.springboot.springbootproject.service;

import org.springframework.core.io.Resource;

public interface ImageService {
    Resource loadImage(String filename);

    String getContentType(String filename);
}
