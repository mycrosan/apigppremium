package br.compneusgppremium.api.repository;

import br.compneusgppremium.api.controller.model.RegistroMaquinaModel;
import br.compneusgppremium.api.controller.model.StatusMaquina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroMaquinaRepository extends JpaRepository<RegistroMaquinaModel, Long> {

    // Buscar por nome
    Optional<RegistroMaquinaModel> findByNomeAndDtDeleteIsNull(String nome);

    // Buscar todas as máquinas não deletadas
    List<RegistroMaquinaModel> findByDtDeleteIsNullOrderByDtCreateDesc();

    // Buscar máquinas ativas (não deletadas e com status ATIVA)
    List<RegistroMaquinaModel> findByStatusAndDtDeleteIsNullOrderByDtCreateDesc(StatusMaquina status);

    // Buscar por ID não deletado
    Optional<RegistroMaquinaModel> findByIdAndDtDeleteIsNull(Long id);

    // Buscar máquinas em manutenção
    List<RegistroMaquinaModel> findByStatusAndDtDeleteIsNull(StatusMaquina status);

    // Verificar se nome já existe (para validação de unicidade)
    boolean existsByNomeAndDtDeleteIsNull(String nome);

    // Verificar se nome já existe para outro registro (para atualização)
    @Query("SELECT COUNT(r) > 0 FROM maquina_registro r WHERE r.nome = :nome AND r.id != :id AND r.dtDelete IS NULL")
    boolean existsByNomeAndIdNotAndDtDeleteIsNull(@Param("nome") String nome, @Param("id") Long id);

    // Verificar se número de série já existe
    boolean existsByNumeroSerieAndDtDeleteIsNull(String numeroSerie);

    // Verificar se número de série já existe para outro registro (para atualização)
    @Query("SELECT COUNT(r) > 0 FROM maquina_registro r WHERE r.numeroSerie = :numeroSerie AND r.id != :id AND r.dtDelete IS NULL")
    boolean existsByNumeroSerieAndIdNotAndDtDeleteIsNull(@Param("numeroSerie") String numeroSerie, @Param("id") Long id);
}