package br.eti.deividferreira.pontointeligente.api.domain;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Empresa;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.entities.Lancamento;
import br.eti.deividferreira.pontointeligente.api.domain.enums.Perfil;
import br.eti.deividferreira.pontointeligente.api.domain.enums.Tipo;
import br.eti.deividferreira.pontointeligente.api.domain.repositories.EmpresaRepository;
import br.eti.deividferreira.pontointeligente.api.domain.repositories.FuncionarioRepository;
import br.eti.deividferreira.pontointeligente.api.domain.repositories.LancamentoRepository;
import br.eti.deividferreira.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public abstract class PontoInteligenteAbstractTests {
   protected static final String EMAIL = "email@email.com";
   protected static final String OTHER_EMAIL = "email@other-email.com";
   protected static final String CPF = "24291173474";
   protected static final String OTHER_CPF = "12345678901";
   protected static final String CNPJ = "51463645000100";
   protected static final String NOME_EMPRESA = "Minha Empresa LTDA";
   protected static final String NOME_FUNCIONARIO = "Ciclano da Silva Junior";
   protected static final String SENHA = "123456";

   @Autowired
   protected LancamentoRepository lancamentoRepository;
   @Autowired
   protected FuncionarioRepository funcionarioRepository;
   @Autowired
   protected EmpresaRepository empresaRepository;

   protected Long funcionarioId;

   protected Funcionario obterDadosFuncionario(Empresa empresa)
      throws NoSuchAlgorithmException {

      Funcionario funcionario = new Funcionario();
      funcionario.setNome(NOME_FUNCIONARIO);
      funcionario.setPerfil(Perfil.ROLE_USUARIO);
      funcionario.setSenha(PasswordUtils.gerarBCrypt(SENHA));
      funcionario.setCpf(CPF);
      funcionario.setEmail(EMAIL);
      funcionario.setEmpresa(empresa);
      return funcionario;
   }

   protected Empresa obterDadosEmpresa() {
      Empresa empresa = new Empresa();
      empresa.setRazaoSocial(NOME_EMPRESA);
      empresa.setCnpj("51463645000100");
      return empresa;
   }

   protected Lancamento obterDadosLancamento(Funcionario funcionario) {
      Lancamento lancamento = new Lancamento();
      lancamento.setData(LocalDateTime.now());
      lancamento.setTipo(Tipo.INICIO_ALMOCO);
      lancamento.setFuncionario(funcionario);
      return lancamento;
   }

}
