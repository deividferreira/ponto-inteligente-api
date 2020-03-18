/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import br.eti.deividferreira.pontointeligente.api.domain.repositories.EmpresaRepository;
import br.eti.deividferreira.pontointeligente.api.domain.services.EmpresaService;

/**
 * @author Deivid Ferreira
 *
 */
@Service
public class EmpresaServiceImpl implements EmpresaService {
   private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

   @Autowired
   private EmpresaRepository empresaRepository;

   @Override
   public Optional<Empresa> buscarPorCnpj(String cnpj) {
      log.info("Buscando uma empresa para o CNPJ: {}", cnpj);
      return this.empresaRepository.findByCnpj(cnpj);
   }

   @Override
   public Empresa persistir(Empresa empresa) {
      log.info("Inserindo uma nova empresa {}", empresa);
      return this.empresaRepository.save(empresa);
   }

}
