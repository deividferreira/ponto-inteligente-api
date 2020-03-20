/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.services.EmpresaService;
import br.eti.deividferreira.pontointeligente.api.domain.services.FuncionarioService;
import br.eti.deividferreira.pontointeligente.api.web.dto.CadastroPFDTO;
import br.eti.deividferreira.pontointeligente.api.web.response.Response;

/**
 * @author Deivid Ferreira
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/cadastrar-pf")
public class CadastroPFController {
   private static final Logger log = LoggerFactory
         .getLogger(CadastroPFController.class);

   private EmpresaService empresaService;
   private FuncionarioService funcionarioService;

   /**
    * @param empresaService
    * @param funcionarioService
    */
   public CadastroPFController(
         EmpresaService empresaService, FuncionarioService funcionarioService) {
      super();
      this.empresaService = empresaService;
      this.funcionarioService = funcionarioService;
   }

   /**
    * @param cadastroPFDto
    * @param result
    * @return
    * @throws NoSuchAlgorithmException
    */
   @PostMapping
   public ResponseEntity<Response<CadastroPFDTO>> cadastrar(
      @Valid @RequestBody CadastroPFDTO cadastroPFDto,
      BindingResult result) {
      log.info("Cadastrando PF: {}", cadastroPFDto.toString());
      Response<CadastroPFDTO> response = new Response<>();

      validarDadosExistentes(cadastroPFDto, result);
      Funcionario funcionario = CadastroPFDTO.converterParaFuncionario(cadastroPFDto);

      if (result.hasErrors()) {
         log.error("Erro validando dados de cadastro PF: {}", result.getAllErrors());
         result.getAllErrors()
               .forEach(error -> response.getErrors().add(error.getDefaultMessage()));
         return ResponseEntity.badRequest().body(response);
      }

      Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPFDto.getCnpjEmpresa());
      empresa.ifPresent(funcionario::setEmpresa);
      this.funcionarioService.persistir(funcionario);

      response.setData(CadastroPFDTO.converterCadastroPFDTO(funcionario));
      return ResponseEntity.ok(response);
   }

   /**
    * @param cadastroPFDTO
    * @param result
    */
   private void validarDadosExistentes(CadastroPFDTO cadastroPFDTO, BindingResult result) {
      Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPFDTO.getCnpjEmpresa());
      if (!empresa.isPresent()) {
         result.addError(new ObjectError("empresa", "Empresa não cadastrada."));
      }

      this.funcionarioService.buscarPorCpf(cadastroPFDTO.getCpf())
            .ifPresent(
                  func -> result.addError(new ObjectError("funcionario", "CPF já existente.")));

      this.funcionarioService.buscarPorEmail(cadastroPFDTO.getEmail())
            .ifPresent(
                  func -> result.addError(new ObjectError("funcionario", "Email já existente.")));
   }
}
