package br.com.empresa.visitas.controllers;

import br.com.empresa.visitas.contracts.VisitService;
import br.com.empresa.visitas.dtos.VisitCreateRequest;
import br.com.empresa.visitas.dtos.VisitResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/visitas")
public class VisitController {
    private final VisitService service;
    public VisitController(VisitService service) { this.service = service; }

    @GetMapping public ResponseEntity<List<VisitResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }
    @GetMapping("/{id}") public ResponseEntity<VisitResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping public ResponseEntity<VisitResponse> criar(@Valid @RequestBody VisitCreateRequest request, Authentication authentication) {
        String usuario = authentication == null ? "sistema" : authentication.getName();
        VisitResponse response = service.criar(request, usuario);
        return ResponseEntity.created(URI.create("/api/visitas/" + response.id())).body(response);
    }
    @PutMapping("/{id}") public ResponseEntity<VisitResponse> atualizar(@PathVariable Long id, @Valid @RequestBody VisitCreateRequest request, Authentication authentication) {
        String usuario = authentication == null ? "sistema" : authentication.getName();
        return ResponseEntity.ok(service.atualizar(id, request, usuario));
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
