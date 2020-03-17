package br.eti.deividferreira.pontointeligente.api.domain.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.eti.deividferreira.pontointeligente.api.domain.enums.Tipo;
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
@Table(name = "lancamentos")
@EqualsAndHashCode(callSuper = true)
public class Lancamento extends AbstractEntityBase {

   @Getter
   @Setter
   @Column(nullable = false)
   private LocalDateTime data;
   @Getter
   @Setter
   @Column(nullable = true)
   private String descricao;
   @Getter
   @Setter
   @Column(nullable = true)
   private String localizacao;
   @Getter
   @Setter
   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private Tipo tipo;
   @Getter
   @Setter
   @JoinColumn(name = "funcionario_id")
   @ManyToOne(fetch = FetchType.EAGER)
   private Funcionario funcionario;
}
