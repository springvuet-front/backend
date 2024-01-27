package prologbackend.dto.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prologbackend.domain.post.Post;

@Getter
@Setter
@NoArgsConstructor

public class PostRequestDto {
    private String postTitle;
    private String postCategory;
    private String postStatus;
    private String postContent;

    public Post toEntity() {
        return Post.builder()
                .postTitle(this.postTitle)
                .postCategory(this.postCategory)
                .postStatus(this.postStatus)
                .postContent(this.postContent)
                .build();
    }
}
