package prologbackend.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import prologbackend.domain.post.Comment;
import prologbackend.domain.post.Post;
import prologbackend.dto.post.CommentDto;
import prologbackend.dto.post.PostDto;
import prologbackend.dto.post.PostResponseDto;
import prologbackend.dto.post.PostRequestDto;
import prologbackend.service.PostService;

import java.util.List;
import java.util.UUID;

@Lazy
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시글 작성
    @PostMapping("/post/create")
    public ResponseEntity<Post> createPost(@RequestBody PostRequestDto postRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Post newPost = postService.createPost(postRequestDto, email);
        return ResponseEntity.ok(newPost);
    }

    //게시글 수정
    @PutMapping("/post/{postUuid}/edit")
    public ResponseEntity<Post> updatePost
    (@PathVariable UUID postUuid, @RequestBody PostRequestDto postRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Post updatePost = postService.updatePost(postUuid, postRequestDto, email);
        return ResponseEntity.ok(updatePost);
    }

    //게시글 조회_최신순
    @GetMapping("/post")
    public ResponseEntity<List<PostResponseDto>> findPostByDesc() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<PostResponseDto> postResponseDtos = postService.findPostByDesc(email);
        return ResponseEntity.ok(postResponseDtos);
    }

//    //게시글 조회_status -> 모집중, 모집완료
//    @GetMapping("/post/{postStatus}")
//    public ResponseEntity<List<PostListDto>> findPostByPostStatue(@PathVariable String postStatus) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        List<PostListDto> postListDtos = postServiceImpl.findPostByPostStatus(postStatus, email);
//        return ResponseEntity.ok(postListDtos);
//    }
//    //게시글 조회_category -> 앱,웹
//    @GetMapping("/post/{postCategory}")
//    public ResponseEntity<List<PostListDto>> findPostByPostCategory(@PathVariable String postCategory) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        List<PostListDto> postListDtos = postServiceImpl.findPostByPostCategory(postCategory, email);
//        return ResponseEntity.ok(postListDtos);
//    }

    //특정 게시글 조회+댓글 리스트업
    @GetMapping("/post/{postUuid}")
    public ResponseEntity<PostDto> postAndComments(@PathVariable UUID postUuid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        PostDto postDto = postService.postAndComments(postUuid, email);
        return ResponseEntity.ok(postDto);
    }
    //댓글 작성
    @PostMapping("/post/{postUuid}/comment/create")
    public ResponseEntity<Comment> createComment
    (@PathVariable UUID postUuid, @RequestBody CommentDto commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Comment newComment = postService.createComment(postUuid, commentDto, email);
        return ResponseEntity.ok(newComment);
    }

    //댓글 수정
    @PutMapping("/post/{postUuid}/{commentUuid}/comment/edit")
    public ResponseEntity<Comment> updateComment
    (@PathVariable UUID postUuid, @PathVariable UUID commentUuid, @RequestBody CommentDto commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Comment updateComment = postService.updateComment(commentUuid, commentDto, email);
        return ResponseEntity.ok(updateComment);
    }
}
