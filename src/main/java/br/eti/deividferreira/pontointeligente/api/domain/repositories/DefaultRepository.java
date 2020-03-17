/**
 * 
 */
package br.eti.deividferreira.pontointeligente.api.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.deividferreira.pontointeligente.api.domain.entities.AbstractEntityBase;

/**
 * @author Deivid Ferreira
 *
 */
public interface DefaultRepository<T extends AbstractEntityBase> extends JpaRepository<T, Long> {

}
