package br.com.fiap.globalSolution.DTO;

import java.util.List;

public class LinhaResponseDTO
{
    private String linha;
    private List<VagaResponseDTO> vagas;
    private int vagasLivres;

    public String getLinha() {
        return linha;
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    public List<VagaResponseDTO> getVagas() {
        return vagas;
    }

    public void setVagas(List<VagaResponseDTO> vagas) {
        this.vagas = vagas;
    }

    public int getVagasLivres() {
        return vagasLivres;
    }

    public void setVagasLivres(int vagasLivres) {
        this.vagasLivres = vagasLivres;
    }
}
