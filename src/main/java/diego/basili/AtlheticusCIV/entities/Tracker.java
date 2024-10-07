package diego.basili.AtlheticusCIV.entities;

import diego.basili.AtlheticusCIV.enums.TipoPartita;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "trackers")
public class Tracker {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private TipoPartita tipoPartita;
    private Double attività;
    private Double distanza;
    private Long passaggi;
    private String corsa;
    private Long numeroSprint;
    private Double sprintMedio;
    private Double sprintMassimo;
    private String possesso;
    private Long tiri;
    private Double tiroMassimo;
    private Double tiroMedio;
    private Double valutazione5Stelle;
    private String analisiAllenatore;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistica_id")
    private Statistica statistica;

    public Tracker(TipoPartita tipoPartita, Double attività, Double distanza, Long passaggi, String corsa, Long numeroSprint, Double sprintMedio, Double sprintMassimo, String possesso, Long tiri, Double tiroMassimo, Double tiroMedio, Double valutazione5Stelle, String analisiAllenatore, Statistica statistica) {
        this.tipoPartita = tipoPartita;
        this.attività = attività;
        this.distanza = distanza;
        this.passaggi = passaggi;
        this.corsa = corsa;
        this.numeroSprint = numeroSprint;
        this.sprintMedio = sprintMedio;
        this.sprintMassimo = sprintMassimo;
        this.possesso = possesso;
        this.tiri = tiri;
        this.tiroMassimo = tiroMassimo;
        this.tiroMedio = tiroMedio;
        this.valutazione5Stelle = valutazione5Stelle;
        this.analisiAllenatore = analisiAllenatore;
        this.statistica = statistica;
    }
}
