package ivan.kravets.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MarkDTO {

    private Long id;
    private Long markStatus;
    private UserDTO user;
}
