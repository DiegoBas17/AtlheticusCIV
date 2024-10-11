package diego.basili.AtlheticusCIV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import diego.basili.AtlheticusCIV.enums.StatoPrenotazione;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "prenotazione_partite")
public class PrenotazionePartita {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private StatoPrenotazione statoPrenotazione;

    @ManyToOne
    @JoinColumn(name = "atleta_id")
    private Atleta atleta;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "partita_id")
    private Partita partita;

    public PrenotazionePartita(Atleta atleta, Partita partita, StatoPrenotazione statoPrenotazione) {
        this.atleta = atleta;
        this.partita = partita;
        this.statoPrenotazione = statoPrenotazione;
    }
}
