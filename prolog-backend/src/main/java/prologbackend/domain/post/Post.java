package prologbackend.domain.post;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import prologbackend.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_uuid", columnDefinition = "BINARY(16)")
    private UUID postUuid;

    @ManyToOne
    @JoinColumn(name = "member_uuid")
    private Member member;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_category")
    private String postCategory;

    @Column(name = "post_status")
    private String postStatus;

    @Column(name = "post_content",columnDefinition = "LONGTEXT")
    private String postContent;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt = LocalDateTime.now();



    public void update(String postTitle, String postCategory, String postStatus, String postContent) {
        this.postTitle = postTitle;
        this.postCategory = postCategory;
        this.postStatus = postStatus;
        this.postContent = postContent;
    }

}
