/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.eti.deividferreira.pontointeligente.api.domain.PontoInteligenteAbstractTests;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;

/**
 * @author Deivid Ferreira
 *
 */
@SpringBootTest
public class EmpresaRepositoryTest extends PontoInteligenteAbstractTests {

   @Before
   public void setUp() throws Exception {
      this.empresaRepository.save(this.obterDadosEmpresa());
   }

   @After
   public final void tearDown() {
      this.empresaRepository.deleteAll();
   }

   @Test
   public void testBuscarPorCnpj() {
      Empresa empresa = this.empresaRepository.findByCnpj(CNPJ).get();

      assertEquals(CNPJ, empresa.getCnpj());
   }

}
