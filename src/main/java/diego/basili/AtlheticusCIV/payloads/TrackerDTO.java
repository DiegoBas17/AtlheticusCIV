package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TrackerDTO(@NotEmpty(message = "Il tipo Partita è obbligatorio!")
                         @Size(min = 3)
                         String tipoPartita,
                         @NotEmpty(message = "L'attività è obbligatoria!")
                         @Size(min = 3)
                         String attività,
                         @NotEmpty(message = "La distanza è obbligatoria!")
                         @Size(min = 3)
                         String distanza,
                         @NotNull(message = "Il valore dei passaggi è obbligatorio")
                         int passaggi,
                         @NotEmpty(message = "La corsa è obbligatoria!")
                         @Size(min = 3)
                         String corsa,
                         @NotNull(message = "Il valore del numeroSprint è obbligatorio")
                         int numeroSprint,
                         @NotEmpty(message = "Lo sprintMedio è obbligatorio!")
                         @Size(min = 3)
                         String sprintMedio,
                         @NotEmpty(message = "Lo sprintMassimo è obbligatorio!")
                         @Size(min = 3)
                         String sprintMassimo,
                         @NotEmpty(message = "Il possesso è obbligatorio!")
                         @Size(min = 3)
                         String possesso,
                         @NotNull(message = "Il valore dei tiri è obbligatorio")
                         int tiri,
                         @NotEmpty(message = "Il tiroMassimo è obbligatorio!")
                         @Size(min = 3)
                         String tiroMassimo,
                         @NotEmpty(message = "Il tiroMedio è obbligatorio!")
                         @Size(min = 3)
                         String tiroMedio,
                         @NotEmpty(message = "La valutazione a 5 stelle è obbligatorio!")
                         @Size(min = 1)
                         String valutazione5Stelle,
                         @NotEmpty(message = "L'analisiAllenatore è obbligatorio!")
                         @Size(min = 3)
                         String analisiAllenatore) {
}
