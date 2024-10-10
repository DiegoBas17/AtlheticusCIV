package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RuoliInCampoDTO(@NotEmpty(message = "Il ruolo primario non può essere vuoto")
                              @Size(min = 3, max = 30)
                              String ruoloInCampoPrimario,
                              @NotEmpty(message = "Il ruolo secondario non può essere vuoto")
                              @Size(min = 3, max = 30)
                              String ruoloInCampoSecondario,
                              @NotEmpty(message = "Il ruolo alternativo non può essere vuoto")
                              @Size(min = 3, max = 30)
                              String ruoloInCampoAlternativo) {
}
