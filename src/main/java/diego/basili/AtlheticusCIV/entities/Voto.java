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
    @JoinColumn(name = "partita_id")
    private Partita partita;

    @ManyToOne
    @JoinColumn(name = "statistica_id")
    private Statistica statistica;
}
