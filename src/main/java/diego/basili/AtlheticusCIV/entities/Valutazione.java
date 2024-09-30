package diego.basili.AtlheticusCIV.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "valutazioni")
public class Valutazione {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private Double difesa;
    private Double velocità;
    private Double resistenza;
    private Double tiro;
    private Double tecnica;

    @OneToOne(mappedBy = "valutazione")
    private Atleta atleta;
}