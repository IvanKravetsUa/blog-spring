package ivan.kravets.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

public class UserDetailsDTO {

    private Long id;
    private Date dateOfBirth;
    private String maritalStatus;
    private String hobby;
    private String aboutMe;
    private UserDTO user;
}
