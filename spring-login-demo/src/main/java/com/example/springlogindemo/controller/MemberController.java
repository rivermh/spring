package com.example.springlogindemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springlogindemo.entity.Member;
import com.example.springlogindemo.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
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
	public String loginForm() {
		return "login";
	}
	
	//로그인 처리
	@PostMapping("/login")
	public String login(@RequestParam String userId, @RequestParam String password, HttpSession session, Model model) {
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
	
	//마이페이지
	@GetMapping("/mypage")
	public String myPage(HttpSession session, Model model) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(loginMember == null) {
			return "redirect:/login";
		}
		model.addAttribute("member", loginMember);
		return "mypage"; 
	}
	
	//마이페이지 수정 폼
	@GetMapping("/mypage/edit")
	public String editForm(HttpSession session, Model model) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(loginMember == null) {
			return "redirect:/login";
		}
		model.addAttribute("member", loginMember);
		return "mypage-edit";
	}
	
	//마이페이지 수정 처리
	public String updateProfile(@RequestParam String username, HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(loginMember == null) {
			return "redirect:/login";
		}
		memberService.updateMember(loginMember.getId(), username);
		
		//세션 정보도 같이 갱신
		loginMember.setUsername(username);
		session.setAttribute("loginMember", loginMember);
		
		return "redirect:/mypage";
	}
	
}