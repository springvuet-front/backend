package prologbackend.service;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import prologbackend.domain.member.Member;
import prologbackend.domain.member.MemberRepository;
import prologbackend.domain.member.MemberRole;
import prologbackend.dto.TokenDto;
import prologbackend.dto.member.JoinDto;
import prologbackend.dto.member.LoginDto;
import prologbackend.dto.member.MemberInfoDto;
import prologbackend.jwt.TokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManagerBuilder managerBuilder;
    private final TokenProvider tokenProvider;
    public MemberServiceImpl
            (MemberRepository memberRepository, @Qualifier("passwordEncoder") BCryptPasswordEncoder encoder,
             AuthenticationManagerBuilder managerBuilder, TokenProvider tokenProvider)

    {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
        this.managerBuilder = managerBuilder;
        this.tokenProvider = tokenProvider;
    }

    //boolean으로 return해서 결과 확인 -> t/f 결과에 따라서 작업하기 -> controller에서 t/f 에 따라 가입여부 처리
    @Override
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public Member signUp(JoinDto joinDto) {
        if (memberRepository.existsByEmail(joinDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member newMember =  Member.builder()
                .email(joinDto.getEmail())
                .nickname(joinDto.getNickname())
                .memberPw(encoder.encode(joinDto.getMemberPw()))
                .role(MemberRole.USER) // 기본 권한은 USER로 설정
                .build();
        return memberRepository.save(newMember);
    }

    //로그인
    @Override
    public TokenDto login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }

    //member 정보 확인
    @Override
    public MemberInfoDto findMemberInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Authentication authentication = tokenProvider.getAuthentication(token);
        String email = authentication.getName();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        return new MemberInfoDto(member);

    }





}
