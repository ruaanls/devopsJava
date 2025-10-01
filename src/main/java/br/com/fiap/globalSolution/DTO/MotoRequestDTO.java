package br.com.fiap.globalSolution.DTO;

import jakarta.validation.constraints.NotBlank;

public class MotoRequestDTO
{
    @NotBlank(message = "A placa é obrigatório")
    private String placa;
    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    private int ano;
    @NotBlank(message = "A cor é obrigatória")
    private String cor;


    @NotBlank(message = "O status da moto é obrigatório")
    private String status;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }





    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
