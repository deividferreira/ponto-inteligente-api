package br.eti.deividferreira.pontointeligente.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.eti.deividferreira.pontointeligente.api.domain.entities.Funcionario;
import br.eti.deividferreira.pontointeligente.api.domain.enums.Perfil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUserFactory {
   /**
    * Converte e gera um JwtUser com base nos dados de um funcionário.
    * 
    * @param funcionario
    * @return JwtUser
    */
   public static JwtUser create(Funcionario funcionario) {
      return new JwtUser(funcionario.getId(), funcionario.getEmail(), funcionario.getSenha(),
            mapToGrantedAuthorities(funcionario.getPerfil()));
   }

   /**
    * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
    * 
    * @param perfilEnum
    * @return List<GrantedAuthority>
    */
   private static List<GrantedAuthority> mapToGrantedAuthorities(Perfil perfilEnum) {
      List<GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
      return authorities;
   }

}
