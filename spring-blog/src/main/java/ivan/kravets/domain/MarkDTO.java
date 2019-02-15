package ivan.kravets.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor

public class MarkDTO {

    private Long id;

    @NotNull(message = "Field 'markStatus' can't be null")
    private Long markStatus;

    @NotNull(message = "Field 'user' can't be null")
    private UserDTO user;
}
