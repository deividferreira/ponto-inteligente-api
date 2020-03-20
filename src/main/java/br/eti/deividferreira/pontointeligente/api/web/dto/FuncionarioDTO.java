/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.dto;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Deivid Ferreira
 *
 */
@Getter
@Setter
@ToString
public class FuncionarioDTO {

   private Long id;
   @NotEmpty(message = "Nome não pode ser vazio")
   @Length(min = 3, max = 200,
      message = "Nome deve conter entre 3 e 200 caracteres.")
   private String nome;
   @NotEmpty(message = "Email não pode ser vazio.")
   @Length(min = 5, max = 200,
      message = "Email deve conter entre 5 e 200 caracteres.")
   @Email(message = "Email invalido")
   private String email;
   private Optional<String> senha;
   private Optional<String> valorHora;
   private Optional<String> qtdHorasTrabalhoDia;
   private Optional<String> qtdHorasAlmoco;

   public FuncionarioDTO() {
      this.senha = Optional.empty();
      this.valorHora = Optional.empty();
      this.qtdHorasAlmoco = Optional.empty();
      this.qtdHorasTrabalhoDia = Optional.empty();
   }

   public static FuncionarioDTO converterFuncionarioDTO(Funcionario funcionario) {
      FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
      funcionarioDTO.setId(funcionario.getId());
      funcionarioDTO.setEmail(funcionario.getEmail());
      funcionarioDTO.setNome(funcionario.getNome());
      funcionario.getQtdHorasAlmoco().ifPresent(
            qtdHorasAlmoco -> funcionarioDTO
                  .setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
      funcionario.getQtdHorasTrabalhoDia().ifPresent(
            qtdHorasTrabDia -> funcionarioDTO
                  .setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
      funcionario.getValorHora()
            .ifPresent(valorHora -> funcionarioDTO.setValorHora(Optional.of(valorHora.toString())));

      return funcionarioDTO;
   }

}
