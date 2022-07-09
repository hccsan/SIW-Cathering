package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.repository.PiattoRepository;

public class PiattoService {

	@Autowired
	private PiattoRepository piattoRepository;

	@Autowired
	private CredentialsService credentialsService;

	@Transactional
	public Piatto save(Piatto piatto) {
		return piattoRepository.save(piatto);
	}

	@Transactional
	public void delete(Piatto piatto) {
		piattoRepository.delete(piatto);
	}

	public Piatto findById(Long id) {
		return piattoRepository.findById(id).get();
	}

	public List<Piatto> getByBuffet(Buffet buffet) {
		List<Piatto> piattiPerBuffet = new ArrayList<>();
		Iterable<Piatto> i = piattoRepository.findByBuffet(buffet);
		for (Piatto p : i) {
			piattiPerBuffet.add(p);
		}
		return piattiPerBuffet;
	}

	public List<Piatto> getByIngrediente(Ingrediente ingrediente) {
		List<Piatto> piattoPerIngrediente = new ArrayList<>();
		Iterable<Piatto> i = piattoRepository.findByIngredienti(ingrediente);
		for (Piatto p : i) {
			piattoPerIngrediente.add(p);
		}
		return piattoPerIngrediente;
	}

	public List<Piatto> findAll() {
		List<Piatto> elencoPiatti = new ArrayList<>();
		for (Piatto p : piattoRepository.findAll()) {
			elencoPiatti.add(p);
		}
		return elencoPiatti;
	}

	public List<Piatto> findAll(Long id) {
		List<Piatto> elencoPiatti = new ArrayList<>();
		for (Piatto p : piattoRepository.findAll()) {
			elencoPiatti.add(p);
		}
		return elencoPiatti;
	}

	public boolean alreadyExists(Piatto piatto) {
		return piattoRepository.existsByNome(piatto.getNome());
	}

	public void deleteById(Long id) {
		piattoRepository.deleteById(id);
	}

	@Transactional
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

}
