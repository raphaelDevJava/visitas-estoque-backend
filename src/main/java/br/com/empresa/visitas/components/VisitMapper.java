package br.com.empresa.visitas.components;

import br.com.empresa.visitas.dtos.VisitCreateRequest;
import br.com.empresa.visitas.dtos.VisitResponse;
import br.com.empresa.visitas.entities.Visit;
import org.springframework.stereotype.Component;

@Component
public class VisitMapper {
    public Visit toEntity(VisitCreateRequest request) {
        Visit v = new Visit();
        apply(request, v);
        return v;
    }
    public void apply(VisitCreateRequest request, Visit v) {
        v.setClientId(request.clientId());
        v.setNomeFantasia(request.nomeFantasia().trim());
        v.setCnpj(request.cnpj());
        v.setDataVisita(request.dataVisita());
        v.setBairro(request.bairro());
        v.setCidade(request.cidade());
        v.setEstoqueInformado(request.estoqueInformado() == null ? 0 : request.estoqueInformado());
        v.setProdutosJson(request.produtosJson());
        v.setEstoqueAvarias(request.estoqueAvarias() == null ? 0 : request.estoqueAvarias());
        v.setAvariasJson(request.avariasJson());
        v.setObservacoes(request.observacoes());
    }
    public VisitResponse toResponse(Visit v) {
        return new VisitResponse(v.getId(), v.getClientId(), v.getNomeFantasia(), v.getCnpj(),
            v.getDataVisita(), v.getBairro(), v.getCidade(), v.getEstoqueInformado(),
            v.getProdutosJson(), v.getEstoqueAvarias(), v.getAvariasJson(), v.getObservacoes(),
            v.getCriadoPor(), v.getCriadoEm());
    }
}
