package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository; // DB에 접근할 수 있게 하는 코드
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    public void save(MemberDTO memberDTO) {
        // repository의 save메서드 호출 ( 조건. entity객체를 넘겨줘야 함)
        // 1. dto -> entity 변환
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);

        //비밀번호 암호화
        memberEntity.setMemberPassword(passwordEncoder.encode(memberDTO.getMemberPassword()));
        memberRepository.save(memberEntity);

        // 2. repository의 save 메서드 호출

    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        //사용자가 입력한 이메일로 memberRepository를 통해 데이터베이스에서 회원 정보를 조회

        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get(); // 객체 가져오기


            //비밀번호 일치 확인
            if (passwordEncoder.matches(memberDTO.getMemberPassword(), memberEntity.getMemberPassword())) {
                //비밀번호 일치
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                // 비밀번호 불일치(로그인실패)
                return null;
            }
        } else {
            // 조회 결과가 없다 (해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

    public List<MemberDTO> findAll() { // MemberDTO 타입의 객체들을 담은 리스트를 반환
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        //Entity 리스트를 DTO 리스트로 바꿔줘야함
        for(MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
//            MemberDTO memberDTO = MemberDTO.toMemberDTO((memberEntity));
//            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get()); //Entity -> DTO 변환
        } else {
            return null;
        }
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get()); // 위에 3줄과 같은 코드
        } else {
            return null;
        }

    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
