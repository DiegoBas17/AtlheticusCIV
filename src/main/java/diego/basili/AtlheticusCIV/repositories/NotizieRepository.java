package diego.basili.AtlheticusCIV.repositories;

import diego.basili.AtlheticusCIV.entities.Notizia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotizieRepository extends JpaRepository<Notizia, UUID> {
}
