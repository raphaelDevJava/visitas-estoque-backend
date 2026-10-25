package br.com.empresa.visitas.contracts;

import br.com.empresa.visitas.dtos.VisitCreateRequest;
import br.com.empresa.visitas.dtos.VisitResponse;
import java.util.List;

public interface VisitService {
    List<VisitResponse> listar();
    VisitResponse buscarPorId(Long id);
    VisitResponse criar(VisitCreateRequest request, String usuario);
    VisitResponse atualizar(Long id, VisitCreateRequest request, String usuario);
    void excluir(Long id);
}
