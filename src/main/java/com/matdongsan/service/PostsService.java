package com.matdongsan.service;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.util.ImageUtil;
import com.matdongsan.web.dto.posts.PostCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
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

    /*public void delete(Posts posts) {
        postsRepository.delete(posts);
    }*/
}
