package com.matdongsan.web.controller;


import com.matdongsan.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    final private ImageUtil imageUtil;

    @GetMapping(value = "/images/{filename}",produces = "image/*")
    public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {
        log.info("fullPath = {}", imageUtil.getFullPath(filename));
        return new UrlResource("file:" + imageUtil.getFullPath(filename));
    }
}
