package br.com.fiap.globalSolution.controller;

import br.com.fiap.globalSolution.DTO.LinhaResponseDTO;
import br.com.fiap.globalSolution.DTO.VagaRequestDTO;
import br.com.fiap.globalSolution.DTO.VagaResponseDTO;
import br.com.fiap.globalSolution.entity.Vagas;
import br.com.fiap.globalSolution.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/vagas")
public class VagasController
{

    @Autowired
    private VagaService vagaService;

    // ✅ Página inicial - Lista todas as vagas
    @GetMapping
    public String listarVagas(Model model) {
        List<VagaResponseDTO> vagas = vagaService.findAll();
        model.addAttribute("vagas", vagas);
        return "vagas/lista";
    }

    // ✅ Formulário para criar nova vaga
    @GetMapping("/nova")
    public String novaVagaForm(Model model) {
        model.addAttribute("vagaRequest", new VagaRequestDTO());
        return "vagas/formulario";
    }

    // ✅ Processar criação de vaga
    @PostMapping("/criar")
    public String criarVaga(@ModelAttribute VagaRequestDTO vagaRequest,
                            RedirectAttributes redirectAttributes) {
        try {
            vagaService.createVaga(vagaRequest);
            redirectAttributes.addFlashAttribute("sucesso", "Vaga criada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar vaga: " + e.getMessage());
        }
        return "redirect:/vagas";
    }

    // ✅ Formulário para editar vaga
    @GetMapping("/editar/{id}")
    public String editarVagaForm(@PathVariable Long id, Model model) {
        try {
            VagaResponseDTO vaga = vagaService.findVagaById(id);
            model.addAttribute("vaga", vaga);
            model.addAttribute("vagaRequest", new VagaRequestDTO());
            return "vagas/formulario";
        } catch (Exception e) {
            model.addAttribute("erro", "Vaga não encontrada");
            return "redirect:/vagas";
        }
    }

    // ✅ Processar edição de vaga
    @PostMapping("/editar/{id}")
    public String editarVaga(@PathVariable Long id,
                             @ModelAttribute VagaRequestDTO vagaRequest,
                             RedirectAttributes redirectAttributes) {
        try {
            vagaService.updateVaga(id, vagaRequest);
            redirectAttributes.addFlashAttribute("sucesso", "Vaga atualizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar vaga: " + e.getMessage());
        }
        return "redirect:/vagas";
    }

    // ✅ Excluir vaga
    @GetMapping("/excluir/{id}")
    public String excluirVaga(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            vagaService.deleteVaga(id);
            redirectAttributes.addFlashAttribute("sucesso", "Vaga excluída com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir vaga: " + e.getMessage());
        }
        return "redirect:/vagas";
    }

    // ✅ Visualizar layout de uma linha
    @GetMapping("/layout/{linha}")
    public String visualizarLayoutLinha(@PathVariable String linha, Model model) {
        try {
            LinhaResponseDTO layout = vagaService.vagasLivres(linha);
            model.addAttribute("layout", layout);
            return "vagas/layout";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar layout: " + e.getMessage());
            return "redirect:/vagas";
        }
    }



}
