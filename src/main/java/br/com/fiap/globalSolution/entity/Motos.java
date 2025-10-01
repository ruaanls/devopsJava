package br.com.fiap.globalSolution.entity;

import jakarta.persistence.*;

@Entity
public class Motos
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String placa;
    private String modelo;
    private int ano;
    private String cor;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id", nullable = true)
    private Vagas vaga;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Vagas getVaga() {
        return vaga;
    }

    public void setVaga(Vagas vaga) {
        this.vaga = vaga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
