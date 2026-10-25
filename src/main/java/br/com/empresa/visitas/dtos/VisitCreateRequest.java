package br.com.empresa.visitas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record VisitCreateRequest(
    @NotBlank @Size(max = 100) String clientId,
    @NotBlank @Size(max = 180) String nomeFantasia,
    @Size(max = 30) String cnpj,
    LocalDate dataVisita,
    @Size(max = 120) String bairro,
    @Size(max = 120) String cidade,
    Integer estoqueInformado,
    String produtosJson,
    Integer estoqueAvarias,
    String avariasJson,
    String observacoes
) {}
