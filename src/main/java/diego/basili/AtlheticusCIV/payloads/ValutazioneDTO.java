package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ValutazioneDTO(@NotEmpty(message = "Il valore della difesa è obbligatorio!")
                             @Size(min = 3, max = 20)
                             String difesa,
                             @NotEmpty(message = "Il valore della velocità è obbligatorio!")
                             @Size(min = 3, max = 20)
                             String velocità,
                             @NotEmpty(message = "Il valore della resistenza è obbligatorio!")
                             @Size(min = 3, max = 20)
                             String resistenza,
                             @NotEmpty(message = "Il valore del tiro è obbligatorio!")
                             @Size(min = 3, max = 20)
                             String tiro,
                             @NotEmpty(message = "Il valore della tecnica è obbligatorio!")
                             @Size(min = 3, max = 20)
                             String tecnica) {
}
