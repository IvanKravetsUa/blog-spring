package ivan.kravets.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class UserDTO {

    private Long id;

    @NotNull(message = "Field 'firstName' can't be null")
    @Size(min = 2, message = "FirstName should be min 2")
    private String firstName;

    @NotNull(message = "Field 'lastName' can't be null")
    @Size(min = 2, message = "LastName should be min 2")
    private String lastName;

    @NotNull
    @Size(min = 3, message = "Email should be min 2")
    private String email;

    @NotNull(message = "Field 'password' can't be null")
    @Size(min = 6, message = "Password should be min 6")
    private String password;

    @NotNull(message = "Field 'passwordConfirm' can't be null")
    @Size(min = 6, message = "PasswordConfirm should be min 6")
    private String passwordConfirm;

    @NotNull(message = "Field 'sex' can't be null")
    private String sex;

    @NotNull(message = "Field 'reputation' can't be null")
    private Long reputation;

    private LocalDate accountCreatedDate = LocalDate.now();
}
