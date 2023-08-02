package com.example.server.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.entity.User;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	
	public User join(User user) {
		validateUser(user);
		return userRepository.save(user);
	}

	public void validateUser(User user) {
		User findUser = userRepository.findByUsername(user.getUsername());
		if(findUser != null) {
			log.debug("********** UserService => loadUserByUsername " + findUser.getUsername()+ " **********");
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}
	
}
