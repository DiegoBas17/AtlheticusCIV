package diego.basili.AtlheticusCIV.entities;

import diego.basili.AtlheticusCIV.enums.ColoreSquadra;
import diego.basili.AtlheticusCIV.enums.TipoPartita;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "statistiche")
public class Statistica {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private TipoPartita tipoPartita;
    private ColoreSquadra coloreSquadra;
    private Long gol;
    private Long assist;

    @ManyToOne
    @JoinColumn(name = "partita_id")
    private Partita partita;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tracker_id")
    private Tracker tracker;

    @ManyToOne
    @JoinColumn(name = "atleta_id")
    private Atleta atleta;

    @OneToMany(mappedBy = "statistica", cascade = CascadeType.ALL)
    private List<Voto> voti;

    public Statistica(TipoPartita tipoPartita, ColoreSquadra coloreSquadra, Long gol, Long assist, Partita partita, Atleta atleta) {
        this.tipoPartita = tipoPartita;
        this.coloreSquadra = coloreSquadra;
        this.gol = gol;
        this.assist = assist;
        this.partita = partita;
        this.tracker = null;
        this.atleta = atleta;
        this.voti = new ArrayList<>();
    }
}
