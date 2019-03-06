package ivan.kravets.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {

    @NotNull
    @Size(min = 6, max = 30)
    private String email;

    @NotNull
    @Size(min = 6, max = 30)
    private String password;
}
