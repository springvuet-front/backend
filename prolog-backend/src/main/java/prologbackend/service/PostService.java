package prologbackend.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prologbackend.domain.member.Member;
import prologbackend.domain.member.MemberRepository;
import prologbackend.domain.post.Comment;
import prologbackend.domain.post.CommentRepository;
import prologbackend.domain.post.Post;
import prologbackend.domain.post.PostRepository;
import prologbackend.dto.post.*;
import prologbackend.exception.CommentNotFoundException;
import prologbackend.exception.PostNotFoundException;
import prologbackend.exception.UnauthorizedException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public PostService
            (PostRepository postRepository, MemberRepository memberRepository,CommentRepository commentRepository){
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }
    //게시글 작성
    public Post createPost(PostRequestDto postRequestDto, String email) {
        Post newPost = postRequestDto.toEntity();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        newPost.setMember(member);

        return postRepository.save(newPost);

    }

    //게시글 수정
    public Post updatePost(UUID postUuid,PostRequestDto postRequestDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        Post updatePost = postRepository.findById(postUuid)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        if (!updatePost.getMember().equals(member)) {
            throw new UnauthorizedException("You can only modify your own posts");
        }else{
            updatePost.update
                    (postRequestDto.getPostTitle(), postRequestDto.getPostCategory(), postRequestDto.getPostStatus(), postRequestDto.getPostCategory());
            return postRepository.save(updatePost);
        }
    }

    //게시글 삭제, 조회, 정렬..
    //게시글 조회_최신순
    public List<PostResponseDto> findPostByDesc(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

       List<PostResponseDto> postResponseDtos = postRepository.findPostByDesc();

     return postResponseDtos;
    }

    //
//    //게시글 조회_status -> 모집중,모집완료
//    public List<PostListDto> findPostByPostStatus(String postStatus, String email) {
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
//
//        List<PostListDto> postListDtos = postRepository.findPostByPostStatus(postStatus);
//
//        return postListDtos;
//    }
//    //게시글 조회_category -> 앱, 웹
//    public List<PostListDto> findPostByPostCategory(String postCategory, String email) {
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
//
//        List<PostListDto> postListDtos = postRepository.findPostByPostCategory(postCategory);
//
//        return postListDtos;
//    }
    //특정 게시글 조회 + 댓글 리스트업
    public PostDto postAndComments(UUID postUuid, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        PostResponseDto postResponseDto = postRepository.findPost(postUuid);
        List<CommentResponseDto> commentResponseDtoList = commentRepository.findCommentByPost(postUuid);

        return new PostDto(postResponseDto, commentResponseDtoList);
    }

    //댓글 작성
    public Comment createComment(UUID postUuid, CommentDto commentDto, String email) {
        Comment newComment = commentDto.toEntity();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        Post post = postRepository.findById(postUuid)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));


        newComment.setMember(member);
        newComment.setPost(post);

        return commentRepository.save(newComment);
    }

    //댓글 수정
    public Comment updateComment(UUID commentUuid, CommentDto commentDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        Comment updateComment = commentRepository.findById(commentUuid)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

        if (!updateComment.getMember().equals(member)) {
            throw new UnauthorizedException("You can only modify your own comments");
        }else{
            updateComment.update(commentDto.getContent());
            return commentRepository.save(updateComment);
        }

    }


}

