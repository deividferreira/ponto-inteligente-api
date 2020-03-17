package br.eti.deividferreira.pontointeligente.api.domain.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;

/**
 * @author Deivid Ferreira
 *
 */
@SpringBootTest
public class FuncionarioRepositoryTest extends PontoInteligenteRepositoriesTests {

   @Before
   public void setUp() throws Exception {
      Empresa empresa = this.empresaRepository.save(this.obterDadosEmpresa());
      this.funcionarioRepository.save(this.obterDadosFuncionario(empresa));
   }

   @After
   public final void tearDown() {
      this.empresaRepository.deleteAll();
   }

   @Test
   public void testBuscarFuncionarioPorEmail() {
      Funcionario funcionario = this.funcionarioRepository
            .findByEmail(EMAIL).get();

      assertEquals(EMAIL, funcionario.getEmail());
   }

   @Test
   public void testBuscarFuncionarioPorCpf() {
      Funcionario funcionario = this.funcionarioRepository
            .findByCpf(CPF).get();

      assertEquals(CPF, funcionario.getCpf());
   }

   @Test
   public void testBuscarFuncionarioPorEmailECpf() {
      Funcionario funcionario = this.funcionarioRepository
            .findByCpfOrEmail(CPF, EMAIL).get();

      assertNotNull(funcionario);
   }

   @Test
   public void testBuscarFuncionarioPorEmailOuCpfParaEmailInvalido() {
      Funcionario funcionario =
            this.funcionarioRepository.findByCpfOrEmail(CPF, OTHER_EMAIL)
                  .get();

      assertNotNull(funcionario);
   }

   @Test
   public void testBuscarFuncionarioPorEmailECpfParaCpfInvalido() {
      Funcionario funcionario =
            this.funcionarioRepository.findByCpfOrEmail(OTHER_CPF, EMAIL)
                  .get();

      assertNotNull(funcionario);
   }
}
