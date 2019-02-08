package ivan.kravets.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "reputation", nullable = false)
    private Long reputation;

    @Column(name = "account_created_date", nullable = false)
    private LocalDate accountCreatedDate = LocalDate.now();


}
