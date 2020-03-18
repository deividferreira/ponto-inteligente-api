/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.services;

import java.util.Optional;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;

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
    * Insere uma nova empresa
    * 
    * @param empresa
    * @return {@link Empresa}
    */
   Empresa persistir(Empresa empresa);
}
