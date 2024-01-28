package prologbackend.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prologbackend.domain.member.Member;
import prologbackend.domain.member.MemberRepository;
import prologbackend.domain.post.Comment;
import prologbackend.domain.post.CommentRepository;
import prologbackend.domain.post.Post;
import prologbackend.domain.post.PostRepository;
import prologbackend.dto.post.CommentDto;
import prologbackend.dto.post.PostRequestDto;
import prologbackend.exception.CommentNotFoundException;
import prologbackend.exception.PostNotFoundException;
import prologbackend.exception.UnauthorizedException;
import prologbackend.jwt.TokenProvider;

import java.util.UUID;

@Service
@Transactional
public class PostServiceImpl {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final CommentRepository commentRepository;

    public PostServiceImpl
            (PostRepository postRepository, MemberRepository memberRepository, TokenProvider tokenProvider,CommentRepository commentRepository){
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
        this.commentRepository = commentRepository;
    }
    //게시글 작성
    public Post createPost(PostRequestDto postRequestDto, String token) {
        Post newPost = postRequestDto.toEntity();
        Authentication authentication = tokenProvider.getAuthentication(token);
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        newPost.setMember(member);

        return postRepository.save(newPost);

    }

    //게시글 수정
    public Post updatePost(UUID postUuid,PostRequestDto postRequestDto, String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        String email = authentication.getName();
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

    //댓글 작성
    public Comment createComment(UUID postUuid, CommentDto commentDto, String token) {
        Comment newComment = commentDto.toEntity();

        Authentication authentication = tokenProvider.getAuthentication(token);
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        Post post = postRepository.findById(postUuid)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));


        newComment.setMember(member);
        newComment.setPost(post);

        return commentRepository.save(newComment);
    }

    //댓글 수정
    public Comment updateComment(UUID commentUuid, CommentDto commentDto, String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        String email = authentication.getName();

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

