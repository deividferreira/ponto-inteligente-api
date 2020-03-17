/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.repositories;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Lancamento;

/**
 * @author Deivid Ferreira
 *
 */
@Transactional(readOnly = true)
@NamedQuery(name = "LancamentoRepository.findByFuncionarioId",
   query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id = :funcionarioId")
public interface LancamentoRepository extends DefaultRepository<Lancamento> {

   /**
    * @param funcionarioId
    * @return
    */
   List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

   /**
    * @param funcionarioId
    * @param pageable
    * @return
    */
   Page<Lancamento> findByFuncionarioId(
      @Param("funcionarioId") Long funcionarioId,
      Pageable pageable);

}
