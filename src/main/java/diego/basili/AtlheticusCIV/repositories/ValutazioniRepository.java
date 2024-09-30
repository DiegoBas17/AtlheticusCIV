package diego.basili.AtlheticusCIV.repositories;

import diego.basili.AtlheticusCIV.entities.Valutazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ValutazioniRepository extends JpaRepository<Valutazione, UUID> {
}
