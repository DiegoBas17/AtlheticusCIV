package diego.basili.AtlheticusCIV.repositories;

import diego.basili.AtlheticusCIV.entities.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VotiRepository extends JpaRepository<Voto, UUID> {
}
