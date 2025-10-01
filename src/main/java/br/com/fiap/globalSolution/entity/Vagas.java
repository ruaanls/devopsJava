package br.com.fiap.globalSolution.entity;

import jakarta.persistence.*;

@Entity
public class Vagas
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String linha;
    private String coluna;
    @Enumerated(value = EnumType.STRING)
    private StatusVaga statusVaga;

    @OneToOne(mappedBy = "vaga", fetch = FetchType.LAZY)
    private Motos moto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public StatusVaga getStatusVaga() {
        return statusVaga;
    }

    public void setStatusVaga(StatusVaga statusVaga) {
        this.statusVaga = statusVaga;
    }

    public Motos getMoto() {
        return moto;
    }

    public void setMoto(Motos moto) {
        this.moto = moto;
    }
}
