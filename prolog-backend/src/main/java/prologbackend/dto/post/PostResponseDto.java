package prologbackend.dto.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String nickname;
    private String postCategory;
    private String postStatus;
    private String postTitle;
    private String postContent;
    private LocalDateTime createAt;

    public PostResponseDto(String nickname, String postCategory, String postStatus, String postTitle, String postContent, LocalDateTime createAt) {
        this.nickname = nickname;
        this.postCategory = postCategory;
        this.postStatus = postStatus;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.createAt = createAt;
    }
}
