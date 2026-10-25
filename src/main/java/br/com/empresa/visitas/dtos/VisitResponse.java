package br.com.empresa.visitas.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record VisitResponse(
    Long id, String clientId, String nomeFantasia, String cnpj,
    LocalDate dataVisita, String bairro, String cidade,
    Integer estoqueInformado, String produtosJson, Integer estoqueAvarias,
    String avariasJson, String observacoes, String criadoPor, LocalDateTime criadoEm
) {}
