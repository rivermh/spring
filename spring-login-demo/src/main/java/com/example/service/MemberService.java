package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Member;
import com.example.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 조회 (userId 기준)
    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId).orElse(null);
    }

    // 회원 저장
    @Transactional
    public Member register(Member member) {
        return memberRepository.save(member);
    }
}