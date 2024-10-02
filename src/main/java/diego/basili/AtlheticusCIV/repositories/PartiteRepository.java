package diego.basili.AtlheticusCIV.repositories;

import diego.basili.AtlheticusCIV.entities.Partita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PartiteRepository extends JpaRepository<Partita, UUID> {
    boolean existsByData(LocalDateTime data);
}
