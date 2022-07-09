package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.repository.ChefRepository;

public class ChefService {

	@Autowired
	private ChefRepository chefRepository;

	@Autowired
	private CredentialsService credentialsService;

	// ci pensa spring boot
	@Transactional
	public void save(Chef chef) {
		chefRepository.save(chef);
	}

	@Transactional
	public Chef saveChef(Chef chef) {
		return chefRepository.save(chef);
	}

	@Transactional
	public Chef inserisci(Chef chef) {
		return chefRepository.save(chef);

	}

	@Transactional
	public void delete(Chef chef) {
		chefRepository.delete(chef);
	}

	public Chef findById(Long id) {
		return chefRepository.findById(id).get(); // ritorna l'oggetto che ha preso
	}

	public List<Chef> findAll() {
		List<Chef> elencoChef = new ArrayList<>();
		for (Chef c : chefRepository.findAll()) {
			elencoChef.add(c);
		}
		return elencoChef;
	}

	public void deleteById(Long id) {
		chefRepository.deleteById(id);
	}

	public boolean alreadyExists(Chef chef) {
		return chefRepository.existsByNomeAndCognome(chef.getNome(), chef.getCognome());
	}

	@Transactional
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

}
