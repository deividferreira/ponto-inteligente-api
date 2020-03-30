/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Deivid Ferreira
 *
 */
@Configuration
@Profile("prod")
public class DataBaseConfig {

   @Value("${spring.datasource.url}")
   private String dbUrl;

   @Bean
   public DataSource dataSource() {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
   }

}
