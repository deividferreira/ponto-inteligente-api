/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Deivid Ferreira
 *
 */
@Entity
@Table(name = "empresas")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Empresa extends AbstractEntityBase {

   @Getter
   @Setter
   @Column(name = "razao_social",nullable = false)
   private String razaoSocial;
   @Getter
   @Setter
   @Column(nullable = false)
   private String cnpj;

   @Getter
   @Setter
   @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private List<Funcionario> funcionarios;

}
