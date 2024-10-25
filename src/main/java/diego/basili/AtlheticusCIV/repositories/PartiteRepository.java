package diego.basili.AtlheticusCIV.repositories;

import diego.basili.AtlheticusCIV.entities.Partita;
import diego.basili.AtlheticusCIV.enums.TipoPartita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface PartiteRepository extends JpaRepository<Partita, UUID> {
    boolean existsByDataAndTipoPartita(LocalDateTime data, TipoPartita tipoPartita);
}
