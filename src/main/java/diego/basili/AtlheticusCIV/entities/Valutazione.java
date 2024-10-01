package diego.basili.AtlheticusCIV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToOne(mappedBy = "valutazione")
    private Atleta atleta;

    public Valutazione(Double difesa, Double velocità, Double resistenza, Double tiro, Double tecnica) {
        this.difesa = difesa;
        this.velocità = velocità;
        this.resistenza = resistenza;
        this.tiro = tiro;
        this.tecnica = tecnica;
    }
}