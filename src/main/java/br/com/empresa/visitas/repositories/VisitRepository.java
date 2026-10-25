package br.com.empresa.visitas.repositories;

import br.com.empresa.visitas.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findByClientId(String clientId);
    boolean existsByClientId(String clientId);
}
