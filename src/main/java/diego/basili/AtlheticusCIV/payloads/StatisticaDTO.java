package diego.basili.AtlheticusCIV.payloads;

import diego.basili.AtlheticusCIV.enums.TipoPartita;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StatisticaDTO(@NotEmpty(message = "Lo stato Prenotazione è obbligatorio!")
                            @Size(min = 3, max = 20)
                            String tipoPartita,
                            @NotEmpty(message = "Lo stato Prenotazione è obbligatorio!")
                            @Size(min = 3, max = 20)
                            String coloreSquadra,
                            @NotNull(message = "Il valore dei gol è obbligatorio")
                            Long gol,
                            @NotNull(message = "Il valore dei assist è obbligatorio")
                            Long assist) {
}
