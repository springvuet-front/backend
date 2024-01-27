package prologbackend.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import prologbackend.domain.member.Member;
import prologbackend.dto.TokenDto;
import prologbackend.dto.member.JoinDto;
import prologbackend.dto.member.LoginDto;
import prologbackend.dto.member.MemberInfoDto;
import prologbackend.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Lazy
@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //회원가입
    @PostMapping("/auth/signup")
    public ResponseEntity<Member> userJoin(@Valid @RequestBody JoinDto joinDto, BindingResult bindingResult) {
        try {
            //아이디 중복체크
            if (memberService.checkEmailDuplicate(joinDto.getEmail())) {
                bindingResult.addError(new FieldError("joinRequest", "email", "중복된 이메일입니다"));
            }

            Member newMember = memberService.signUp(joinDto);

            return ResponseEntity.ok(newMember);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    //로그인
    @PostMapping("/auth/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(memberService.login(loginDto));
    }

    //유저정보 확인하기
    @GetMapping("/me")
    public ResponseEntity<MemberInfoDto> findMemberInfo(HttpServletRequest request) {
        return ResponseEntity.ok(memberService.findMemberInfo(request));

    }
}
