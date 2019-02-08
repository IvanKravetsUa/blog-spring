package ivan.kravets.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "user_details")
public class UserDetailsEntity extends BaseEntity {


    @Column(name = "date_of_birth", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "marital_status", nullable = false)
    private String maritalStatus;

    @Column(nullable = false)
    private String hobby;

    @Column(name = "about_me", nullable = false)
    private String aboutMe;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
