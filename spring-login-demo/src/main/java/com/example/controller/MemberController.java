package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Member;
import com.example.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	// 회원가입 폼 페이지 이동
	@GetMapping("/signup")
	public String signupForm() {
		return "signup"; // templates/signup.html
	}

	// 회원가입 처리
	@PostMapping("/signup")
	public String signup(@RequestParam String userId, @RequestParam String username, @RequestParam String password,
			Model model) {

		Member member = new Member();
		member.setUserId(userId);
		member.setUsername(username);
		member.setPassword(password);
		
		try {
			memberService.signUp(member);
		}catch (IllegalStateException e) {
			model.addAttribute("error", e.getMessage());
			return "signup";
		}
		return "redirect:/login";
	}
	
	//로그인 폼
	@GetMapping("/login")
	public String login(@RequestParam String userId,@RequestParam String password, HttpSession session, Model model) {
		Member loginMember = memberService.login(userId, password);
		
		if(loginMember == null) {
			model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
			return "login";
		}
		
		session.setAttribute("loginMember", loginMember);
		return "redirect:/home";
	}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	//홈
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		if(loginMember == null) {
			return "redirect:/login";
		}
		model.addAttribute("member", loginMember);
		return "home";
	}
}