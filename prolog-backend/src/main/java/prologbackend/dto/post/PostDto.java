package prologbackend.dto.post;

import lombok.Getter;

import java.util.List;

@Getter
public class PostDto {
    private PostResponseDto postResponseDto;
    private List<CommentResponseDto> commentResponseDtoList;

    public PostDto(PostResponseDto postResponseDto, List<CommentResponseDto> commentResponseDtoList) {
        this.postResponseDto = postResponseDto;
        this.commentResponseDtoList = commentResponseDtoList;
    }
}
