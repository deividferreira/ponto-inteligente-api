/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Lancamento;
import br.eti.deividferreira.pontointeligente.api.domain.repositories.LancamentoRepository;
import br.eti.deividferreira.pontointeligente.api.domain.services.LancamentoService;

/**
 * @author Deivid Ferreira
 *
 */
@Service
public class LancamentoServiceImpl implements LancamentoService {
   private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

   @Autowired
   private LancamentoRepository lancamentoRepository;

   @Override
   public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
      log.info("Buscando lançamentos para o funcionário ID {}", funcionarioId);
      return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
   }

   @Override
   public Optional<Lancamento> buscarPorId(Long id) {
      log.info("Buscando um lançamento pelo ID {}", id);
      return this.lancamentoRepository.findById(id);
   }

   @Override
   public Lancamento persistir(Lancamento lancamento) {
      log.info("Persistindo o lançamento: {}", lancamento);
      return this.lancamentoRepository.save(lancamento);
   }

   @Override
   public void remover(Long id) {
      log.info("Removendo o lançamento ID {}", id);
      this.lancamentoRepository.deleteById(id);
   }

}
