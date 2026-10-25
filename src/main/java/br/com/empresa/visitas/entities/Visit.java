package br.com.empresa.visitas.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitas", uniqueConstraints = @UniqueConstraint(name = "uk_visita_client_id", columnNames = "client_id"))
public class Visit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false, unique = true, length = 100)
    private String clientId;
    @Column(name = "nome_fantasia", nullable = false, length = 180)
    private String nomeFantasia;
    @Column(length = 30) private String cnpj;
    private LocalDate dataVisita;
    @Column(length = 120) private String bairro;
    @Column(length = 120) private String cidade;
    private Integer estoqueInformado = 0;
    @Lob @Column(columnDefinition = "LONGTEXT") private String produtosJson;
    private Integer estoqueAvarias = 0;
    @Lob @Column(columnDefinition = "LONGTEXT") private String avariasJson;
    @Column(columnDefinition = "TEXT") private String observacoes;
    @Column(length = 120) private String criadoPor;
    @Column(nullable = false, updatable = false) private LocalDateTime criadoEm;

    @PrePersist
    void prePersist() {
        if (criadoEm == null) criadoEm = LocalDateTime.now();
        if (estoqueInformado == null) estoqueInformado = 0;
        if (estoqueAvarias == null) estoqueAvarias = 0;
    }

    public Long getId() { return id; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public LocalDate getDataVisita() { return dataVisita; }
    public void setDataVisita(LocalDate dataVisita) { this.dataVisita = dataVisita; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public Integer getEstoqueInformado() { return estoqueInformado; }
    public void setEstoqueInformado(Integer estoqueInformado) { this.estoqueInformado = estoqueInformado; }
    public String getProdutosJson() { return produtosJson; }
    public void setProdutosJson(String produtosJson) { this.produtosJson = produtosJson; }
    public Integer getEstoqueAvarias() { return estoqueAvarias; }
    public void setEstoqueAvarias(Integer estoqueAvarias) { this.estoqueAvarias = estoqueAvarias; }
    public String getAvariasJson() { return avariasJson; }
    public void setAvariasJson(String avariasJson) { this.avariasJson = avariasJson; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public String getCriadoPor() { return criadoPor; }
    public void setCriadoPor(String criadoPor) { this.criadoPor = criadoPor; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
