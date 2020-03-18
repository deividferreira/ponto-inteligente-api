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
import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.repositories.FuncionarioRepository;

/**
 * @author Deivid Ferreira
 *
 */
@SpringBootTest
public class FuncionarioServiceTest extends PontoInteligenteAbstractTests {

   @MockBean
   private FuncionarioRepository repository;

   @Autowired
   private FuncionarioService service;

   @Before
   public void setUp() {
      BDDMockito.given(this.repository.save(Mockito.any(Funcionario.class)))
            .willReturn(new Funcionario());
      BDDMockito.given(this.repository.findById(Mockito.anyLong()))
            .willReturn(Optional.of(new Funcionario()));
      BDDMockito.given(this.repository.findByEmail(Mockito.anyString()))
            .willReturn(Optional.of(new Funcionario()));
      BDDMockito.given(this.repository.findByCpf(Mockito.anyString()))
            .willReturn(Optional.of(new Funcionario()));
   }

   @Test
   public void testPersistirFuncionario() {
      Funcionario funcionario = this.service.persistir(new Funcionario());

      assertNotNull(funcionario);
   }

   @Test
   public void testBuscarFuncionarioPorId() {
      Optional<Funcionario> funcionario = this.service.buscaPorId(1L);

      assertTrue(funcionario.isPresent());
   }

   @Test
   public void testBuscarFuncionarioPorEmail() {
      Optional<Funcionario> funcionario = this.service.buscaPorEmail(EMAIL);

      assertTrue(funcionario.isPresent());
   }

   @Test
   public void testBuscarFuncionarioPorCpf() {
      Optional<Funcionario> funcionario = this.service.buscaPorEmail(CPF);

      assertTrue(funcionario.isPresent());
   }

}
