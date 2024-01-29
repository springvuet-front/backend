package prologbackend.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import prologbackend.domain.post.Comment;
import prologbackend.domain.post.Post;
import prologbackend.dto.post.CommentDto;
import prologbackend.dto.post.PostRequestDto;
import prologbackend.service.PostServiceImpl;

import java.util.UUID;

@Lazy
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostServiceImpl postServiceImpl;

    public PostController(PostServiceImpl postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }

    //게시글 작성
    @PostMapping("/post/create")
    public ResponseEntity<Post> createPost(@RequestBody PostRequestDto postRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Post newPost = postServiceImpl.createPost(postRequestDto, email);
        return ResponseEntity.ok(newPost);
    }

    //게시글 수정
    @PutMapping("/post/{postUuid}/edit")
    public ResponseEntity<Post> updatePost
    (@PathVariable UUID postUuid, @RequestBody PostRequestDto postRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Post updatePost = postServiceImpl.updatePost(postUuid, postRequestDto, email);
        return ResponseEntity.ok(updatePost);
    }

    //댓글 작성
    @PostMapping("/post/{postUuid}/comment/create")
    public ResponseEntity<Comment> createComment
    (@PathVariable UUID postUuid, @RequestBody CommentDto commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Comment newComment = postServiceImpl.createComment(postUuid, commentDto, email);
        return ResponseEntity.ok(newComment);
    }

    //댓글 수정
    @PutMapping("/post/{postUuid}/{commentUuid}/comment/edit")
    public ResponseEntity<Comment> updateComment
    (@PathVariable UUID postUuid, @PathVariable UUID commentUuid, @RequestBody CommentDto commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Comment updateComment = postServiceImpl.updateComment(commentUuid, commentDto, email);
        return ResponseEntity.ok(updateComment);
    }
}
