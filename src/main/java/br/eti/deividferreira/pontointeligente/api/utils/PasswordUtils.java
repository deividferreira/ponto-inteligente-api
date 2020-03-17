/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Deivid Ferreira
 *
 */
@NoArgsConstructor(
   access = AccessLevel.PRIVATE)
public class PasswordUtils {

   private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

   /**
    * Gera Hash da Senha
    * 
    * @param senha
    * @return
    */
   public static String gerarBCrypt(String senha) {
      if (senha == null)
         return senha;

      log.info("Gerando hash com o BCrypt");
      return new BCryptPasswordEncoder().encode(senha);
   }

}
