package br.com.fiap.globalSolution.controller;

import br.com.fiap.globalSolution.DTO.LinhaResponseDTO;
import br.com.fiap.globalSolution.DTO.MotoRequestDTO;
import br.com.fiap.globalSolution.DTO.MotoResponseDTO;
import br.com.fiap.globalSolution.entity.Motos;
import br.com.fiap.globalSolution.mapper.MotoMapper;
import br.com.fiap.globalSolution.service.MotoService;
import br.com.fiap.globalSolution.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/motos")
public class MotoController
{
    @Autowired
    private MotoService motoService;

    @Autowired
    private VagaService vagaService;

    // ✅ Página inicial - Lista todas as motos
    @GetMapping
    public String listarMotos(Model model) {
        List<Motos> motos = motoService.findAll();
        model.addAttribute("motos", motos);
        return "motos/lista";
    }

    // ✅ Formulário para criar nova moto
    @GetMapping("/nova")
    public String novaMotoForm(Model model) {
        model.addAttribute("motoRequest", new MotoRequestDTO());
        return "motos/formulario";
    }

    // ✅ Processar criação de moto
    @PostMapping("/criar")
    public String criarMoto(@ModelAttribute MotoRequestDTO motoRequest,
                            RedirectAttributes redirectAttributes) {
        try {
            motoService.createMoto(motoRequest);
            redirectAttributes.addFlashAttribute("sucesso", "Moto cadastrada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar moto: " + e.getMessage());
        }
        return "redirect:/motos";
    }

    // ✅ Formulário para editar moto
    @GetMapping("/editar/{placa}")
    public String editarMotoForm(@PathVariable String placa, Model model) {
        try {
            MotoResponseDTO moto = motoService.findMotoByPlaca(placa);
            model.addAttribute("moto", moto);
            return "motos/formulario";
        } catch (Exception e) {
            model.addAttribute("erro", "Moto não encontrada");
            return "redirect:/motos";
        }
    }

    // ✅ Processar edição de moto
    @PostMapping("/editar/{placa}")
    public String editarMoto(@PathVariable String placa,
                             @ModelAttribute MotoRequestDTO motoRequest,
                             RedirectAttributes redirectAttributes) {
        try {
            motoService.updateMoto(motoRequest,placa);
            redirectAttributes.addFlashAttribute("sucesso", "Moto atualizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar moto: " + e.getMessage());
        }
        return "redirect:/motos";
    }

    // ✅ Excluir moto
    @GetMapping("/excluir/{placa}")
    public String excluirMoto(@PathVariable String placa, RedirectAttributes redirectAttributes) {
        try {
            motoService.deleteMotoByPlaca(placa);
            redirectAttributes.addFlashAttribute("sucesso", "Moto excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir moto: " + e.getMessage());
        }
        return "redirect:/motos";
    }

    // Exibe formulário para mover moto para vaga
    @GetMapping("/mover/{placa}")
    public String moverMotoForm(@PathVariable String placa, Model model) {
        try {
            MotoResponseDTO moto = motoService.findMotoByPlaca(placa);
            // Buscar todas as vagas livres
            var vagasLivres = vagaService.vagasLivres();
            model.addAttribute("moto", moto);
            model.addAttribute("vagasLivres", vagasLivres);
            return "motos/mover";
        } catch (Exception e) {
            model.addAttribute("erro", "Moto não encontrada ou erro ao buscar vagas.");
            return "redirect:/motos";
        }
    }

    // Processa movimentação da moto para vaga
    @PostMapping("/mover/{placa}")
    public String moverMoto(@PathVariable String placa, @RequestParam Long vagaId, RedirectAttributes redirectAttributes) {
        try {
            motoService.moverMotoParaVaga(placa, vagaId);
            redirectAttributes.addFlashAttribute("sucesso", "Moto movida para vaga com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao mover moto: " + e.getMessage());
        }
        return "redirect:/motos";
    }

    // Retirar moto da vaga
    @GetMapping("/retirar/{placa}")
    public String retirarMotoDaVaga(@PathVariable String placa, RedirectAttributes redirectAttributes) {
        try {
            motoService.retirarMotoDaVaga(placa);
            redirectAttributes.addFlashAttribute("sucesso", "Moto retirada da vaga com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao retirar moto da vaga: " + e.getMessage());
        }
        return "redirect:/motos";
    }






}
