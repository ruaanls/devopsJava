package br.com.fiap.globalSolution.mapper;

import br.com.fiap.globalSolution.DTO.VagaRequestDTO;
import br.com.fiap.globalSolution.DTO.VagaResponseDTO;
import br.com.fiap.globalSolution.entity.StatusVaga;
import br.com.fiap.globalSolution.entity.Vagas;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VagaMapper
{
    public Vagas requestToVaga (VagaRequestDTO vagaRequestDTO)
    {
        Vagas vagas = new Vagas();
        vagas.setStatusVaga(StatusVaga.LIVRE);
        vagas.setColuna(vagaRequestDTO.getColuna());
        vagas.setLinha(vagaRequestDTO.getLinha());
        return vagas;
    }

    public void updateVagaFromRequest(VagaRequestDTO request, Vagas vaga) {

        if (request.getLinha() != null) {
            vaga.setLinha(request.getLinha());
        }
        if (request.getColuna() != null) {
            vaga.setColuna(request.getColuna());
        }


    }

    public List<VagaResponseDTO> vagaToResponse(List<Vagas> vagas)
    {
        List<VagaResponseDTO> vagasDTO = new ArrayList<>();
        for(Vagas vaga : vagas)
        {
            VagaResponseDTO vagaResponse = new VagaResponseDTO();
            vagaResponse.setId(vaga.getId());
            vagaResponse.setPosicao(vaga.getLinha()+ vaga.getColuna());
            vagaResponse.setStatus(vaga.getStatusVaga());
            if(vaga.getMoto() != null)
            {
                vagaResponse.setPlaca(vaga.getMoto().getPlaca());
                vagaResponse.setModelo(vaga.getMoto().getModelo());
                vagaResponse.setAno(vaga.getMoto().getAno());
                vagaResponse.setCor(vaga.getMoto().getCor());
                vagaResponse.setStatusMoto(vaga.getMoto().getStatus());
            }
            vagasDTO.add(vagaResponse);
        }
        return vagasDTO;
    }

}
