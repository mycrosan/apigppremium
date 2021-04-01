package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.UsuarioModel;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<UsuarioModel, Integer> {
}
