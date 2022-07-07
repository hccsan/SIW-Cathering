package it.uniroma3.siw.spring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long> {

	public Chef save(Chef chef);

	public Optional<Chef> findById(Long fileId);

	public boolean existsByNomeAndCognome(String nome, String cognome);

}
