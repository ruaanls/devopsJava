package br.com.fiap.globalSolution.entity;

public enum StatusVaga
{
    LIVRE("Vaga Livre"),
    OCUPADA("Vaga Ocupada");

    private String descricao;

    StatusVaga(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
