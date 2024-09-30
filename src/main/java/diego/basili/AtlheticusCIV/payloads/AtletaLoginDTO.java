package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AtletaLoginDTO(@NotEmpty(message = "L'email è obbligatoria")
                             @Email(message = "L'email non valida!")
                             String email,
                             @NotEmpty(message = "La password è obbligatoria!")
                             @Size(min = 3, max = 40)
                             String password) {
}
