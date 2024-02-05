package prologbackend.dto.post;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
public class PostResponseDto {
    private String nickname;
    private String postCategory;
    private String postStatus;
    private String postTitle;
    private String postContent;
    private LocalDateTime createAt;
    private UUID postUuid;

    public PostResponseDto(String nickname, String postCategory, String postStatus, String postTitle, String postContent, LocalDateTime createAt, UUID postUuid) {
        this.nickname = nickname;
        this.postCategory = postCategory;
        this.postStatus = postStatus;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.createAt = createAt;
        this.postUuid = postUuid;
    }

    public String getCreate() {
        if (createAt == null) {
            return null;
        }
        return createAt.format(DateTimeFormatter.ISO_DATE_TIME);

    }
}
