package com.matdongsan.util.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUtil {

    /**
     * 구현체에 따라 저장되는 Url 이 달라지도록 했습니다.
     * LocalImageUtil의 경우 root 경로의 /images 디렉토리에 이미지가 저장됩니다.
     * 만약 AWS에 저장하려면 URL만 바꾸면 될듯.
     */
    String saveFiles(List<MultipartFile> files);

    /**
     * 업로드 파일 제목 그대로 올리기 보다 UUID로 저장하기 위한 메서드
     */
    String getFullPath(String fileName);
}