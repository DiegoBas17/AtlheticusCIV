package diego.basili.AtlheticusCIV.entities;

import diego.basili.AtlheticusCIV.enums.TipoPartita;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "partite")
public class Partita {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDate data;
    private String luogo;
    @Enumerated(EnumType.STRING)
    private TipoPartita tipoPartita;

    @OneToMany(mappedBy = "partita", cascade = CascadeType.ALL)
    private List<PrenotazionePartita> prenotazioniPartite;

    @OneToMany(mappedBy = "partita", cascade = CascadeType.ALL)
    private List<Statistica> statistiche;
}
