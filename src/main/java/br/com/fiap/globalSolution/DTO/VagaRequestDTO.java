package br.com.fiap.globalSolution.DTO;

import br.com.fiap.globalSolution.entity.StatusVaga;

import jakarta.validation.constraints.NotBlank;

public class VagaRequestDTO
{
    @NotBlank(message = "A linha em que a vaga estará é obrigatória")
    private String linha;
    @NotBlank(message = "A coluna em que a vaga estará é obrigatória")
    private String coluna;
    //private StatusVaga statusVaga;

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
