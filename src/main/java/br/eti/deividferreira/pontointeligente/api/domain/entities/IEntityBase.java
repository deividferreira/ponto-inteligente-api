/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.entities;

import java.io.Serializable;

/**
 * @author Deivid Ferreira
 *
 */
public interface IEntityBase<T extends Serializable> {
	
	/**
	 * @return
	 */
	T getId();

}
