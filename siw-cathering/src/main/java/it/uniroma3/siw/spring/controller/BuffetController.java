package it.uniroma3.siw.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.controller.validator.BuffetValidator;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.ChefService;

@Controller
public class BuffetController {

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private BuffetValidator buffetValidator;

	@Autowired
	private ChefService chefService;

	@PostMapping("/buffet")
	public String addBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, @RequestParam("idChef") String idChef,
			BindingResult bindingResult, Model model) throws IOException {

		/**
		 * Serve per il passaggio dei parametri perche devo passare l'id dello chef per
		 * aggiungere il buffet a questo specifico chef
		 */

		Long id = Long.valueOf(idChef);
		Chef chef = chefService.findById(id);
		buffet.setChef(chef);

		buffetValidator.validate(buffet, bindingResult);

		if (!bindingResult.hasErrors()) {
			// salvo buffet
			buffetService.save(buffet);

			model.addAttribute("buffet", buffet);
			model.addAttribute("elencoBuffet", buffetService.findAll());
			model.addAttribute("role", buffetService.getCredentialsService().getRoleAuthenticated());

			// se e' andato tutto bene
			return "elencoBuffet.html";
		}

		return "buffetForm.html";
	}

	// prendo l'elenco dei buffet in base l'id dello chef
	@GetMapping("/chef/{id}/elencoBuffet")
	public String getElencoBuffet(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		List<Buffet> elencoBuffet = buffetService.getByChef(chef);
		model.addAttribute("elencoChef", elencoBuffet);
		model.addAttribute("role", buffetService.getCredentialsService().getRoleAuthenticated());
		model.addAttribute("chef", chef);

		return "elencoBuffet.html";
	}

	// prendo il buffet per il suo id
	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		Chef chef = buffet.getChef();
		model.addAttribute("chef", chef);
		model.addAttribute("buffet", buffet);

		return "buffet.html";
	}

	// al buffet collego il suo chef prendendo l'id di questo ultimo
	@GetMapping("/admin/chef/{id}/buffetForm")
	public String creaBuffetId(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		Buffet buffet = new Buffet();
		buffet.setChef(chef);
		model.addAttribute("buffet", buffet);
		model.addAttribute("elencoBuffet", buffetService.findAll());

		return "bufferForm.html";
	}

	// cancella -> conferma
	@GetMapping("/admin/toDeleteBuffet/{id}")
	public String toDeleteBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", buffetService.findById(id));

		return "toDeleteBuffet.html";
	}

	// confermo cancellazione
	@GetMapping("/admin/deleteBuffet/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
		buffetService.deleteById(id);
		model.addAttribute("elencoBuffet", buffetService.findAll());
		model.addAttribute("role", buffetService.getCredentialsService().getRoleAuthenticated());

		return "elencoBuffet.html"; // redirect?
	}

}
