package diego.basili.AtlheticusCIV.entities;

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
    private StatoPrenotazione statoPrenotazione;

    @ManyToOne
    @JoinColumn(name = "atleta_id")
    private Atleta atleta;

    @ManyToOne
    @JoinColumn(name = "partita_id")
    private Partita partita;
}
