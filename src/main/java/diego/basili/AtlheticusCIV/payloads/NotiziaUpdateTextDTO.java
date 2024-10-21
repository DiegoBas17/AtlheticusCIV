package diego.basili.AtlheticusCIV.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record NotiziaUpdateTextDTO(@NotEmpty(message = "Il titolo è obbligatoria")
                                   @Size(min = 3, max = 200)
                                   String titolo,
                                   @NotEmpty(message = "Il testo è obbligatoria")
                                   @Size(min = 3)
                                   String testo,
                                   @NotEmpty(message = "Il autore è obbligatoria")
                                   @Size(min = 3, max = 200)
                                   String autore) {
}
