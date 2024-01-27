package prologbackend.service;


import org.springframework.context.annotation.Lazy;
import prologbackend.domain.member.Member;
import prologbackend.dto.TokenDto;
import prologbackend.dto.member.JoinDto;
import prologbackend.dto.member.LoginDto;
import prologbackend.dto.member.MemberInfoDto;

import javax.servlet.http.HttpServletRequest;

@Lazy
public interface MemberService {
    //email id 중복체크
    boolean checkEmailDuplicate(String email);

    //회원가입
    Member signUp(JoinDto joinDto);

    //로그인
    TokenDto login(LoginDto loginDto);

    //유저정보
    MemberInfoDto findMemberInfo(HttpServletRequest request);
}
