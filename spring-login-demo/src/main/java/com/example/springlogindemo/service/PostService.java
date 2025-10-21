package com.example.springlogindemo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.springlogindemo.entity.Post;
import com.example.springlogindemo.repository.PostRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //전체 게시글 조회
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    //게시글 생성
    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }
    
    //게시글 업데이트
    @Transactional
    public void updatePost(Long postId, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + postId));
        post.setTitle(title);
        post.setContent(content);
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // 조회용 메서드 추가
    @Transactional(readOnly = true)
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + postId));
    }
    
    //페이징용 메서드
    @Transactional(readOnly = true)
    public Page<Post> findAllPaged(Pageable pageable){
    	return postRepository.findAll(pageable);
    }
}
