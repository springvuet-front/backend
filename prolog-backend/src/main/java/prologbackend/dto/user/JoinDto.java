package prologbackend.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prologbackend.domain.user.User;
import prologbackend.domain.user.UserRole;

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
    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(this.email)
                .userPw(encodedPassword)
                .nickname(this.nickname)
                .role(UserRole.USER)
                .build();
    }
}
