package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AtletaValoriDTO(
        @NotNull(message = "La media dei gol non può essere null")
        @Min(value = 0, message = "La media dei gol deve essere almeno 0")
        Double mediaGol,
        @NotNull(message = "La media degli assist non può essere null")
        @Min(value = 0, message = "La media degli assist deve essere almeno 0")
        Double mediaAssist,
        @NotNull(message = "La media dei voti non può essere null")
        @Min(value = 0, message = "La media dei voti deve essere almeno 0")
        @Max(value = 10, message = "La media dei voti non può superare 10")
        Double mediaVoti,
        @NotNull(message = "Il numero di partite giocate non può essere null")
        @Min(value = 0, message = "Il numero di partite giocate deve essere almeno 0")
        Long partiteGiocate,
        @NotNull(message = "Il totale dei gol non può essere null")
        @Min(value = 0, message = "Il totale dei gol deve essere almeno 0")
        Long totaleGol,
        @NotNull(message = "Il totale degli assist non può essere null")
        @Min(value = 0, message = "Il totale degli assist deve essere almeno 0")
        Long totaleAssist
) {
}
