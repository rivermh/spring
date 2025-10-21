package com.example.springlogindemo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springlogindemo.service.PostService;

import jakarta.servlet.http.HttpSession;

import com.example.springlogindemo.entity.Member;
import com.example.springlogindemo.entity.Post;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	
	// 게시글 목록
	@GetMapping("/posts")
	public String listPosts(@RequestParam(defaultValue = "0") int page, Model model) {
		PageRequest pageRequest = PageRequest.of(page, 5); //페이지당 5개
		Page<Post> postPage = postService.findAllPaged(pageRequest);
		
		model.addAttribute("postPage", postPage);
		return "post-list";	
	}
	
	// 게시글 작성 폼
	@GetMapping("/posts/new")
	public String newPostForm(Model model) {
		model.addAttribute("post", new Post());
		return "post-form"; 
	}
	
	// 게시글 작성 처리
	@PostMapping("/posts")
	public String createPost(@ModelAttribute Post post, HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(loginMember == null) {
			return "redirect:/login";
		}
		post.setMember(loginMember);
		postService.createPost(post);
		return "redirect:/posts";
	}
	
	// 게시글 상세 조회
	@GetMapping("/posts/{id}")
	public String viewPost(@PathVariable Long id, Model model) {
		Post post = postService.findById(id);
		model.addAttribute("post", post);
		return "post-detail";
	}
	
	// 게시글 수정 폼
	@GetMapping("/posts/{id}/edit")
	public String editPostForm(@PathVariable Long id, Model model, HttpSession session) {
		Post post = postService.findById(id);
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(!post.getMember().getId().equals(loginMember.getId())) {
			return "redirect:/posts";
		}
		model.addAttribute("post", post);
		return "post-form";
	}
	
	// 게시글 수정 처리
	@PostMapping("/posts/{id}/update")
	public String updatePost(@PathVariable Long id, @RequestParam String title, @RequestParam String content, HttpSession session) {
		Post post = postService.findById(id);
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(!post.getMember().getId().equals(loginMember.getId())) {
			return "redirect:/posts";
		}
		postService.updatePost(id, title, content);
		return "redirect:/posts/" + id;
	}
	
	// 게시글 삭제
	@PostMapping("/posts/{id}/delete")
	public String deletePost(@PathVariable Long id, HttpSession session) {
		Post post = postService.findById(id);
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(!post.getMember().getId().equals(loginMember.getId())) {
			return "redirect:/posts";
		}
		postService.deletePost(id);
		return "redirect:/posts";
	}
}
