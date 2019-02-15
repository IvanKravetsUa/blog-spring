package ivan.kravets.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor

public class UserDetailsDTO {

    private Long id;

    @NotNull(message = "Field 'dateOfBirth' can't be null")
    private Date dateOfBirth;

    @NotNull(message = "Field 'maritalStatus' can't be null")
    private String maritalStatus;

    @NotNull(message = "Field 'hobby' can't be null")
    @Size(min = 3, message = "hobby should be min 3")
    private String hobby;

    @NotNull(message = "Field 'aboutMe' can't be null")
    @Size(min = 10, message = "aboutMe should be min 10")
    private String aboutMe;

    @NotNull(message = "Field 'user' can't be null")
    private UserDTO user;
}
