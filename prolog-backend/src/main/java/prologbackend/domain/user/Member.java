package prologbackend.domain.user;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_uuid", columnDefinition = "BINARY(16)")
    private UUID memberUuid;

    @Column(name = "member_email", unique = true)
    private String email;

    @Column(name = "member_pw")
    private String memeberPw;

    @Column(name = "member_nickname", unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private MemberRole role;

    @Builder
    public Member(String email, String memberPw, String nickname, MemberRole role) {
        this.email = email;
        this.memeberPw = memberPw;
        this.nickname = nickname;
        this.role = role;
    }

}
