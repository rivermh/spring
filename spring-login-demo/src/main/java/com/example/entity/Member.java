package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity // 이 클래스를 JPA가 관리하는 테이블로 쓰겠다는 선언
@Table(name = "member") // DB 테이블명을 명시적으로 지정
@Getter @Setter // 필드 자동 Getter / Setter 생성, 코드 간결
public class Member {

	@Id //Primary Key 지정(기본키 지정)
	@GeneratedValue(strategy = GenerationType.IDENTITY) // MySql AUTO_INCREMENT
	private Long id;
	
	@Column(unique = true, nullable = false) //username 중복 방지 (SQL: UNIQUE 제약 조건)
	private String username; // 로그인 아이디 (중복 방지)
	
	@Column(nullable = false)
	private String password;
}
 