package ivan.kravets.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

public class PostDTO {

    private Long id;

    @NotNull(message = "Field 'title' can't be null")
    @Size(min = 2, message = "title should be min 2")
    private String title;

    @NotNull(message = "Field 'description' can't be null")
    @Size(min = 2, message = "description should be min 2")
    private String description;

    private LocalDate createdDate = LocalDate.now();

    @NotNull(message = "Field 'user' can't be null")
    private UserDTO user;

    @NotNull(message = "Field 'tags' can't be null")
    private Set<TagDTO> tags;

    private Set<CommentDTO> comments;

    private Set<MarkDTO> marks;
}
