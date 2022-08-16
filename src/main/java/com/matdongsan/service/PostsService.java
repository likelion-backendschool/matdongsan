package com.matdongsan.service;

import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    public Posts findById(Long id) {
        return postsRepository.findById(id).orElse(null);
    }

    public List<Posts> findAll() {
        return postsRepository.findAll();
    }

    public Posts create(Posts post) {
        return postsRepository.save(post);
    }
}
