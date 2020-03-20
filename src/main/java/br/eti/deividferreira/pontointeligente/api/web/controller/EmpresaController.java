/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import br.eti.deividferreira.pontointeligente.api.domain.services.EmpresaService;
import br.eti.deividferreira.pontointeligente.api.web.dto.EmpresaDTO;
import br.eti.deividferreira.pontointeligente.api.web.response.Response;

/**
 * @author Deivid Ferreira
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/empresas")
public class EmpresaController {
   private static final Logger log = LoggerFactory
         .getLogger(EmpresaController.class);

   private EmpresaService empresaService;

   /**
    * @param empresaService
    */
   public EmpresaController(EmpresaService empresaService) {
      super();
      this.empresaService = empresaService;
   }

   /**
    * @param cnpj
    * @return
    */
   @GetMapping(value = "/cnpj/{cnpj}")
   public ResponseEntity<Response<EmpresaDTO>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
      log.info("Buscando empresa por CNPJ: {}", cnpj);
      Response<EmpresaDTO> response = new Response<>();
      Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cnpj);

      if (!empresa.isPresent()) {
         log.info("Empresa não encontrada para o CNPJ: {}", cnpj);
         response.getErrors().add("Empresa não encontrada para o CNPJ " + cnpj);

         return ResponseEntity.badRequest().body(response);
      }

      response.setData(EmpresaDTO.converterEmpresa(empresa.get()));
      return ResponseEntity.ok(response);
   }

}
