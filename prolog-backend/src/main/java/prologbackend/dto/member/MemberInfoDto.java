package prologbackend.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prologbackend.domain.member.Member;
import prologbackend.domain.member.MemberRole;
@Getter
@Setter
@NoArgsConstructor
public class MemberInfoDto {
    private String email;
    private String nickname;
    private MemberRole role;

    public MemberInfoDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.role = member.getRole();
    }
}
