package br.com.empresa.visitas.repositories;

import br.com.empresa.visitas.entities.Visit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class VisitRepositoryTest {
    @Autowired VisitRepository repository;
    @Test void persisteEConsultaPorClientId() {
        Visit v = new Visit();
        v.setClientId("client-test-001");
        v.setNomeFantasia("Mercado Teste");
        v.setCriadoPor("teste");
        repository.saveAndFlush(v);
        Optional<Visit> found = repository.findByClientId("client-test-001");
        assertTrue(found.isPresent());
        assertEquals("Mercado Teste", found.get().getNomeFantasia());
        assertNotNull(found.get().getCriadoEm());
    }
}
