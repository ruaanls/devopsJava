package br.com.fiap.globalSolution.DTO;

import jakarta.validation.constraints.NotBlank;

public class MotoResponseDTO
{
    private String placa;
    private String modelo;
    private int ano;
    private String cor;
    private Long idVaga;
    private String status;
    private String linha;
    private String coluna;

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

    public Long getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(Long idVaga) {
        this.idVaga = idVaga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public String getColuna() {
        return coluna;
    }

    public void setColuna(String coluna) {
        this.coluna = coluna;
    }
}
