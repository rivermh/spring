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
//@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	// 회원가입 폼 페이지 이동
	@GetMapping("/signup")
	public String signupForm() {
		return "signup"; // templates/signup.html
	}

	// 회원가입 처리
//	@PostMapping("/signup")
	//public String signup(@RequestParam String userId, @RequestParam String username, @RequestParam String password,
	//		HttpSession session, Model model) {

	/*	Member member = memberService.login(userId, password);

		if (member != null) {
			session.setAttribute("loginUser", member); // 세션 저장
			return "redirect:/"; // 홈 또는 마이페이지
		} else {
			model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
			return "login";
		} */
	}
}
