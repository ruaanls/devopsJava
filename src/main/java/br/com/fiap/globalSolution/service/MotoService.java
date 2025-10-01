package br.com.fiap.globalSolution.service;

import br.com.fiap.globalSolution.DTO.MotoRequestDTO;
import br.com.fiap.globalSolution.DTO.MotoResponseDTO;
import br.com.fiap.globalSolution.entity.Motos;
import br.com.fiap.globalSolution.entity.StatusVaga;
import br.com.fiap.globalSolution.entity.Vagas;
import br.com.fiap.globalSolution.exception.MotoOrVagaDontExists;
import br.com.fiap.globalSolution.exception.MotoOrVagaExistsException;
import br.com.fiap.globalSolution.mapper.MotoMapper;
import br.com.fiap.globalSolution.repository.MotoRepository;
import br.com.fiap.globalSolution.repository.VagaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MotoService
{
    @Autowired
    private MotoMapper motoMapper;
    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private VagaRepository vagaRepository;

    public MotoResponseDTO createMoto (MotoRequestDTO request)
    {
        Motos moto = this.motoMapper.requestToMoto(request);
        if(this.motoRepository.findMotosByPlaca(request.getPlaca().toUpperCase()).isPresent())
        {
            throw new MotoOrVagaExistsException("A moto com a placa: "+ request.getPlaca()+ " já existe por favor crie uma nova moto com uma placa diferente");
        }
        else
        {
            moto = this.motoRepository.save(moto);
            return this.motoMapper.motoToResponse(moto);
        }

    }

    public MotoResponseDTO findMotoByPlaca(String placa)
    {
        Optional<Motos> moto = this.motoRepository.findMotosByPlaca(placa);
        if(moto.isPresent())
        {
            if(moto.get().getVaga()!= null)
            {
                MotoResponseDTO motoResponse = this.motoMapper.motoToResponse(moto.get());
                motoResponse.setColuna(moto.get().getVaga().getColuna());
                motoResponse.setLinha(moto.get().getVaga().getLinha());
                motoResponse.setIdVaga(moto.get().getVaga().getId());
                return motoResponse;
            }
            else
            {
                return this.motoMapper.motoToResponse(moto.get());
            }

        }
        else
        {
            throw new MotoOrVagaDontExists("A moto com a placa: "+ placa+ " não existe em nossa base de dados por favor informe uma placa válida!");
        }
    }

    public void deleteMotoByPlaca(String placa)
    {
        Optional<Motos> moto = this.motoRepository.findMotosByPlaca(placa);
        if(moto.isPresent())
        {
            this.motoRepository.delete(moto.get());
        }
        else
        {
            throw new MotoOrVagaDontExists("A moto com a placa: "+ placa+ " não existe em nossa base de dados por favor informe uma placa válida!");
        }
    }

    public MotoResponseDTO updateMoto(MotoRequestDTO motoRequestDTO, String placa)
    {
        Optional<Motos> moto = this.motoRepository.findMotosByPlaca(placa);
        if(moto.isPresent())
        {
            this.motoMapper.updateMotoFromRequest(motoRequestDTO,moto.get());
            Motos motoSalva = this.motoRepository.save(moto.get());
            return this.motoMapper.motoToResponse(motoSalva);
        }
        else
        {
            throw new MotoOrVagaDontExists("A moto com a placa: "+ placa+ " não existe em nossa base de dados por favor informe uma placa válida!");
        }

    }

    @Transactional
    public MotoResponseDTO moverMotoParaVaga(String placa, Long vagaId) {

        // 1. Busca a moto pela placa
        Motos moto = this.motoRepository.findMotosByPlaca(placa)
                .orElseThrow(() -> new MotoOrVagaDontExists("A moto com a placa: "+ placa+ " não existe em nossa base de dados por favor informe uma placa válida!"));

        // 2. Busca a vaga de destino
        Vagas vagaDestino = this.vagaRepository.findById(vagaId)
                .orElseThrow(() -> new MotoOrVagaDontExists("A vaga com o id: "+ vagaId+ " não existe por favor forneça um id existente"));

        // 3. Verifica se a vaga está livre
        if (vagaDestino.getStatusVaga() != StatusVaga.LIVRE) {
            throw new RuntimeException();
        }

        // 4. Move a moto para a nova vaga
        moto.setVaga(vagaDestino);
        vagaDestino.setStatusVaga(StatusVaga.OCUPADA);
        vagaDestino.setMoto(moto);


        // 5. Salva as alterações
        Motos motoAtualizada = this.motoRepository.save(moto);
        Vagas vagaAtualizada = this.vagaRepository.save(vagaDestino);

        MotoResponseDTO motoResponseDTO = this.motoMapper.motoToResponse(motoAtualizada);
        motoResponseDTO.setColuna(vagaDestino.getColuna());
        motoResponseDTO.setLinha(vagaDestino.getLinha());
        motoResponseDTO.setIdVaga(vagaDestino.getId());


        // 6. Converte para DTO
        return motoResponseDTO;
    }

    @Transactional
    public MotoResponseDTO retirarMotoDaVaga(String placa) {

        // 1. Busca a moto pela placa
        placa = placa.toUpperCase();
        String finalPlaca = placa;
        Motos moto = this.motoRepository.findMotosByPlaca(placa)
                .orElseThrow(() -> new MotoOrVagaDontExists("A moto com a placa: "+ finalPlaca + " não está em nenhuma vaga atualmente"));

        // 2. Verifica se a moto está em alguma vaga
        Vagas vagaAtual = moto.getVaga();
        if (vagaAtual == null) {
            throw new MotoOrVagaDontExists("A moto com a placa: "+ placa + " não está em nenhuma vaga atualmente");
        }

        
        // 3. Libera a vaga atual
        vagaAtual.setStatusVaga(StatusVaga.LIVRE);

        // 4. Remove a moto da vaga
        moto.setVaga(null);

        // 5. Salva as alterações
        Motos motoAtualizada = this.motoRepository.save(moto);
        Vagas vagaLiberada = this.vagaRepository.save(vagaAtual);

        // 6. Converte para DTO (sem informações da vaga)
        MotoResponseDTO motoResponseDTO = this.motoMapper.motoToResponse(motoAtualizada);
        motoResponseDTO.setColuna(null);
        motoResponseDTO.setLinha(null);
        motoResponseDTO.setIdVaga(null);

        return motoResponseDTO;
    }



    public List<Motos> findAll()
    {
        return this.motoRepository.findAll();
    }

    public List<MotoResponseDTO> findAllResponse()
    {

        List<Motos> moto = this.motoRepository.findAll();
        List<MotoResponseDTO> motoResponseDTOS = new ArrayList<>();
        for (Motos moto1 : moto) {
            MotoResponseDTO dto = this.motoMapper.motoToResponse(moto1);
            if(moto1.getVaga()!= null)
            {
                dto.setColuna(moto1.getVaga().getColuna());
                dto.setLinha(moto1.getVaga().getLinha());
                dto.setIdVaga(moto1.getVaga().getId());
                motoResponseDTOS.add(dto);
            }
            else
            {
                motoResponseDTOS.add(dto);
            }
        }
        return motoResponseDTOS;

    }



}
