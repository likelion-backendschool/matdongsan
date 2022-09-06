package com.matdongsan.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class LocalImageUtil implements ImageUtil {

    final private String fileDir =
            Paths.get(System.getProperty("user.dir"), "images").toString();

    @Override
    public String saveFiles(List<MultipartFile> files) {
        if(files == null) return null;
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String s = saveFile(file);
            if(s!=null) urls.add(s);
        }
        return String.join(",", urls);
    }

    @Override
    public String getFullPath(String fileName) {
        return fileDir +"/"+ fileName;
    }

    private String saveFile(MultipartFile file) {
        if(file.isEmpty()) return null;
        try {
            String origin = file.getOriginalFilename();
            String storeFileName = createStoreFileName(origin);
            file.transferTo(new File(getFullPath(storeFileName)));
            return storeFileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 에러 발생");
        }
    }

    private String createStoreFileName(String origin) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(origin);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
