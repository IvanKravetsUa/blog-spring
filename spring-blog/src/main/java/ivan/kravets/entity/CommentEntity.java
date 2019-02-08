package ivan.kravets.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Column(nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "comment_mark",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id")
    )
    private Set<MarkEntity> marks = new HashSet<>();
}
