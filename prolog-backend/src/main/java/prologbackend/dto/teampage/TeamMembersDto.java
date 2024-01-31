package prologbackend.dto.teampage;

import lombok.Getter;

@Getter
public class TeamMembersDto {
    private String nickname;
    private String teamRole;

    public TeamMembersDto(String nickname, String teamRole) {
        this.nickname = nickname;
        this.teamRole = teamRole;
    }
}
