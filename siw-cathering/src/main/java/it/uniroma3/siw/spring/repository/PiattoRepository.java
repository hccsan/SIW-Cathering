package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {

	public boolean existsByNome(String nome);

	public List<Piatto> findByBuffet(Buffet buffet);

	public List<Piatto> findByIngredienti(Ingrediente ingrediente);

}
