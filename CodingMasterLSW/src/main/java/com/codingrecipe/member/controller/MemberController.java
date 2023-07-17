package com.codingrecipe.member.controller;


import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.EmailService;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;
    private final EmailService emailService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")

    public String save(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {

        String userEmail =  memberDTO.getMemberEmail();
        // 회원가입 폼에서 사용자가 입력한 정보가 MemberDTO 객체에 저장
        System.out.println("MemberController.save");
        System.out.println("memberEmail = " + memberDTO.getMemberEmail());

        String sessionVerificationCode = (String) session.getAttribute("verificationCode");
        String userEnteredVerificationCode = memberDTO.getVerificationCode();

        if (sessionVerificationCode != null && sessionVerificationCode.equals(userEnteredVerificationCode)) {
            memberService.save(memberDTO);
            return "redirect:/";   // 회원가입 완료 후 메인 페이지로 리다이렉트
        } else {
            model.addAttribute("errorMessage", "인증 코드가 일치하지 않습니다.");
            return "save";  // 인증 코드가 일치하지 않으면 다시 회원 가입 페이지로 리다이렉트
        }




}

    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) { //MemberDTO 객체 생성 후 login 메서드로 전달
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            //login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/member/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        //어떠한 html로 가져갈 데이터가 있다면 model을 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";

    }
    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";

    }
    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
       String myEmail = (String) session.getAttribute("loginEmail");   // 강제 형변환
       MemberDTO memberDTO = memberService.updateForm(myEmail);
       model.addAttribute("updateMember", memberDTO);
       return "update";
    }

    @PostMapping("/member/update")   //redirect 해주는 역할
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();

    }
    @GetMapping ("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id); // 목록상에서 삭제
        return "redirect:/member/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "index";
    }
    @GetMapping("/email-verification")
    public String sendVerificationCode(@RequestParam("email") String email, HttpSession session) {
        String verificationCode = emailService.generateVerificationCode();
        emailService.sendSimpleMessage(email, "Your verification code", "Your verification code is: " + verificationCode);
        // Store the verification code in the user's session
        session.setAttribute("verificationCode", verificationCode);
        return "save";
    }



}

