package ivan.kravets.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor

public class CommentDTO {

    private Long id;
    private String body;
    private UserDTO user;
    private Set<MarkDTO> marks;
}
