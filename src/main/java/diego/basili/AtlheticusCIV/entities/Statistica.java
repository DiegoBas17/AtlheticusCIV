package diego.basili.AtlheticusCIV.entities;

import diego.basili.AtlheticusCIV.enums.TipoPartita;
import jakarta.persistence.*;
import lombok.*;

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

}
