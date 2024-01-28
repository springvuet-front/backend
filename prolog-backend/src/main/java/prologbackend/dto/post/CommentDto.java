package prologbackend.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prologbackend.domain.post.Comment;
@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}
