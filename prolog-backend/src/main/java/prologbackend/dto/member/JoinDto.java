package prologbackend.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prologbackend.domain.member.Member;

@Getter
@Setter
@NoArgsConstructor
public class JoinDto {
//    @NotBlank(message = "이메일 아이디를 입력하세요")
    private String email;

//    @NotBlank(message = "비밀번호를 입력하세요")
    private String memberPw;

//    @NotBlank(message = "닉네임을 입력하세요")
    private String nickname;

    //dto -> entity
    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .memberPw(this.memberPw)
                .nickname(this.nickname)
                .build();
    }

}
