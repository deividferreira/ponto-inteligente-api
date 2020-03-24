/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Lancamento;
import br.eti.deividferreira.pontointeligente.api.domain.enums.Tipo;
import br.eti.deividferreira.pontointeligente.api.domain.services.FuncionarioService;
import br.eti.deividferreira.pontointeligente.api.domain.services.LancamentoService;
import br.eti.deividferreira.pontointeligente.api.web.dto.LancamentoDTO;

/**
 * @author Deivid Ferreira
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LancamentoControllerTest {

   @Autowired
   private MockMvc mvc;

   @MockBean
   private LancamentoService lancamentoService;

   @MockBean
   private FuncionarioService funcionarioService;

   private static final String URL_BASE = "/api/lancamentos/";
   private static final Long ID_FUNCIONARIO = 1L;
   private static final Long ID_LANCAMENTO = 1L;
   private static final String TIPO = Tipo.INICIO_TRABALHO.name();
   private static final DateTimeFormatter DATE_FORMAT =
         DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
   private static final String DATA = LocalDateTime.now().format(DATE_FORMAT);

   @Test
   @WithMockUser
   public void testCadastrarLancamento() throws Exception {
      Lancamento lancamento = obterDadosLancamento();
      BDDMockito.given(this.funcionarioService.buscaPorId(Mockito.anyLong()))
            .willReturn(Optional.of(new Funcionario()));
      BDDMockito.given(this.lancamentoService.persistir(Mockito.any(Lancamento.class)))
            .willReturn(lancamento);

      mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
            .content(this.obterJsonRequisicaoPost())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(ID_LANCAMENTO))
            .andExpect(jsonPath("$.data.tipo").value(TIPO))
            .andExpect(jsonPath("$.data.data").value(DATA))
            .andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
            .andExpect(jsonPath("$.errors").isEmpty());
   }

   @Test
   @WithMockUser
   public void testCadastrarLancamentoFuncionarioIdInvalido() throws Exception {
      BDDMockito.given(this.funcionarioService.buscaPorId(Mockito.anyLong()))
            .willReturn(Optional.empty());

      mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
            .content(this.obterJsonRequisicaoPost())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors").value("Funcionário não encontrado. ID inexistente."))
            .andExpect(jsonPath("$.data").isEmpty());
   }

   @Test
   @WithMockUser(username = "admin@admin.com", roles = { "ADMIN" })
   public void testRemoverLancamento() throws Exception {
      BDDMockito.given(this.lancamentoService.buscarPorId(Mockito.anyLong()))
            .willReturn(Optional.of(new Lancamento()));

      mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LANCAMENTO)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
   }
   
   @Test
   @WithMockUser
   public void testRemoverLancamentoNegado() throws Exception {
      BDDMockito.given(this.lancamentoService.buscarPorId(Mockito.anyLong()))
            .willReturn(Optional.of(new Lancamento()));

      mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LANCAMENTO)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
   }

   private String obterJsonRequisicaoPost() throws JsonProcessingException {
      LancamentoDTO lancamentoDto = new LancamentoDTO();
      lancamentoDto.setId(null);
      lancamentoDto.setData(DATA);
      lancamentoDto.setTipo(TIPO);
      lancamentoDto.setFuncionarioId(ID_FUNCIONARIO);
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(lancamentoDto);
   }

   private Lancamento obterDadosLancamento() {
      Lancamento lancamento = new Lancamento();
      lancamento.setId(ID_LANCAMENTO);
      lancamento.setData(LocalDateTime.parse(DATA, DATE_FORMAT));
      lancamento.setTipo(Tipo.valueOf(TIPO));
      lancamento.setFuncionario(new Funcionario());
      lancamento.getFuncionario().setId(ID_FUNCIONARIO);
      return lancamento;
   }
}
