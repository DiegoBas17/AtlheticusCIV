package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AtletaDTO(@NotEmpty(message = "Il nome è obbligatorio!")
                        @Size(min = 3, max = 20)
                        String nome,
                        @NotEmpty(message = "Il cognome è obbligatorio!")
                        @Size(min = 3, max = 20)
                        String cognome,
                        @NotEmpty(message = "L'email è obbligatoria")
                        @Email(message = "Email non valida!")
                        String email,
                        @NotEmpty(message = "Il numeroDiCellulare è obbligatorio!")
                        @Size(min = 10, max = 10)
                        String numeroDiCellulare,
                        @NotEmpty(message = "La password è obbligatoria!")
                        @Size(min = 3, max = 40)
                        String password) {
}
