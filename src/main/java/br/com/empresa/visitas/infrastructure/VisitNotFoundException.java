package br.com.empresa.visitas.infrastructure;

public class VisitNotFoundException extends RuntimeException {
    public VisitNotFoundException(Long id) { super("Visita não encontrada para o ID " + id + "."); }
}
