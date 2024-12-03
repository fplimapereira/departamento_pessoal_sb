package com.fplima.mvc.sb.departamento.web.controller;

import com.fplima.mvc.sb.departamento.domain.Departamento;
import com.fplima.mvc.sb.departamento.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService service;

    @GetMapping("/cadastrar")
    public String cadastrar(Departamento departamento){
        return "/departamento/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model){
        model.addAttribute("departamentos", service.buscarTodos());
        return "/departamento/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr){
        if(result.hasErrors()){
            return "departamento/cadastro";
        }
        service.salvar(departamento);
        attr.addFlashAttribute("success", "Departamento criado com sucesso.");
        return "redirect:/departamentos/cadastrar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("departamento", service.buscarPorId(id));
        return "/departamento/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr){
        if(result.hasErrors()){
            return "departamento/cadastro";
        }
        service.editar(departamento);
        attr.addFlashAttribute("success", "Departamento editado com sucesso.");
        return "redirect:/departamentos/cadastrar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, ModelMap model){
        if(service.departamentoTemCargos(id)){
            model.addAttribute("fail",
                    "Departamento não removido pois possui cargo(s) vinculado(s)");
        }
        else {
            service.excluir(id);
            model.addAttribute("success",
                    "Departamento removido com sucesso.");
        }
        return listar(model);
    }
}
