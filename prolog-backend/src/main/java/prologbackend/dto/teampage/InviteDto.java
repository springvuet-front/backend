package prologbackend.dto.teampage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InviteDto {
    @Getter
    @Setter
    public static class Invitee {
        private String nickname;
        private String role;
        private String admin;
    }

    private List<Invitee> invitees;
}
