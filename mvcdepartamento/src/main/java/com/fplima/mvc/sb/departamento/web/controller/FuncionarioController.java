package com.fplima.mvc.sb.departamento.web.controller;

import com.fplima.mvc.sb.departamento.domain.Cargo;
import com.fplima.mvc.sb.departamento.domain.Funcionario;
import com.fplima.mvc.sb.departamento.domain.UF;
import com.fplima.mvc.sb.departamento.service.CargoService;
import com.fplima.mvc.sb.departamento.service.FuncionarioService;
import com.fplima.mvc.sb.departamento.web.validator.FuncionarioValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcService;

    @Autowired
    private CargoService cargoService;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new FuncionarioValidator());
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Funcionario funcionario){
        return "/funcionario/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model){
        model.addAttribute("funcionarios", funcService.buscarTodos());
        return "/funcionario/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr){
        if(result.hasErrors()){
            return "funcionario/cadastro";
        }
        funcService.salvar(funcionario);
        attr.addFlashAttribute("success", "Funcionário inserido com sucesso.");
        return "redirect:/funcionarios/cadastrar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("funcionario", funcService.buscarPorId(id));
        return "/funcionario/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr){
        if(result.hasErrors()){
            return "funcionario/cadastro";
        }
        funcService.editar(funcionario);
        attr.addFlashAttribute("success", "Funcionario editado com sucesso.");
        return "redirect:/funcionarios/cadastrar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr){
        funcService.excluir(id);
        attr.addFlashAttribute("success",
                    "Funcionario removido com sucesso.");
        return "redirect:/funcionarios/listar";
    }

    @GetMapping("/buscar/nome")
    public String getPorNome(@RequestParam("nome") String nome, ModelMap model){
        model.addAttribute("funcionarios", funcService.buscarPorNome(nome));
        return "/funcionario/lista";
    }

    @GetMapping("/buscar/cargo")
    public String getPorCargo(@RequestParam("id") Long id, ModelMap model){
        model.addAttribute("funcionarios", funcService.buscarPorCargo(id));
        return "/funcionario/lista";
    }

    @GetMapping("/buscar/data")
    public String getPorDatas(@RequestParam(value = "entrada", required = false) LocalDate entrada,
                              @RequestParam(value = "saida", required = false) LocalDate saida,
                              ModelMap model){
        model.addAttribute("funcionarios", funcService.buscarPorDatas(entrada,saida));
        return "funcionario/lista";
    }

    @ModelAttribute("cargos")
    public List<Cargo> listaCargos(){
        return cargoService.buscarTodos();
    }

    @ModelAttribute("ufs")
    public UF[] getUFs(){
        return UF.values();
    }


}
