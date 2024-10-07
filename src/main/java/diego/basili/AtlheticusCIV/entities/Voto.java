package diego.basili.AtlheticusCIV.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "voti")
public class Voto {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private Long voto;

    @ManyToOne
    @JoinColumn(name = "atleta_id")
    private Atleta atleta;

    @ManyToOne
    @JoinColumn(name = "statistica_id")
    private Statistica statistica;

    public Voto(Long voto, Statistica statistica, Atleta atleta) {
        this.voto = voto;
        this.statistica = statistica;
        this.atleta = atleta;
    }
}
