package prologbackend.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import prologbackend.domain.user.Member;

@Getter
@Setter
@NoArgsConstructor
public class JoinDto {
//    @NotBlank(message = "이메일 아이디를 입력하세요")
    private String email;

//    @NotBlank(message = "비밀번호를 입력하세요")
    private String userPw;
    private String userPwCheck;

//    @NotBlank(message = "닉네임을 입력하세요")
    private String nickname;

    //dto -> entity
    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .userPw(this.userPw)
                .nickname(this.nickname)
                .build();
    }

}
