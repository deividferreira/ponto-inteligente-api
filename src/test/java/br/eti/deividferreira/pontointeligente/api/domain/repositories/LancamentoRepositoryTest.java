/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.repositories;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Lancamento;

/**
 * @author Deivid Ferreira
 *
 */
@SpringBootTest
public class LancamentoRepositoryTest extends PontoInteligenteRepositoriesTests {

   @Before
   public void setUp() throws NoSuchAlgorithmException {
      Empresa empresa = this.empresaRepository.save(this.obterDadosEmpresa());
      Funcionario funcionario =
            this.funcionarioRepository.save(this.obterDadosFuncionario(empresa));

      this.funcionarioId = funcionario.getId();

      this.lancamentoRepository.save(this.obterDadosLancamento(funcionario));
      this.lancamentoRepository.save(this.obterDadosLancamento(funcionario));
   }

   @After
   public void tearDown() {
      this.empresaRepository.deleteAll();
   }

   @Test
   public void testBuscarLancamentoPorFuncionarioId() {
      List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);

      assertEquals(2, lancamentos.size());
   }

   @Test
   public void testBuscarLancamentosPorFuncionarioIdPaginado() {
      Page<Lancamento> lancamentos =
            this.lancamentoRepository.findByFuncionarioId(funcionarioId, PageRequest.of(0, 10));

      assertEquals(2, lancamentos.getTotalElements());
   }
}
