/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.controller;

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
import br.eti.deividferreira.pontointeligente.api.web.dto.CadastroPJDTO;
import br.eti.deividferreira.pontointeligente.api.web.response.Response;

/**
 * @author Deivid Ferreira
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/cadastrar-pj")
public class CadastroPJController {
   private static final Logger log = LoggerFactory
         .getLogger(CadastroPJController.class);

   private FuncionarioService funcionarioService;
   private EmpresaService empresaService;

   public CadastroPJController(
         FuncionarioService funcionarioService, EmpresaService empresaService) {
      this.empresaService = empresaService;
      this.funcionarioService = funcionarioService;
   }

   /**
    * @param cadastroPJDTO
    * @param result
    * @return
    */
   @PostMapping
   public ResponseEntity<Response<CadastroPJDTO>>
      cadastrar(@Valid @RequestBody CadastroPJDTO cadastroPJDTO, BindingResult result) {
      log.info("Cadastrando PJ: {}", cadastroPJDTO.toString());
      Response<CadastroPJDTO> response = new Response<>();

      this.validarDadosExistentes(cadastroPJDTO, result);
      Empresa empresa = CadastroPJDTO.converterParaEmpresa(cadastroPJDTO);
      Funcionario funcionario = CadastroPJDTO.converterParaFuncionario(cadastroPJDTO);

      if (result.hasErrors()) {
         log.error("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
         result.getAllErrors().forEach(
               error -> response.getErrors().add(error.getDefaultMessage()));

         return ResponseEntity.badRequest().body(response);
      }

      this.empresaService.persistir(empresa);
      funcionario.setEmpresa(empresa);
      this.funcionarioService.persistir(funcionario);

      response.setData(CadastroPJDTO.converterCadastro(funcionario));
      return ResponseEntity.ok(response);
   }

   /**
    * @param cadastroPJDTO
    * @param result
    */
   private void validarDadosExistentes(CadastroPJDTO cadastroPJDTO, BindingResult result) {
      this.empresaService.buscarPorCnpj(cadastroPJDTO.getCnpj())
            .ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente.")));

      this.funcionarioService.buscarPorCpf(cadastroPJDTO.getCpf())
            .ifPresent(
                  func -> result.addError(new ObjectError("funcionario", "CPF já existente.")));

      this.funcionarioService.buscarPorEmail(cadastroPJDTO.getEmail())
            .ifPresent(
                  func -> result.addError(new ObjectError("funcionario", "Email já existente.")));
   }

}
