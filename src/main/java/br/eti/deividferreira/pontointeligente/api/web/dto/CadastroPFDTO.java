/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.dto;

import java.math.BigDecimal;
import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.enums.Perfil;
import br.eti.deividferreira.pontointeligente.api.utils.PasswordUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Deivid Ferreira
 *
 */
@Setter
@Getter
@ToString
public class CadastroPFDTO {

   private Long id;
   @NotEmpty(message = "Nome não pode ser vazio.")
   @Length(min = 3, max = 200,
      message = "Nome deve conter entre 3 e 200 caracteres.")
   private String nome;
   @NotEmpty(message = "Email não pode ser vazio.")
   @Email(message = "Email invalido.")
   @Length(min = 5, max = 200,
      message = "Email deve conter entre 5 e 200 caracteres.")
   private String email;
   @NotEmpty(message = "Senha não pode ser vazia.")
   @Length(min = 3, max = 50,
      message = "Senha deve conter entre 5 e 200 caracteres.")
   private String senha;
   @NotEmpty(message = "CPF não pode ser vazio.")
   @CPF(message = "CPF invalido.")
   private String cpf;
   @NotEmpty(message = "CNPJ da empresa não pode ser vazio.")
   @CNPJ(message = "CNPJ invalido.")
   private String cnpjEmpresa;
   private Optional<String> valorHora;
   private Optional<String> qtdHorasTrabalhoDia;
   private Optional<String> qtdHorasAlmoco;

   /**
    * 
    */
   public CadastroPFDTO() {
      this.valorHora = Optional.empty();
      this.qtdHorasTrabalhoDia = Optional.empty();
      this.qtdHorasAlmoco = Optional.empty();
   }

   /**
    * Converte os dados do DTO para funcionário.
    * 
    * @param cadastroPFDTO
    * @param result
    * @return Funcionario
    */
   public static Funcionario
      converterParaFuncionario(CadastroPFDTO cadastroPFDTO) {
      Funcionario funcionario = new Funcionario();
      funcionario.setNome(cadastroPFDTO.getNome());
      funcionario.setEmail(cadastroPFDTO.getEmail());
      funcionario.setCpf(cadastroPFDTO.getCpf());
      funcionario.setPerfil(Perfil.ROLE_USUARIO);
      funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDTO.getSenha()));
      cadastroPFDTO.getQtdHorasAlmoco()
            .ifPresent(
                  qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
      cadastroPFDTO.getQtdHorasTrabalhoDia()
            .ifPresent(qtdHorasTrabDia -> funcionario
                  .setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));
      cadastroPFDTO.getValorHora()
            .ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

      return funcionario;
   }

   /**
    * Popula o DTO de cadastro com os dados do funcionário e empresa.
    * 
    * @param funcionario
    * @return CadastroPFDTO
    */
   public static CadastroPFDTO converterCadastroPFDTO(Funcionario funcionario) {
      CadastroPFDTO cadastroPFDTO = new CadastroPFDTO();
      cadastroPFDTO.setId(funcionario.getId());
      cadastroPFDTO.setNome(funcionario.getNome());
      cadastroPFDTO.setEmail(funcionario.getEmail());
      cadastroPFDTO.setCpf(funcionario.getCpf());
      cadastroPFDTO.setCnpjEmpresa(funcionario.getEmpresa().getCnpj());
      funcionario.getQtdHorasAlmoco().ifPresent(qtdHorasAlmoco -> cadastroPFDTO
            .setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
      funcionario.getQtdHorasTrabalhoDia().ifPresent(
            qtdHorasTrabDia -> cadastroPFDTO
                  .setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
      funcionario.getValorHora()
            .ifPresent(valorHora -> cadastroPFDTO.setValorHora(Optional.of(valorHora.toString())));

      return cadastroPFDTO;
   }

}
