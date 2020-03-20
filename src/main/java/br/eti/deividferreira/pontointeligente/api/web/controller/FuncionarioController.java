/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.controller;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.services.FuncionarioService;
import br.eti.deividferreira.pontointeligente.api.utils.PasswordUtils;
import br.eti.deividferreira.pontointeligente.api.web.dto.FuncionarioDTO;
import br.eti.deividferreira.pontointeligente.api.web.response.Response;

/**
 * @author Deivid Ferreira
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

   private static final Logger log = LoggerFactory
         .getLogger(FuncionarioController.class);

   private FuncionarioService funcionarioService;

   /**
    * @param funcionarioService
    */
   public FuncionarioController(FuncionarioService funcionarioService) {
      this.funcionarioService = funcionarioService;
   }

   /**
    * @param id
    * @return
    */
   @PutMapping(value = "/{id}")
   public ResponseEntity<Response<FuncionarioDTO>> atualizar(
      @PathVariable("id") Long id, @Valid @RequestBody FuncionarioDTO funcionarioDTO,
      BindingResult result) {
      log.info("Atualizando funcionario: {}", funcionarioDTO.toString());
      Response<FuncionarioDTO> response = new Response<>();

      Optional<Funcionario> funcionario = this.funcionarioService.buscaPorId(id);
      if (!funcionario.isPresent())
         result.addError(new ObjectError("funcionario", "Funcionario não encontrado."));

      this.atualizarDadosFuncionario(funcionario.get(), funcionarioDTO, result);

      if (result.hasErrors()) {
         log.error("Erro validando funcionario {}", result.getAllErrors());
         result.getAllErrors()
               .forEach(error -> response.getErrors().add(error.getDefaultMessage()));
         return ResponseEntity.badRequest().body(response);
      }

      this.funcionarioService.persistir(funcionario.get());
      response.setData(FuncionarioDTO.converterFuncionarioDTO(funcionario.get()));

      return ResponseEntity.ok(response);
   }

   /**
    * @param funcionario
    * @param funcionarioDTO
    * @param result
    */
   private void atualizarDadosFuncionario(
      Funcionario funcionario, FuncionarioDTO funcionarioDTO, BindingResult result) {
      funcionario.setNome(funcionarioDTO.getNome());

      if (!funcionario.getEmail().equals(funcionarioDTO.getEmail())) {
         this.funcionarioService.buscarPorEmail(funcionarioDTO.getEmail())
               .ifPresent(func -> result.addError(new ObjectError("email", "Email já existente.")));
         funcionario.setEmail(funcionarioDTO.getEmail());
      }

      funcionario.setQtdHorasAlmoco(null);
      funcionarioDTO.getQtdHorasAlmoco()
            .ifPresent(
                  qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

      funcionario.setQtdHorasTrabalhoDia(null);
      funcionarioDTO.getQtdHorasTrabalhoDia()
            .ifPresent(qtdHorasTrabDia -> funcionario
                  .setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));

      funcionario.setValorHora(null);
      funcionarioDTO.getValorHora()
            .ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

      if (funcionarioDTO.getSenha().isPresent()) {
         funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDTO.getSenha().get()));
      }
   }
}
