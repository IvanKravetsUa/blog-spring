package ivan.kravets.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

public class TagDTO {

    private Long id;

    @NotNull(message = "Field 'name' can't be null")
    @Size(min = 2, message = "name should be min 2")
    private String name;
}
