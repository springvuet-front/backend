package prologbackend.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prologbackend.dto.post.CommentResponseDto;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("SELECT new prologbackend.dto.post.CommentResponseDto(m.nickname,c.content,c.createAt)" +
            "FROM Comment c JOIN c.post p JOIN  c.member m " +
            "WHERE p.postUuid = :postUuid " +
            "ORDER BY c.createAt ASC ")
    List<CommentResponseDto> findCommentByPost(@Param("postUuid") UUID postUuid);

}

