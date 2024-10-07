package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.NotNull;

public record VotoDTO(@NotNull(message = "Il valore del voto è obbligatorio")
                      int voto) {
}
