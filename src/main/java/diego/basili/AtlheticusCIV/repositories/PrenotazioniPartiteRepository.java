package diego.basili.AtlheticusCIV.repositories;

import diego.basili.AtlheticusCIV.entities.PrenotazionePartita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrenotazioniPartiteRepository extends JpaRepository<PrenotazionePartita, UUID> {
}
