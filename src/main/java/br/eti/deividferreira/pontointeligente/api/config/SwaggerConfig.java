/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.eti.deividferreira.pontointeligente.api.security.utils.JwtTokenUtil;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Deivid Ferreira
 *
 */
@Configuration
@Profile("prod")
@EnableSwagger2
public class SwaggerConfig {
   
   private JwtTokenUtil jwtTokenUtil;
   private UserDetailsService userDetailsService;
   
   /**
    * @param jwtTokenUtil
    * @param userDetailsService
    */
   public SwaggerConfig(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
      this.jwtTokenUtil = jwtTokenUtil;
      this.userDetailsService = userDetailsService;
   }

   @Bean
   public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors
                  .basePackage("br.eti.deividferreira.pontointeligente.api.web.controller"))
            .paths(PathSelectors.any()).build()
            .apiInfo(apiInfo());
   }

   private ApiInfo apiInfo() {
      return new ApiInfoBuilder().title("Ponto Inteligente API")
            .description("Documentação da API Ponto Inteligente")
            .version("1.0")
            .build();
   }

   @Bean
   public SecurityConfiguration security() {
      String token;
      try {
         UserDetails userDetails =
               this.userDetailsService.loadUserByUsername("admin@myempresa.com");
         token = this.jwtTokenUtil.obterToken(userDetails);
      } catch (Exception e) {
         token = "";
         e.printStackTrace();
      }
      return new SecurityConfiguration(null, null, null, null, "Bearer " + token,
            ApiKeyVehicle.HEADER, "Authorization", ",");
   }

}
