package ivan.kravets.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

public class PostDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate createdDate = LocalDate.now();
    private UserDTO user;
    private Set<TagDTO> tags;
    private Set<CommentDTO> comments;
    private Set<MarkDTO> marks;
}
