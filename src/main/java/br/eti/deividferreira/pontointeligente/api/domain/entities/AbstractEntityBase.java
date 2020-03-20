/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Deivid Ferreira
 *
 */
@ToString
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode
public abstract class AbstractEntityBase implements IEntityBase<Long> {

   @Id
   @Getter
   @Setter
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @Getter
   @Column(name = "data_criacao", nullable = false)
   private LocalDateTime createdOn;
   @Getter
   @Column(name = "data_atualizacao")
   private LocalDateTime updatedOn;

   /**
    *
    */
   @PrePersist
   protected void beforeInsert() {
      this.createdOn = LocalDateTime.now();
   }

   /**
    * 
    */
   @PreUpdate
   protected void beforeUpdate() {
      this.updatedOn = LocalDateTime.now();
   }

}
