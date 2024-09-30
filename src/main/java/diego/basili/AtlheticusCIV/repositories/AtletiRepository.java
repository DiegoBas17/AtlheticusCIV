package diego.basili.AtlheticusCIV.repositories;

import diego.basili.AtlheticusCIV.entities.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AtletiRepository extends JpaRepository<Atleta, UUID> {
    boolean existsByEmail(String email);

    Optional<Atleta> findByEmail(String email);
}
