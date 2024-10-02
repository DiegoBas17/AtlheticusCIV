package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PrenotazionePartitaDTO(@NotEmpty(message = "Lo stato Prenotazione è obbligatorio!")
                                     @Size(min = 3, max = 20)
                                     String statoPrenotazione) {
}
