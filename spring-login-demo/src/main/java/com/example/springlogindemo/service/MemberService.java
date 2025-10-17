package com.example.springlogindemo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springlogindemo.entity.Member;
import com.example.springlogindemo.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	
	// 회원가입 (중복 체크 포함)
	@Transactional // 회원 저장은 readOnly = false
	public Member signUp(Member member) {
		Member existingMember = memberRepository.findByUserId(member.getUserId()).orElse(null);
		
		if(existingMember != null) {
			throw new IllegalStateException("이미 존재하는 아이디입니다.");
		}
		return memberRepository.save(member);
	}
	
	//로그인
	public Member login(String userId, String password) {
		Member member = memberRepository.findByUserId(userId).orElse(null);
		
		if(member != null && member.getPassword().equals(password)) {
			return member;
		}
		return null;
	}
	
	// 단순 조회용
	public Member findByUserId(String userId) {
		return memberRepository.findByUserId(userId).orElse(null);
	}
	
	//@Transactional
	public void updateMember(Long id, String username) {
		Member member = memberRepository.findById(id).orElseThrow();
		member.setUsername(username);
	}
 
}