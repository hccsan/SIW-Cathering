package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.repository.IngredienteRepository;

@Service
public class IngredienteService {

	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Autowired
	private CredentialsService credentialsService;

	@Transactional
	public void save(Ingrediente ingrediente) {
		ingredienteRepository.save(ingrediente);
	}

	@Transactional
	public void delete(Ingrediente ingrediente) {
		ingredienteRepository.delete(ingrediente);
	}

	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id).get();
	}

	public List<Ingrediente> getByPiatto(Piatto piatto) {
		List<Ingrediente> ingredientiPerPiatto = new ArrayList<>();
		Iterable<Ingrediente> i = ingredienteRepository.findByPiatto(piatto);
		for (Ingrediente ing : i) {
			ingredientiPerPiatto.add(ing);
		}
		return ingredientiPerPiatto;
	}

	public List<Ingrediente> findAll() {
		List<Ingrediente> elencoIngredienti = new ArrayList<>();
		for (Ingrediente ing : ingredienteRepository.findAll()) {
			elencoIngredienti.add(ing);
		}
		return elencoIngredienti;
	}

	public void deleteById(Long id) {
		ingredienteRepository.deleteById(id);
	}

	public boolean alreadyExists(Ingrediente ingrediente) {
		return ingredienteRepository.existsByName(ingrediente.getNome());
	}

	@Transactional
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}
}
