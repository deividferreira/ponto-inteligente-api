/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.dto;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Deivid Ferreira
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmpresaDTO {

   private Long id;
   private String razaoSocial;
   private String cnpj;

   /**
    * @param empresa
    * @return
    */
   public static EmpresaDTO converterEmpresa(Empresa empresa) {
      EmpresaDTO empresaDTO = new EmpresaDTO();
      empresaDTO.setId(empresa.getId());
      empresaDTO.setCnpj(empresa.getCnpj());
      empresaDTO.setRazaoSocial(empresa.getRazaoSocial());

      return empresaDTO;
   }

}
