package com.codingrecipe.member.service;

import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //여기서는 username이 이메일임
        MemberEntity memberEntity = memberRepository.findByMemberEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return User.builder()
                .username(memberEntity.getMemberEmail())
                .password(memberEntity.getMemberPassword())
                .authorities(Collections.emptyList()) // 사용자 권한 정보를 설정합니다.
                .build();
    }


}
