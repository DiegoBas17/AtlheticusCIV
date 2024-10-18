package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.*;

public record ValutazioneDTO(@NotNull(message = "Il campo difesa è obbligatorio")
                             @Min(value = 0, message = "Il valore minimo per difesa è 0")
                             @Max(value = 100, message = "Il valore massimo per difesa è 100")
                             int difesa,
                             @NotNull(message = "Il campo velocità è obbligatorio")
                             @Min(value = 0, message = "Il valore minimo per velocità è 0")
                             @Max(value = 100, message = "Il valore massimo per velocità è 100")
                             int velocità,
                             @NotNull(message = "Il campo resistenza è obbligatorio")
                             @Min(value = 0, message = "Il valore minimo per resistenza è 0")
                             @Max(value = 100, message = "Il valore massimo per resistenza è 100")
                             int resistenza,
                             @NotNull(message = "Il campo tiro è obbligatorio")
                             @Min(value = 0, message = "Il valore minimo per tiro è 0")
                             @Max(value = 100, message = "Il valore massimo per tiro è 100")
                             int tiro,
                             @NotNull(message = "Il campo tecnica è obbligatorio")
                             @Min(value = 0, message = "Il valore minimo per tecnica è 0")
                             @Max(value = 100, message = "Il valore massimo per tecnica è 100")
                             int tecnica) {
}
