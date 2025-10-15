package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	
	// 로그인 시 아이디로 조회 (Unique ID가 있다면)
	Optional<Member> findByUserId(String userId);
	
	// 이메일 중복 체크 같은 용도로
	Optional<Member> findByEmail(String email);
}

// Member 엔티티 + PK 타입(Long)으로 JPA 기능 자동 제공

// findByUserId(String userId) SELECT * FROM member WHERE user_id = ? 자동 쿼리 생성

// Optional<Member> 값이 없을 수도 있으니 Optional로 감싸 Null 방지