package prologbackend.service;


import org.springframework.context.annotation.Lazy;
import prologbackend.domain.user.User;
import prologbackend.dto.user.JoinDto;

@Lazy
public interface UserService {
    //email id 중복체크
    boolean checkEmailDuplicate(String email);

    //회원가입
    User signUp(JoinDto joinDto);
}
