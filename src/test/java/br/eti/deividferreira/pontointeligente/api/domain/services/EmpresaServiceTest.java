/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.eti.deividferreira.pontointeligente.api.domain.PontoInteligenteAbstractTests;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import br.eti.deividferreira.pontointeligente.api.domain.repositories.EmpresaRepository;

/**
 * @author Deivid Ferreira
 *
 */
@SpringBootTest
public class EmpresaServiceTest extends PontoInteligenteAbstractTests {

   @MockBean
   private EmpresaRepository empresaRepository;

   @Autowired
   private EmpresaService empresaService;

   @Before
   public void setUp() {
      BDDMockito.given(this.empresaRepository.findByCnpj(Mockito.anyString()))
            .willReturn(Optional.of(new Empresa()));
      BDDMockito.given(this.empresaRepository.save(Mockito.any(Empresa.class)))
            .willReturn(new Empresa());
   }

   @Test
   public void testBuscarEmpresaPorCnpj() {
      Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(CNPJ);

      assertTrue(empresa.isPresent());
   }

   @Test
   public void testInserirEmpresa() {
      Empresa empresa = this.empresaService.persistir(new Empresa());
      
      assertNotNull(empresa);
   }

}
