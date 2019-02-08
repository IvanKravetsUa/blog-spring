package ivan.kravets.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "mark")
public class MarkEntity extends BaseEntity {

    @Column(name = "mark_status", nullable = false)
    private Long markStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
