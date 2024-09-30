package diego.basili.AtlheticusCIV.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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

    /*statistica onetoone atleta*/
    /*statistica onetomany voto*/
    /*statistica onetomany tracker*/

}
