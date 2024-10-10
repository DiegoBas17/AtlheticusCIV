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
    private int difesa;
    private int velocità;
    private int resistenza;
    private int tiro;
    private int tecnica;

    @JsonIgnore
    @OneToOne(mappedBy = "valutazioneAdmin", cascade = CascadeType.ALL)
    private Atleta atletaAdmin;

    @JsonIgnore
    @OneToOne(mappedBy = "valutazioneCIV", cascade = CascadeType.ALL)
    private Atleta atletaCiv;


    public Valutazione(int difesa, int velocità, int resistenza, int tiro, int tecnica, Atleta atleta) {
        this.difesa = difesa;
        this.velocità = velocità;
        this.resistenza = resistenza;
        this.tiro = tiro;
        this.tecnica = tecnica;
        this.atletaAdmin = atleta;
    }
}