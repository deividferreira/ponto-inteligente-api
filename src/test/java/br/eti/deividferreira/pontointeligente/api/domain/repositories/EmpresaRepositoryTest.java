/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;

/**
 * @author Deivid Ferreira
 *
 */
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class EmpresaRepositoryTest {

   private static final String CNPJ = "51463645000100";

   @Autowired
   private EmpresaRepository empresaRepository;

   @Before
   public void setUp() throws Exception {
      Empresa empresa = new Empresa();
      empresa.setRazaoSocial("Minha Empresa");
      empresa.setCnpj(CNPJ);

      this.empresaRepository.save(empresa);
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
