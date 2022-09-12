package com.matdongsan.service;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.util.ImageUtil;
import com.matdongsan.web.dto.posts.PostCreateDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final PlaceService placeService;

    private final ImageUtil imageUtil;
    private final PostsRepository postsRepository;
    public Posts findById(Long id) {
        return postsRepository.findById(id).get();
    }

    public List<Posts> findAll() {
        return postsRepository.findAll();
    }

    public Posts savePost(Member member, PostCreateDto dto) {
        String imageUrls = imageUtil.saveFiles(dto.getImgFiles());
        Place place = placeService.findPlace(dto.getPlaceId());

        Posts posts = Posts.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .privateStatus(dto.getPrivateStatus())
                .author(member)
                .imageUrls(imageUrls)
                .createdTime(LocalDateTime.now())
                .modifiedTime(null)
                .build();
        posts.addPlace(place);

        return postsRepository.save(posts);
    }

    // 이미지 폴더에서 파일을 리스트로 받아오는 로직
    public List<MultipartFile> getImageList(String imageUrls) throws IOException {
        List<MultipartFile> imgList = new ArrayList<>();

        // 해당 File의 URL을 바꿔줘야 함 !
        File file = new File("/Users/bc/Desktop/BC/CODE_LION/project/matdongsan/images");
        File listFiles[] = file.listFiles();

        for (File listFile : listFiles) {
            imgList.add(getMultipartFile(listFile));
        }
        log.info("리스트 생성 완료");

        return imgList;

    }

    // File 객체를 MultipartFile로 변경하는 로직
    private MultipartFile getMultipartFile(File file) throws IOException {

        log.info("변환 완료");
        FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());

        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);

        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

    public void delete(Long id) {
        postsRepository.deleteById(id);
    }

}
