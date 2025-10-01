package br.com.fiap.globalSolution.exception;

import org.springframework.http.HttpStatus;

public class ExceptionResponseDTO
{
    private HttpStatus status;
    private String message;

    public ExceptionResponseDTO(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ExceptionResponseDTO() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
