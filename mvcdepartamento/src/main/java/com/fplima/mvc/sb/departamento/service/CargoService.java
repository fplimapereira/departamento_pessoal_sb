package com.fplima.mvc.sb.departamento.service;

import com.fplima.mvc.sb.departamento.domain.Cargo;

import java.util.List;

public interface CargoService {

     void salvar(Cargo cargo);

     void editar(Cargo cargo);

     void excluir(Long id);

     Cargo buscarPorId(Long id);

     List<Cargo> buscarTodos();

     boolean cargoTemFuncionarios(Long id);

}
