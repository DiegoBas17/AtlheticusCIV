package diego.basili.AtlheticusCIV.repositories;

import diego.basili.AtlheticusCIV.entities.Statistica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatisticheRepository extends JpaRepository<Statistica, UUID> {
}
