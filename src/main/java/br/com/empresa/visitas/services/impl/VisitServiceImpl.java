package br.com.empresa.visitas.services.impl;

import br.com.empresa.visitas.components.VisitMapper;
import br.com.empresa.visitas.contracts.VisitService;
import br.com.empresa.visitas.dtos.VisitCreateRequest;
import br.com.empresa.visitas.dtos.VisitResponse;
import br.com.empresa.visitas.entities.Visit;
import br.com.empresa.visitas.infrastructure.ClientIdConflictException;
import br.com.empresa.visitas.infrastructure.VisitNotFoundException;
import br.com.empresa.visitas.repositories.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {
    private final VisitRepository repository;
    private final VisitMapper mapper;
    public VisitServiceImpl(VisitRepository repository, VisitMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    @Override @Transactional(readOnly = true)
    public List<VisitResponse> listar() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
    @Override @Transactional(readOnly = true)
    public VisitResponse buscarPorId(Long id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new VisitNotFoundException(id)));
    }
    @Override
    public VisitResponse criar(VisitCreateRequest request, String usuario) {
        var existente = repository.findByClientId(request.clientId());
        if (existente.isPresent()) {
            // Idempotência: sincronizar novamente a mesma visita não cria duplicatas.
            return mapper.toResponse(existente.get());
        }
        Visit visit = mapper.toEntity(request);
        visit.setCriadoPor(usuario);
        return mapper.toResponse(repository.save(visit));
    }
    @Override
    public VisitResponse atualizar(Long id, VisitCreateRequest request, String usuario) {
        Visit visit = repository.findById(id).orElseThrow(() -> new VisitNotFoundException(id));
        if (!visit.getClientId().equals(request.clientId()) && repository.existsByClientId(request.clientId())) {
            throw new ClientIdConflictException(request.clientId());
        }
        mapper.apply(request, visit);
        visit.setCriadoPor(usuario);
        return mapper.toResponse(repository.save(visit));
    }
    @Override
    public void excluir(Long id) {
        Visit visit = repository.findById(id).orElseThrow(() -> new VisitNotFoundException(id));
        repository.delete(visit);
    }
}
