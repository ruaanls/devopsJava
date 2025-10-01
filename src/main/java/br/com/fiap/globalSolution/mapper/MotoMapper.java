package br.com.fiap.globalSolution.mapper;

import br.com.fiap.globalSolution.DTO.MotoRequestDTO;
import br.com.fiap.globalSolution.DTO.MotoResponseDTO;
import br.com.fiap.globalSolution.entity.Motos;
import org.springframework.stereotype.Component;

@Component
public class MotoMapper
{

    public Motos requestToMoto (MotoRequestDTO motoRequestDTO)
    {
        Motos motos = new Motos();
        motos.setAno(motoRequestDTO.getAno());
        motos.setCor(motoRequestDTO.getCor());
        motos.setModelo(motoRequestDTO.getModelo());
        motos.setPlaca(motoRequestDTO.getPlaca());
        motos.setStatus(motoRequestDTO.getStatus());
        motos.setAno(motoRequestDTO.getAno());
        return motos;
    }

    public MotoResponseDTO motoToResponse(Motos moto)
    {
        MotoResponseDTO motoResponse = new MotoResponseDTO();
        motoResponse.setAno(moto.getAno());
        motoResponse.setCor(moto.getCor());
        motoResponse.setModelo(moto.getModelo());
        motoResponse.setPlaca(moto.getPlaca());
        motoResponse.setStatus(moto.getStatus());
        return motoResponse;
    }

    public void updateMotoFromRequest(MotoRequestDTO motoRequestDTO, Motos moto) {
        if (motoRequestDTO.getPlaca() != null) {
            moto.setPlaca(motoRequestDTO.getPlaca());
        }
        if (motoRequestDTO.getModelo() != null) {
            moto.setModelo(motoRequestDTO.getModelo());
        }
        moto.setAno(motoRequestDTO.getAno());

        if (motoRequestDTO.getCor() != null) {
            moto.setCor(motoRequestDTO.getCor());
        }
        if (motoRequestDTO.getStatus() != null) {
            moto.setStatus(motoRequestDTO.getStatus());
        }

    }


}
