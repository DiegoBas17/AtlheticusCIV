package diego.basili.AtlheticusCIV.entities;

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
    private Long partiteGiocate;
    private Long gol;
    private Long mediaGol;

    @OneToOne(mappedBy = "statistica")
    private Atleta atleta;

    @OneToMany(mappedBy = "statistica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voto> voti;

    @OneToMany(mappedBy = "statistica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tracker> trackers;

}
