package prologbackend.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Post> createPost(@RequestBody PostRequestDto postRequestDto, @RequestHeader("Authorization") String token) {
        Post newPost = postServiceImpl.createPost(postRequestDto, token);
        return ResponseEntity.ok(newPost);
    }

    //게시글 수정
    @PutMapping("/post/{postUuid}/edit")
    public ResponseEntity<Post> updatePost
    (@PathVariable UUID postUuid, @RequestBody PostRequestDto postRequestDto, @RequestHeader("Authorization") String token) {
        Post updatePost = postServiceImpl.updatePost(postUuid, postRequestDto, token);
        return ResponseEntity.ok(updatePost);
    }

    //댓글 작성
    @PostMapping("/post/{postUuid}/comment/create")
    public ResponseEntity<Comment> createComment
    (@PathVariable UUID postUuid, @RequestBody CommentDto commentDto, @RequestHeader("Authorization") String token) {
        Comment newComment = postServiceImpl.createComment(postUuid, commentDto, token);
        return ResponseEntity.ok(newComment);
    }

    //댓글 수정
    @PutMapping("/post/{postUuid}/{commentUuid}/comment/edit")
    public ResponseEntity<Comment> updateComment
    (@PathVariable UUID postUuid, @PathVariable UUID commentUuid, @RequestBody CommentDto commentDto, @RequestHeader("Authorization") String token) {
        Comment updateComment = postServiceImpl.updateComment(commentUuid, commentDto, token);
        return ResponseEntity.ok(updateComment);
    }
}
