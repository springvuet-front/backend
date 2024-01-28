package prologbackend.domain.post;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import prologbackend.domain.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_uuid", columnDefinition = "BINARY(16)")
    private UUID commentUuid;

    @ManyToOne
    @JoinColumn(name = "member_uuid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_uuid")
    private Post post;

    @Column(name = "c_parent_id", columnDefinition = "BINARY(16)")
    private UUID parentId;

    @Column(name = "c_content",columnDefinition = "LONGTEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt = LocalDateTime.now();

    public void update(String content) {
        this.content = content;
    }

}
