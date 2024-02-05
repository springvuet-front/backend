package prologbackend.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prologbackend.dto.post.PostResponseDto;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    //게시글 작성 날짜 순 정렬
    @Query("SELECT new prologbackend.dto.post.PostResponseDto(m.nickname,p.postCategory,p.postStatus,p.postTitle,p.postContent,p.createAt,p.postUuid) " +
            "FROM Post p JOIN p.member m " +
            "ORDER BY p.createAt DESC ")
    List<PostResponseDto> findPostByDesc();

//    //게시글 category 별 정렬
//    @Query("SELECT new prologbackend.dto.post.PostListDto(m.nickname,p.postCategory,p.postStatus,p.postTitle,p.postContent,p.createAt) " +
//            "FROM Post p JOIN p.member m " +
//            "WHERE p.postCategory = :postCategory ORDER BY p.createAt DESC ")
//    List<PostListDto> findPostByPostCategory(@Param("postCategory") String postCategory);
//
//    //게시글 status별 정렬
//    @Query("SELECT new prologbackend.dto.post.PostListDto(m.nickname,p.postCategory,p.postStatus,p.postTitle,p.postContent,p.createAt) " +
//            "FROM Post p JOIN p.member m " +
//            "WHERE p.postStatus = :postStatus ORDER BY p.createAt DESC ")
//    List<PostListDto> findPostByPostStatus(@Param("postStatus") String postStatus);

    //게시글 + 게시글에 해당하는 댓글
    @Query("SELECT new prologbackend.dto.post.PostResponseDto(m.nickname,p.postCategory,p.postStatus,p.postTitle,p.postContent,p.createAt,p.postUuid) " +
            "FROM Post p JOIN p.member m " +
            "WHERE p.postUuid = :postUuid ")
    PostResponseDto findPost(@Param("postUuid") UUID PostUuid);

}

