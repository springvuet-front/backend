package prologbackend.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_uuid", columnDefinition = "BINARY(16)")
    private UUID userUuid;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "user_nickname", unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Builder
    public User(String email, String userPw, String nickname, UserRole role) {
        this.email = email;
        this.userPw = userPw;
        this.nickname = nickname;
        this.role = role;
    }

}
