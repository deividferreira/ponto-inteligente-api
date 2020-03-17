/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.repositories;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;

/**
 * @author Deivid Ferreira
 *
 */
public interface EmpresaRepository extends DefaultRepository<Empresa> {

   /**
    * @param cnpj
    * @return
    */
   @Transactional(readOnly = true)
   Optional<Empresa> findByCnpj(String cnpj);

}
