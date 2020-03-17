package br.eti.deividferreira.pontointeligente.api.domain.repositories;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;

/**
 * @author Deivid Ferreira
 *
 */
@Transactional(readOnly = true)
public interface FuncionarioRepository extends DefaultRepository<Funcionario> {

   /**
    * @param cpf
    * @return
    */
   Optional<Funcionario> findByCpf(String cpf);

   /**
    * @param email
    * @return
    */
   Optional<Funcionario> findByEmail(String email);

   /**
    * @param cpf
    * @param email
    * @return
    */
   Optional<Funcionario> findByCpfOrEmail(String cpf, String email);

}
