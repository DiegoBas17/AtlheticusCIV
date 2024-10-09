package diego.basili.AtlheticusCIV.repositories;

import diego.basili.AtlheticusCIV.entities.Statistica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StatisticheRepository extends JpaRepository<Statistica, UUID> {
    List<Statistica> findByAtletaId(UUID atletaId);
}
