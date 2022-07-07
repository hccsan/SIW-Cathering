package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	private BuffetRepository buffetRepository;

	@Autowired
	private CredentialsService credentialsService;

	@Transactional
	public void save(Buffet buffet) {
		buffetRepository.save(buffet);
	}

	@Transactional
	public Buffet inserisci(Buffet buffet) {
		return buffetRepository.save(buffet);
	}

	@Transactional
	public void delete(Buffet buffet) {
		buffetRepository.delete(buffet);
	}

	public Buffet findById(Long id) {
		return buffetRepository.findById(id).get();
	}

	public List<Buffet> findAll() {
		List<Buffet> elencoBuffet = new ArrayList<>();
		for (Buffet b : buffetRepository.findAll()) {
			elencoBuffet.add(b);
		}
		return elencoBuffet;
	}

	public List<Buffet> getByChef(Chef chef) {
		List<Buffet> buffetByChef = new ArrayList<>();
		List<Buffet> i = buffetRepository.findByChef(chef);
		for (Buffet b : i) {
			buffetByChef.add(b);
		}
		return buffetByChef;
	}

	public boolean alreadyExists(Buffet buffet) {
		return buffetRepository.existsByNome(buffet.getNome());
	}

	public void deleteById(Long id) {
		buffetRepository.deleteById(id);
	}

	@Transactional
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

}
