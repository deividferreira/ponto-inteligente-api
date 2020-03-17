/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.repositories;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Lancamento;
import br.eti.deividferreira.pontointeligente.api.domain.enums.Perfil;
import br.eti.deividferreira.pontointeligente.api.domain.enums.Tipo;
import br.eti.deividferreira.pontointeligente.api.utils.PasswordUtils;

/**
 * @author Deivid Ferreira
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

   @Autowired
   private LancamentoRepository lancamentoRepository;
   @Autowired
   private FuncionarioRepository funcionarioRepository;
   @Autowired
   private EmpresaRepository empresaRepository;

   private Long funcionarioId;

   @Before
   public void setUp() throws NoSuchAlgorithmException {
      Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
      Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));

      this.funcionarioId = funcionario.getId();

      this.lancamentoRepository.save(obterDadosLancamento(funcionario));
      this.lancamentoRepository.save(obterDadosLancamento(funcionario));
   }

   private Lancamento obterDadosLancamento(Funcionario funcionario) {
      Lancamento lancamento = new Lancamento();
      lancamento.setData(LocalDateTime.now());
      lancamento.setTipo(Tipo.INICIO_ALMOCO);
      lancamento.setFuncionario(funcionario);
      return lancamento;
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

   private Funcionario obterDadosFuncionario(Empresa empresa)
      throws NoSuchAlgorithmException {

      Funcionario funcionario = new Funcionario();
      funcionario.setNome("Fulano de Tal");
      funcionario.setPerfil(Perfil.ROLE_USUARIO);
      funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
      funcionario.setCpf("24291173474");
      funcionario.setEmail("email@email.com");
      funcionario.setEmpresa(empresa);
      return funcionario;
   }

   private Empresa obterDadosEmpresa() {
      Empresa empresa = new Empresa();
      empresa.setRazaoSocial("Minha Empresa");
      empresa.setCnpj("51463645000100");
      return empresa;
   }

}
