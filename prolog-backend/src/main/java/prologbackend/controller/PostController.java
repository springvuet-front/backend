package prologbackend.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prologbackend.domain.post.Post;
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

    @PostMapping("/post/create")
    public ResponseEntity<Post> createPost(@RequestBody PostRequestDto postRequestDto, @RequestHeader("Authorization") String token) {
        Post newPost = postServiceImpl.createPost(postRequestDto, token);
        return ResponseEntity.ok(newPost);
    }

    @PutMapping("/post/{postUuid}/edit")
    public ResponseEntity<Post> updatePost
            (@PathVariable UUID postUuid, @RequestBody PostRequestDto postRequestDto, @RequestHeader("Authorization") String token) {
        Post updatePost = postServiceImpl.updatePost(postUuid, postRequestDto, token);
        return ResponseEntity.ok(updatePost);
    }
}
