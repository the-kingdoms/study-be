package com.codingrecipe.member.config;

import com.codingrecipe.member.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //test
                .csrf().disable()
                // 이거때문에 에러가 났음. 근데 뭔지 모르겠음.
                // CSRF 보호를 비활성화 라고 함.
                // CSRF 공부하기


                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") // ADMIN 역할을 가진 사용자만 접근 가능
                .anyRequest().permitAll() // 나머지 모든 요청에 대해 접근 허용
                .and()
                .formLogin()
                .loginPage("/member/login") // 로그인 페이지 경로 설정
                .permitAll() // 로그인 페이지는 모두에게 접근 가능
                .defaultSuccessUrl("/index", true) // 로그인 성공시 이동할 URL 설정
                .and()
                .logout()
                .permitAll(); // 로그아웃은 모두에게 접근 가능
    }

}





