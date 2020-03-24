package br.eti.deividferreira.pontointeligente.api.web.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Lancamento;
import br.eti.deividferreira.pontointeligente.api.domain.services.FuncionarioService;
import br.eti.deividferreira.pontointeligente.api.domain.services.LancamentoService;
import br.eti.deividferreira.pontointeligente.api.web.dto.LancamentoDTO;
import br.eti.deividferreira.pontointeligente.api.web.response.Response;

/**
 * @author Deivid Ferreira
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/lancamentos")
public class LancamentoController {
   private static final Logger log = LoggerFactory
         .getLogger(LancamentoController.class);

   @Value("${paginacao.qtd_por_pagina}")
   private int qtdPorPagina;

   private LancamentoService lancamentoService;
   private FuncionarioService funcionarioService;

   /**
    * @param lancamentoService
    * @param funcionarioService
    */
   public LancamentoController(
         LancamentoService lancamentoService, FuncionarioService funcionarioService) {
      this.lancamentoService = lancamentoService;
      this.funcionarioService = funcionarioService;
   }

   /**
    * @param funcionarioId
    * @param pag
    * @param ord
    * @param dir
    * @return
    */
   @GetMapping(value = "/funcionario/{funcionarioId}")
   public ResponseEntity<Response<Page<LancamentoDTO>>> listarPorFuncionario(
      @PathVariable("funcionarioId") Long funcionarioId,
      @RequestParam(value = "pag", defaultValue = "0") int pag,
      @RequestParam(value = "ord", defaultValue = "id") String ord,
      @RequestParam(value = "dir", defaultValue = "DESC") String dir) {

      log.info("Buscando lancamentos por ID  do funcionario: {}, pagina: {}", funcionarioId, pag);
      Response<Page<LancamentoDTO>> response = new Response<>();

      PageRequest pageRequest = PageRequest.of(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
      Page<Lancamento> lancamentos =
            this.lancamentoService.buscarPorFuncionarioId(funcionarioId, pageRequest);
      Page<LancamentoDTO> lancamentosDTO = lancamentos.map(LancamentoDTO::converterLancamento);

      response.setData(lancamentosDTO);
      return ResponseEntity.ok(response);
   }

   /**
    * @param id
    * @return
    */
   @GetMapping(value = "/{id}")
   public ResponseEntity<Response<LancamentoDTO>> listarPorId(@PathVariable("id") Long id) {
      log.info("Buscando lancamento por ID: {}", id);
      Response<LancamentoDTO> response = new Response<>();
      Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

      if (!lancamento.isPresent()) {
         log.info("Lancamento não encontrado para o ID: {}", id);
         response.getErrors().add("Lancamento não encontrado par ao id " + id);
         return ResponseEntity.badRequest().body(response);
      }

      response.setData(LancamentoDTO.converterLancamento(lancamento.get()));
      return ResponseEntity.ok(response);
   }

   /**
    * @param lancamentoDTO
    * @param result
    * @return
    */
   @PostMapping
   public ResponseEntity<Response<LancamentoDTO>>
      adicionar(@Valid @RequestBody LancamentoDTO lancamentoDTO, BindingResult result) {
      log.info("Adicionando lancamento: {}", lancamentoDTO);
      Response<LancamentoDTO> response = new Response<>();
      validarFuncionario(lancamentoDTO, result);
      Lancamento lancamento =
            LancamentoDTO.converterParaLancamento(lancamentoDTO, this.lancamentoService, result);

      if (result.hasErrors()) {
         log.error("Erro validando lancamento: {}", result.getAllErrors());
         result.getAllErrors()
               .forEach(error -> response.getErrors().add(error.getDefaultMessage()));
         return ResponseEntity.badRequest().body(response);
      }

      lancamento = this.lancamentoService.persistir(lancamento);
      response.setData(LancamentoDTO.converterLancamento(lancamento));
      return ResponseEntity.ok(response);
   }

   /**
    * @param id
    * @param lancamentoDTO
    * @param result
    * @return
    */
   @PutMapping(value = "/{id}")
   public ResponseEntity<Response<LancamentoDTO>> atualizar(
      @PathVariable("id") Long id, @Valid @RequestBody LancamentoDTO lancamentoDTO,
      BindingResult result) {
      log.info("Atualixando lancamento: {}", lancamentoDTO);
      Response<LancamentoDTO> response = new Response<>();
      validarFuncionario(lancamentoDTO, result);
      lancamentoDTO.setId(Optional.of(id));
      Lancamento lancamento =
            LancamentoDTO.converterParaLancamento(lancamentoDTO, this.lancamentoService, result);

      if (result.hasErrors()) {
         log.error("Erro validando lancamento: {}", result.getAllErrors());
         result.getAllErrors()
               .forEach(error -> response.getErrors().add(error.getDefaultMessage()));
         return ResponseEntity.badRequest().body(response);
      }

      lancamento = this.lancamentoService.persistir(lancamento);
      response.setData(LancamentoDTO.converterLancamento(lancamento));
      return ResponseEntity.ok(response);
   }

   @DeleteMapping(value = "/{id}")
   @PreAuthorize("hasAnyRole('ADMIN')")
   public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
      log.info("Removendo lancamento {}", id);
      Response<String> response = new Response<>();
      Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(id);

      if (!lancamento.isPresent()) {
         log.info("Erro ao remover devido ao lancamenti ID: {} ser invalido", id);
         response.getErrors()
               .add("Erro ao remover lancamento. Registro não encontrado para o id " + id);
         return ResponseEntity.badRequest().body(response);
      }

      this.lancamentoService.remover(id);
      return ResponseEntity.ok(new Response<String>());
   }

   /**
    * @param lancamentoDTO
    * @param result
    */
   private void validarFuncionario(LancamentoDTO lancamentoDTO, BindingResult result) {
      if (lancamentoDTO.getFuncionarioId() == null) {
         result.addError(new ObjectError("funcionario", "Funcionário não informado."));
         return;
      }

      log.info("Validando funcionário id {}: ", lancamentoDTO.getFuncionarioId());
      Optional<Funcionario> funcionario =
            this.funcionarioService.buscaPorId(lancamentoDTO.getFuncionarioId());
      if (!funcionario.isPresent()) {
         result.addError(
               new ObjectError("funcionario", "Funcionário não encontrado. ID inexistente."));
      }
   }
}
