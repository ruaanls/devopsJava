package br.com.fiap.globalSolution.controller;

import br.com.fiap.globalSolution.DTO.LinhaResponseDTO;
import br.com.fiap.globalSolution.DTO.VagaRequestDTO;
import br.com.fiap.globalSolution.DTO.VagaResponseDTO;
import br.com.fiap.globalSolution.entity.StatusVaga;
import br.com.fiap.globalSolution.entity.Vagas;
import br.com.fiap.globalSolution.service.VagaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vagas")
public class VagasRestController
{
    @Autowired
    private VagaService vagaService;


    @PostMapping
    public ResponseEntity<Vagas> criarVaga (@Valid @RequestBody VagaRequestDTO request ) {
        return new ResponseEntity<>(vagaService.createVaga(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VagaResponseDTO> findVagaById(@PathVariable Long id)
    {
        return new ResponseEntity<>(vagaService.findVagaById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vagas> editarVaga(@PathVariable Long id, @RequestBody VagaRequestDTO request) {
        return new ResponseEntity<>(vagaService.updateVaga(id, request),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluirVaga(@PathVariable Long id) {
        vagaService.deleteVaga(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/livres/{linha}")
    public ResponseEntity<LinhaResponseDTO> vagasLivres(@PathVariable String linha)
    {
        return new ResponseEntity<>(vagaService.vagasLivres(linha), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VagaResponseDTO>> findAllVagas()
    {
        return new ResponseEntity<>(vagaService.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/all/{status}")
    public ResponseEntity<List<VagaResponseDTO>> findAllVagas(@PathVariable StatusVaga status)
    {
        return new ResponseEntity<>(vagaService.findAllVagasStatus(status), HttpStatus.OK);
    }






}
