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
    @JoinColumn(name = "valutazione_id")
    private Valutazione valutazione;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistica_id")
    private Statistica statistica;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "atleta_partita",
            joinColumns = @JoinColumn(name = "atleta_id"),
            inverseJoinColumns = @JoinColumn(name = "partita_id")
    )
    private List<Partita> partite;
}
