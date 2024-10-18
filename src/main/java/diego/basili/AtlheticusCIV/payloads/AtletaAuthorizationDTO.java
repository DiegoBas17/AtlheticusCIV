package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AtletaAuthorizationDTO(@NotEmpty(message = "Il ruolo è obbligatoria")
                                     @Size(min = 3, max = 20)
                                     String ruolo) {
}
