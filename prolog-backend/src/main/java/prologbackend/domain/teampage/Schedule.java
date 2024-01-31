package prologbackend.domain.teampage;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import prologbackend.domain.teamrelationship.TeamRelationship;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "schedule_uuid", columnDefinition = "BINARY(16)")
    private UUID scheduleUuid;

    //순환참조 방지 -> Schedule entity를 조회할 때는 관련 Teampage entity를 가져오지 않음
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "teampage_uuid")
    private Teampage teampage;


    @Column(name = "schedule_content", columnDefinition = "LONGTEXT")
    private String scheduleContent;


    @Column(name = "schedule_start")
    private LocalDateTime scheduleStart;

    @Column(name = "schedule_end")
    private LocalDateTime scheduleEnd;

}
