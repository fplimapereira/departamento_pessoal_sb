package com.fplima.mvc.sb.departamento.web.controller;

import com.fplima.mvc.sb.departamento.domain.Cargo;
import com.fplima.mvc.sb.departamento.domain.Departamento;
import com.fplima.mvc.sb.departamento.service.CargoService;
import com.fplima.mvc.sb.departamento.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @Autowired
    private DepartamentoService depService;

    @GetMapping("/cadastrar")
    public String cadastrar(Cargo cargo){
        return "/cargo/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model){
        model.addAttribute("cargos", cargoService.buscarTodos());
        return "/cargo/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr){
        if(result.hasErrors()){
            return "cargo/cadastro";
        }
        cargoService.salvar(cargo);
        attr.addFlashAttribute("success", "Cargo inserido com sucesso.");
        return "redirect:/cargos/cadastrar";
    }

    @ModelAttribute("departamentos")
    public List<Departamento> listaDepartamentos(){
        return depService.buscarTodos();
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("cargo", cargoService.buscarPorId(id));
        return "/cargo/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr){
        if(result.hasErrors()){
            return "cargo/cadastro";
        }
        cargoService.editar(cargo);
        attr.addFlashAttribute("success", "Cargo editado com sucesso.");
        return "redirect:/cargos/cadastrar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr){
        if(cargoService.cargoTemFuncionarios(id)){
            attr.addFlashAttribute("fail",
                    "Cargo não removido pois possui funcionario(s) vinculado(s)");
        }
        else {
            cargoService.excluir(id);
            attr.addFlashAttribute("success",
                    "Cargo removido com sucesso.");
        }
        return "redirect:/cargos/listar";
    }
}
