/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Lancamento;
import br.eti.deividferreira.pontointeligente.api.domain.enums.Tipo;
import br.eti.deividferreira.pontointeligente.api.domain.services.LancamentoService;
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
public class LancamentoDTO {

   private Optional<Long> id;
   @NotEmpty(message = "Data não pode ser vazia.")
   private String data;
   private String tipo;
   private String descricao;
   private String localizacao;
   private Long funcionarioId;

   public LancamentoDTO() {
      this.id = Optional.empty();
   }

   public static LancamentoDTO converterLancamento(Lancamento lancamento) {
      LancamentoDTO lancamentoDto = new LancamentoDTO();
      lancamentoDto.setId(Optional.of(lancamento.getId()));
      lancamentoDto.setData(
            lancamento.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
      lancamentoDto.setTipo(lancamento.getTipo().toString());
      lancamentoDto.setDescricao(lancamento.getDescricao());
      lancamentoDto.setLocalizacao(lancamento.getLocalizacao());
      lancamentoDto.setFuncionarioId(lancamento.getFuncionario().getId());

      return lancamentoDto;
   }

   public static Lancamento
      converterParaLancamento(
         LancamentoDTO lancamentoDTO, LancamentoService lancamentoService, BindingResult result) {
      Lancamento lancamento = new Lancamento();

      if (lancamentoDTO.getId().isPresent()) {
         Optional<Lancamento> lanc = lancamentoService.buscarPorId(lancamentoDTO.getId().get());
         if (lanc.isPresent()) {
            lancamento = lanc.get();
         } else {
            result.addError(new ObjectError("lancamento", "Lançamento não encontrado."));
         }
      } else {
         lancamento.setFuncionario(new Funcionario());
         lancamento.getFuncionario().setId(lancamentoDTO.getFuncionarioId());
      }

      lancamento.setDescricao(lancamentoDTO.getDescricao());
      lancamento.setLocalizacao(lancamentoDTO.getLocalizacao());
      DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
      lancamento.setData(LocalDateTime.parse(lancamentoDTO.getData(), formater));

      if (EnumUtils.isValidEnum(Tipo.class, lancamentoDTO.getTipo())) {
         lancamento.setTipo(Tipo.valueOf(lancamentoDTO.getTipo()));
      } else {
         result.addError(new ObjectError("tipo", "Tipo inválido."));
      }

      return lancamento;
   }

}
