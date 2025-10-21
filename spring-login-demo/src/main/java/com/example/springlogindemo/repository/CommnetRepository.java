package com.example.springlogindemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springlogindemo.entity.Comment;

public interface CommnetRepository extends JpaRepository<Comment , Long> {
	List<Comment> findByPostId(Long postId); //특성 게시글의 댓글 조회

}
