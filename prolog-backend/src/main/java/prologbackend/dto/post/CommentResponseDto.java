package prologbackend.dto.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private String nickname;
    private String content;
    private LocalDateTime createAt;

    public CommentResponseDto(String nickname, String content, LocalDateTime createAt) {
        this.nickname = nickname;
        this.content = content;
        this.createAt = createAt;
    }
}
