package com.matdongsan.service;

import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.web.dto.posts.PostsDto;
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

    public Long savePost(PostsDto postsDto) {

        return postsRepository.save(postsDto.toEntity()).getId();

    }

    public Posts update(Long id , PostsDto postsDto) {
        Posts post= postsDto.toEntity();
        Posts target = postsRepository.findById(id).orElse(null);

        if (target == null) {
            return null;
        }

        target.update(post);
        return postsRepository.save(target);

    }

    public Posts delete(Long id) {
        Posts post = postsRepository.findById(id).orElse(null);

        if (post == null) {
            return null;
        }

        postsRepository.delete(post);
        return post;
    }
}
