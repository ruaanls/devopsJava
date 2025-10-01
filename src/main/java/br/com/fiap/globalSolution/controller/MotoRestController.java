package br.com.fiap.globalSolution.controller;

import br.com.fiap.globalSolution.DTO.MotoRequestDTO;
import br.com.fiap.globalSolution.DTO.MotoResponseDTO;
import br.com.fiap.globalSolution.DTO.MoverMotoVagaDTO;
import br.com.fiap.globalSolution.service.MotoService;
import br.com.fiap.globalSolution.service.VagaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motos")
public class MotoRestController
{
    @Autowired
    private MotoService motoService;

    @Autowired
    private VagaService vagaService;

    @PostMapping
    public ResponseEntity<MotoResponseDTO> criarMoto(@Valid @RequestBody  MotoRequestDTO request ) {
        return new ResponseEntity<>(motoService.createMoto(request), HttpStatus.CREATED);
    }

    @PutMapping("/{placa}")
    public ResponseEntity<MotoResponseDTO> editarMoto(@Valid @PathVariable String placa,
                                      @RequestBody MotoRequestDTO request) {
        return new ResponseEntity<>(motoService.updateMoto(request, placa),HttpStatus.OK) ;
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity excluirMoto(@Valid @PathVariable String placa) {
        motoService.deleteMotoByPlaca(placa);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/moverVaga")
    public ResponseEntity< MotoResponseDTO> moverMoto(@Valid @RequestBody MoverMotoVagaDTO request) {
        return new ResponseEntity<>(motoService.moverMotoParaVaga(request.getPlaca(), request.getIdVaga()),HttpStatus.CREATED);
    }

    @GetMapping("/{placa}")
    public ResponseEntity<MotoResponseDTO> buscarMoto(@Valid @PathVariable String placa) {
        return new ResponseEntity<>(motoService.findMotoByPlaca(placa),HttpStatus.OK) ;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MotoResponseDTO>> listarTodasMotos ()
    {
        return new ResponseEntity<>(this.motoService.findAllResponse(), HttpStatus.OK);
    }

    @PostMapping("/retirarVaga/{placa}")
    public ResponseEntity<MotoResponseDTO> retirarMoto (@PathVariable String placa)
    {
        return new ResponseEntity<>(motoService.retirarMotoDaVaga(placa), HttpStatus.OK);
    }


}
