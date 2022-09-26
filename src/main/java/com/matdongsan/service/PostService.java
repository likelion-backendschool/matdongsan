package com.matdongsan.service;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.domain.post.Post;
import com.matdongsan.domain.post.PostRepository;
import com.matdongsan.domain.post.PostRepositoryImpl;
import com.matdongsan.domain.post.SearchType;
import com.matdongsan.web.controller.util.image.ImageUtil;
import com.matdongsan.web.dto.posts.PostCreateDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
public class PostService {

    private final PlaceService placeService;

    private final ImageUtil imageUtil;
    private final PostRepository postRepository;
    private final PostRepositoryImpl postRepositoryImpl;

    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findAllByPlace(Place place){
        return postRepository.findAllByPlace(place);
    }

    public Post savePost(Member member, PostCreateDto dto) {
        String imageUrls = imageUtil.saveFiles(dto.getImgFiles());
        Place place = placeService.findPlace(dto.getPlaceId());

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .privateStatus(dto.getPrivateStatus())
                .author(member)
                .imageUrls(imageUrls)
                .createdTime(LocalDateTime.now())
                .modifiedTime(null)
                .build();
        post.addPlace(place);

        return postRepository.save(post);
    }

    // 이미지 폴더에서 파일을 리스트로 받아오는 로직
    public List<MultipartFile> getImageList(String imageUrls) throws IOException {
        List<MultipartFile> imgList = new ArrayList<>();

        for (String imgName : imageUrls.split(",")){
            if (StringUtils.isEmpty(imgName)){
                continue;
            }
            File file = new File(imageUtil.getFullPath(imgName));
            imgList.add(getMultipartFile(file));
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
        postRepository.deleteById(id);
    }

    // 게시글 전체 조회를 페이징으로
    public Page<Post> getList(String keyword , int page , String searchType ,Pageable pageable) {

        searchType = searchType.toLowerCase();

        if (searchType.equals(SearchType.TITLE.getKey())) {
            return postRepository.searchTitle(keyword, pageable);
        } else if (searchType.equals(SearchType.CONTENT.getKey())) {
            return postRepository.searchContent(keyword, pageable);
        } else if (searchType.equals(SearchType.AUTHOR.getKey())) {
            return postRepository.searchAuthor(keyword, pageable);
        }

        return postRepository.findAll(pageable);
    }

    public List<Post> findTop5Post() {
        return postRepository.findPostTop5();
    }
}
