/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.enums.Perfil;
import br.eti.deividferreira.pontointeligente.api.utils.PasswordUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Deivid Ferreira
 *
 */
@ToString
@NoArgsConstructor
public class CadastroPJDTO {

   @Getter
   @Setter
   private Long id;
   @Getter
   @Setter
   @NotEmpty(message = "Nome não pode ser vazio")
   @Length(min = 3, max = 200,
      message = "Nome deve conter entre 3 e 200 caracteres.")
   private String nome;
   @Getter
   @Setter
   @NotEmpty(message = "Email não pode ser vazio.")
   @Length(min = 5, max = 200,
      message = "Email deve conter entre 5 e 200 caracteres.")
   @Email(message = "Email inválido.")
   private String email;
   @Getter
   @Setter
   @NotEmpty(message = "Senha não pode ser vazia.")
   private String senha;
   @Getter
   @Setter
   @NotEmpty(message = "CPF não pode ser vazio.")
   @CPF(message = "CPF inválido")
   private String cpf;
   @Getter
   @Setter
   @NotEmpty(message = "Razão social não pode ser vazio.")
   @Length(min = 5, max = 200,
      message = "Razão social deve conter entre 5 e 200 caracteres.")
   private String razaoSocial;
   @Getter
   @Setter
   @NotEmpty(message = "CNPJ não pode ser vazio.")
   @CNPJ(message = "CNPJ inválido.")
   private String cnpj;

   /**
    * @param cadastroPJDTO
    * @return
    */
   public static Empresa converterParaEmpresa(CadastroPJDTO cadastroPJDTO) {
      Empresa empresa = new Empresa();
      empresa.setCnpj(cadastroPJDTO.getCnpj());
      empresa.setRazaoSocial(cadastroPJDTO.getRazaoSocial());

      return empresa;
   }

   /**
    * @param cadastroPJDTO
    * @return
    */
   public static Funcionario
      converterParaFuncionario(CadastroPJDTO cadastroPJDTO) {
      Funcionario funcionario = new Funcionario();
      funcionario.setNome(cadastroPJDTO.getNome());
      funcionario.setEmail(cadastroPJDTO.getEmail());
      funcionario.setCpf(cadastroPJDTO.getCpf());
      funcionario.setPerfil(Perfil.ROLE_ADMIN);
      funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDTO.getSenha()));

      return funcionario;
   }

   /**
    * @param funcionario
    * @return
    */
   public static CadastroPJDTO converterCadastro(Funcionario funcionario) {
      CadastroPJDTO cadastroPJDto = new CadastroPJDTO();
      cadastroPJDto.setId(funcionario.getId());
      cadastroPJDto.setNome(funcionario.getNome());
      cadastroPJDto.setEmail(funcionario.getEmail());
      cadastroPJDto.setCpf(funcionario.getCpf());
      cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
      cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());

      return cadastroPJDto;
   }
}
