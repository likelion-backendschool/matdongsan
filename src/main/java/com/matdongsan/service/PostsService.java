package com.matdongsan.service;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.web.dto.posts.PostsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    public Posts findById(Long id) {
        return postsRepository.findById(id).get();
    }

    public List<Posts> findAll() {
        return postsRepository.findAll();
    }

    public Posts savePost(String title , String content , boolean status , Member author) {

        Posts posts = new Posts();
        posts.setTitle(title);
        posts.setContent(content);
        posts.setPrivateStatus(status);
        posts.setAuthor(author);
        posts.setCreatedTime(LocalDateTime.now());

        return postsRepository.save(posts);

    }

    public Posts update(Posts posts , String title , String content , Member author , boolean status) {

        posts.setTitle(title);
        posts.setContent(content);
        posts.setAuthor(author);
        posts.setPrivateStatus(status);

        return postsRepository.save(posts);

    }

    public void delete(Posts posts) {
        postsRepository.delete(posts);
    }
}
