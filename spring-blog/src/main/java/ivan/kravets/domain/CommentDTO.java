package ivan.kravets.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor

public class CommentDTO {

    private Long id;

    @NotNull(message = "Field 'body' can't be null")
    @Size(min = 3, message = "Body should be min 6")
    private String body;

    @NotNull(message = "Field 'user' can't be null")
    private UserDTO user;

    private Set<MarkDTO> marks;
}
