/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.services;

import java.util.Optional;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;

/**
 * @author Deivid Ferreira
 *
 */
public interface EmpresaService {

   /**
    * Dado um cnpj retorna uma Empresa
    * 
    * @param cnpj
    * @return @link Optional<Empresa>
    */
   Optional<Empresa> buscarPorCnpj(String cnpj);

   /**
    * Dado um funcionario retorna a empresa a qual ele pertence
    * 
    * @param funcionario
    * @return @link Optional<Empresa>
    */
   Optional<Empresa> buscarPorFuncionario(Funcionario funcionario);

   /**
    * Insere uma nova empresa
    * 
    * @param empresa
    * @return {@link Empresa}
    */
   Empresa persistir(Empresa empresa);
}
