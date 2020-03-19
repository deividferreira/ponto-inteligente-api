/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.web.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Deivid Ferreira
 *
 */
@NoArgsConstructor
public class Response<T> {

   @Getter
   @Setter
   private T data;
   @Setter
   private List<String> errors;

   public List<String> getErrors() {
      if (this.errors == null)
         this.errors = new ArrayList<>();
      return this.errors;
   }

}
