package diego.basili.AtlheticusCIV.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "atleti")
public class Atleta {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private String nome;
    private String cognome;
    private String email;
    private String numeroTelefono;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "valutazione_id", referencedColumnName = "id")
    private Valutazione valutazione;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistica_id", referencedColumnName = "id")
    private Statistica statistica;
    /*collegamento manytomany con partite*/
}
