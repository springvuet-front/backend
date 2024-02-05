package prologbackend.dto.post;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CommentResponseDto {
    private String nickname;
    private String content;
    private LocalDateTime createAt;
    private UUID commentUuid;

    public CommentResponseDto(String nickname, String content, LocalDateTime createAt, UUID commentUuid) {
        this.nickname = nickname;
        this.content = content;
        this.createAt = createAt;
        this.commentUuid = commentUuid;
    }
}
