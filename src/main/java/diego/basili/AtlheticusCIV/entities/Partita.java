package diego.basili.AtlheticusCIV.entities;

import diego.basili.AtlheticusCIV.enums.TipoPartita;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "partite")
public class Partita {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDate data;
    private String luogo;
    private TipoPartita tipoPartita;

    /*partita manytomany atleti*/
}
