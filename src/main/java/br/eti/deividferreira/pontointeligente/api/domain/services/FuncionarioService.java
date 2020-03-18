/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.services;

import java.util.Optional;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;

/**
 * @author Deivid Ferreira
 *
 */
public interface FuncionarioService {

   /**
    * Insere um funcionário na base de dados
    * 
    * @param funcionario
    * @return {@link Funcionario}
    */
   Funcionario persistir(Funcionario funcionario);

   /**
    * retorna um funcionario dado o cpf
    * 
    * @param cpf
    * @return Optional<Funcionario>
    */
   Optional<Funcionario> buscarPorCpf(String cpf);

   /**
    * retorna um funcionario dado o email
    * 
    * @param email
    * @return Optional<Funcionario>
    */
   Optional<Funcionario> buscaPorEmail(String email);

   /**
    * retorna um funcionario dado um id
    * 
    * @param id
    * @return Optional<Funcionario>
    */
   Optional<Funcionario> buscaPorId(Long id);

}
