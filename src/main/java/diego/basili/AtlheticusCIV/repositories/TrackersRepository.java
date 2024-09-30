package diego.basili.AtlheticusCIV.repositories;

import diego.basili.AtlheticusCIV.entities.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrackersRepository extends JpaRepository<Tracker, UUID> {
}
