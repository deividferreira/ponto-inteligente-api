/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Deivid Ferreira
 *
 */
public class PasswordUtilsTest {

   private static final String SENHA = "123456";
   private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

   @Test
   public void testSenhaNula() {
      assertNull(PasswordUtils.gerarBCrypt(null));
   }

   @Test
   public void testGerarHashSenha() {
      String hash = PasswordUtils.gerarBCrypt(SENHA);

      assertTrue(this.bCryptEncoder.matches(SENHA, hash));
   }

}
