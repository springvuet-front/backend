package prologbackend.service;


import org.springframework.context.annotation.Lazy;
import prologbackend.domain.user.Member;
import prologbackend.dto.TokenDto;
import prologbackend.dto.member.JoinDto;
import prologbackend.dto.member.LoginDto;

@Lazy
public interface MemberService {
    //email id 중복체크
    boolean checkEmailDuplicate(String email);

    //회원가입
    Member signUp(JoinDto joinDto);

    //로그인
    TokenDto login(LoginDto loginDto);
}
