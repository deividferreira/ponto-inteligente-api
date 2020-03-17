package br.eti.deividferreira.pontointeligente.api.domain.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.eti.deividferreira.pontointeligente.api.domain.enums.Perfil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Deivid Ferreira
 *
 */
@Entity
@ToString(callSuper = true)
@Table(name = "funcionarios")
@EqualsAndHashCode(callSuper = true)
public class Funcionario extends AbstractEntityBase {

   @Getter
   @Setter
   @Column(nullable = false)
   private String nome;
   @Getter
   @Setter
   @Column(nullable = false)
   private String senha;
   @Getter
   @Setter
   private String email;
   @Getter
   @Setter
   @Column(nullable = false)
   private String cpf;
   @Setter
   @Column(name = "valor_hora", nullable = true)
   private BigDecimal valorHora;
   @Setter
   @Column(name = "qtd_horas_trabalho_dia", nullable = true)
   private Float qtdHorasTrabalhoDia;
   @Getter
   @Setter
   @Column(name = "qtd_horas_almoco", nullable = true)
   private Float qtdHorasAlmoco;
   @Getter
   @Setter
   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private Perfil perfil;
   @Getter
   @Setter
   @JoinColumn(name = "empresa_id")
   @ManyToOne(fetch = FetchType.EAGER)
   private Empresa empresa;
   @Getter
   @Setter
   @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private List<Lancamento> lancamentos;

   @Transient
   public Optional<Float> getQtdHorasTrabalhoDia() {
      return Optional.ofNullable(this.qtdHorasTrabalhoDia);
   }

   @Transient
   public Optional<BigDecimal> getValorHora() {
      return Optional.ofNullable(this.valorHora);
   }

}
