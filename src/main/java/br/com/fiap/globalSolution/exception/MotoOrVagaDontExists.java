package br.com.fiap.globalSolution.exception;

public class MotoOrVagaDontExists extends RuntimeException {
    public MotoOrVagaDontExists(String message) {
        super(message);
    }
}
