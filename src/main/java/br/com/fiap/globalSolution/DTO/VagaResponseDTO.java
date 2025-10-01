package br.com.fiap.globalSolution.DTO;

import br.com.fiap.globalSolution.entity.Motos;
import br.com.fiap.globalSolution.entity.StatusVaga;

public class VagaResponseDTO
{
    private Long id;
    private String posicao; // A1, B2, etc.
    private StatusVaga status;  // livre, ocupada
    private String placa;
    private String modelo;
    private int ano;
    private String cor;
    private String statusMoto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public StatusVaga getStatus() {
        return status;
    }

    public void setStatus(StatusVaga status) {
        this.status = status;
    }

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

    public String getStatusMoto() {
        return statusMoto;
    }

    public void setStatusMoto(String statusMoto) {
        this.statusMoto = statusMoto;
    }
}
