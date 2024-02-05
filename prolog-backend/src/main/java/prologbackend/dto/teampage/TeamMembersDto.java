package prologbackend.dto.teampage;

import lombok.Getter;

@Getter
public class TeamMembersDto {
    private String nickname;
    private String teamRole;
    private String teamAdmin;

    public TeamMembersDto(String nickname, String teamRole, String teamAdmin) {
        this.nickname = nickname;
        this.teamRole = teamRole;
        this.teamAdmin = teamAdmin;
    }
}
