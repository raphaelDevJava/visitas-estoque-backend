package br.com.empresa.visitas.infrastructure;

public class ClientIdConflictException extends RuntimeException {
    public ClientIdConflictException(String clientId) { super("Já existe uma visita com o clientId " + clientId + "."); }
}
