package it.uniroma3.siw.spring.controller;

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

import it.uniroma3.siw.spring.controller.validator.PiattoValidator;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.PiattoService;

@Controller
public class PiattoController {

	@Autowired
	private PiattoService piattoService;

	@Autowired
	private PiattoValidator piattoValidator;

	@Autowired
	private BuffetService buffetService;

	@PostMapping("/piatto")
	public String addPiatto(@RequestParam("idPiatto") String idBuffet, @Valid @ModelAttribute("piatto") Piatto piatto,
			BindingResult bindingResult, Model model) {

		Long id = Long.valueOf(idBuffet);
		Buffet buffet = buffetService.findById(id);
		piatto.setBuffet(buffet);

		piattoValidator.validate(piatto, bindingResult);

		if (!bindingResult.hasErrors()) {
			// salvo il piatto
			piattoService.save(piatto);

			// aggiungo al modello
			model.addAttribute("piatto", piatto);
			model.addAttribute("elencoPiatti", piattoService.findAll());
			model.addAttribute("role", piattoService.getCredentialsService().getRoleAuthenticated());

			return "elencoPiatti.html";
		}
		return "piattoForm.html";
	}

	// elenco dei piatti tramite l'id del buffet
	@GetMapping("/buffet/{id}/elencoPiatti")
	public String getElencoPiatti(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		List<Piatto> elencoPiatti = piattoService.getByBuffet(buffet);
		model.addAttribute("buffet", buffet);
		model.addAttribute("elencoPiatti", elencoPiatti);
		model.addAttribute("role", piattoService.getCredentialsService().getRoleAuthenticated());

		return "elencoPiatti.html";
	}

	// piatto per il suo id
	@GetMapping("/piatto/{id}")
	public String getPiatto(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		Buffet buffet = piatto.getBuffet();
		Chef chef = buffet.getChef();
		model.addAttribute("chef", chef);
		model.addAttribute("buffet", buffet);
		model.addAttribute("piatto", piatto);
		return "piatto.html";
	}

	// prendo il buffet dall'id e aggiungo un piatto
	@GetMapping("admin/buffet/{id}/piattoForm")
	public String creaPiatto(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		Piatto piatto = new Piatto();
		piatto.setBuffet(buffet);
		model.addAttribute("piatto", piatto);
		model.addAttribute("elencoPiatti", piattoService.findAll());

		return "piattoForm.html";
	}

	// cancella -> conferma
	@GetMapping("/admin/toDeletePiatto/{id}")
	public String toDeletePiatto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", piattoService.findById(id));

		return "toDeletePiatto.html";
	}

	// confermo cancellazione
	@GetMapping("/admin/deletePiatto/{id}")
	public String deletePiatto(@PathVariable("id") Long id, Model model) {
		piattoService.deleteById(id);
		model.addAttribute("elencoPiatto", piattoService.findAll());
		model.addAttribute("role", piattoService.getCredentialsService().getRoleAuthenticated());

		return "elencoPiatto.html";
	}
}