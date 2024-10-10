package diego.basili.AtlheticusCIV.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "notizie")
public class Notizia {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private String immagine;
    private String titolo;
    private String testo;
    private LocalDateTime dataCreazione;
    private String autore;

    public Notizia(String titolo, String testo, LocalDateTime dataCreazione, String autore) {
        this.titolo = titolo;
        this.testo = testo;
        this.dataCreazione = dataCreazione;
        this.autore = autore;
        this.immagine = null;
    }
}
