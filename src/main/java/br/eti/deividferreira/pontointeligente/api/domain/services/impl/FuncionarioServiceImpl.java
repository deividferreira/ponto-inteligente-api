/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.repositories.FuncionarioRepository;
import br.eti.deividferreira.pontointeligente.api.domain.services.FuncionarioService;

/**
 * @author Deivid Ferreira
 *
 */
@Service
public class FuncionarioServiceImpl implements FuncionarioService {
   private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

   @Autowired
   private FuncionarioRepository funcionarioRepository;

   @Override
   public Funcionario persistir(Funcionario funcionario) {
      log.info("persistindo o funcionario: {}", funcionario);
      return this.funcionarioRepository.save(funcionario);
   }

   @Override
   public Optional<Funcionario> buscarPorCpf(String cpf) {
      log.info("buscando o funcionario pelo CPF: {}", cpf);
      return this.funcionarioRepository.findByCpf(cpf);
   }

   @Override
   public Optional<Funcionario> buscarPorEmail(String email) {
      log.info("buscando o funcionario pelo email: {}", email);
      return this.funcionarioRepository.findByEmail(email);
   }

   @Override
   public Optional<Funcionario> buscaPorId(Long id) {
      log.info("buscando o funcionario pelo id: {}", id);
      return this.funcionarioRepository.findById(id);
   }

}
