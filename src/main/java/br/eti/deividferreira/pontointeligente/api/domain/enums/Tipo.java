/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.enums;

/**
 * @author Deivid Ferreira
 *
 */
public enum Tipo {
   INICIO_TRABALHO,
   TERMINO_TRABALHO,
   INICIO_ALMOCO,
   TERMINO_ALMOCO,
   INICIO_PAUSA,
   TERMINO_PAUSA;
   
   
   @Override
   public String toString() {
      return this.name();
   }
}
