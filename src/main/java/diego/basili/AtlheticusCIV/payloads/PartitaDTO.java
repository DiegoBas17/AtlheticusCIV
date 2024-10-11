package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PartitaDTO(LocalDateTime data,
                         @NotEmpty(message = "Il luogo è obbligatoria")
                         @Size(min = 3)
                         String luogo,
                         @NotEmpty(message = "Il tipoPartita è obbligatoria")
                         @Size(min = 3, max = 10)
                         String tipoPartita) {
}
