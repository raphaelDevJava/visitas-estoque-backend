package br.com.empresa.visitas.services;

import br.com.empresa.visitas.components.VisitMapper;
import br.com.empresa.visitas.dtos.VisitCreateRequest;
import br.com.empresa.visitas.dtos.VisitResponse;
import br.com.empresa.visitas.entities.Visit;
import br.com.empresa.visitas.infrastructure.VisitNotFoundException;
import br.com.empresa.visitas.repositories.VisitRepository;
import br.com.empresa.visitas.services.impl.VisitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceImplTest {
    @Mock VisitRepository repository;
    private VisitServiceImpl service;
    @BeforeEach void setup() { service = new VisitServiceImpl(repository, new VisitMapper()); }
    private VisitCreateRequest request(String clientId) {
        return new VisitCreateRequest(clientId, "Loja Teste", "", null, "Centro", "Maricá", 4, "{}", 0, "{}", "teste");
    }
    @Test void criaVisitaERegistraUsuario() {
        when(repository.existsByClientId("abc")).thenReturn(false);
        when(repository.save(any(Visit.class))).thenAnswer(inv -> {
            Visit v = inv.getArgument(0); return v;
        });
        VisitResponse result = service.criar(request("abc"), "supervisor");
        assertEquals("Loja Teste", result.nomeFantasia());
        assertEquals("supervisor", result.criadoPor());
        verify(repository).save(any(Visit.class));
    }
    @Test void repeticaoDoMesmoClientIdRetornaVisitaExistenteSemDuplicar() {
        Visit existente = new Visit();
        existente.setClientId("abc");
        existente.setNomeFantasia("Loja Teste");
        existente.setCriadoPor("supervisor");
        when(repository.findByClientId("abc")).thenReturn(Optional.of(existente));
        VisitResponse result = service.criar(request("abc"), "supervisor");
        assertEquals("abc", result.clientId());
        verify(repository, never()).save(any());
    }
    @Test void retornaErroQuandoVisitaNaoExiste() {
        when(repository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(VisitNotFoundException.class, () -> service.buscarPorId(999L));
    }
}
