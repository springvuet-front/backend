package prologbackend.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import prologbackend.domain.member.Member;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String memberPw;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .email(this.email)
                .memberPw(encodedPassword)
                .build();

    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, memberPw);
    }

}
