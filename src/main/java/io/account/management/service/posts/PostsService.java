package io.account.management.service.posts;

import io.account.management.domain.posts.Posts;
import io.account.management.domain.posts.PostsRepository;
import io.account.management.exception.PostsNotFoundException;
import io.account.management.web.dto.PostsResponseDto;
import io.account.management.web.dto.PostsSaveRequestDto;
import io.account.management.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.createPosts()).getId();
    }

    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new PostsNotFoundException("Can't find posts in database"));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return posts.getId();
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new PostsNotFoundException("Can't find posts in database"));
        return new PostsResponseDto(posts);
    }
}
