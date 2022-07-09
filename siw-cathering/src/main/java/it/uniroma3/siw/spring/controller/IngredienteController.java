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

import it.uniroma3.siw.spring.model.Ingrediente;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.service.IngredienteService;
import it.uniroma3.siw.spring.service.PiattoService;

@Controller
public class IngredienteController {

	@Autowired
	private IngredienteService ingredienteService;

	@Autowired
	private PiattoService piattoService;

	@PostMapping("/ingrediente")
	public String addIngrediente(@RequestParam("idPiatto") String idPiatto,
			@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {

		Long id = Long.valueOf(idPiatto);
		Piatto piatto = piattoService.findById(id);
		ingrediente.setPiatto(piatto);

		if (!bindingResult.hasErrors()) {

			ingredienteService.save(ingrediente);

			model.addAttribute("ingrediente", ingrediente);
			model.addAttribute("elencoIngredienti", ingredienteService.findAll());
			model.addAttribute("role", ingredienteService.getCredentialsService().getRoleAuthenticated());

			return "elencoIngredienti.html";

		}
		return "ingredienteForm.html";
	}

	// prendo elenco degli ingredienti in base l'id del piatto
	@GetMapping("/piatto/{id}/elencoIngredienti")
	public String getElencoIngredienti(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		List<Ingrediente> elencoIngredienti = ingredienteService.getByPiatto(piatto);
		model.addAttribute("elencoIngredienti", elencoIngredienti);
		model.addAttribute("role", this.ingredienteService.getCredentialsService().getRoleAuthenticated());

		return "elencoIngredienti.html";
	}

	// ingrediente per l'id
	@GetMapping("/ingrediente/{id}")
	public String getIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);

		return "ingrediente.html";
	}

	// prendo il piatto per l'id e aggiungo un ingrediente
	@GetMapping("/admin/piatto/{id}/ingredienteForm")
	public String creaIngrediente(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		Ingrediente ingrediente = new Ingrediente();
		ingrediente.setPiatto(piatto);
		model.addAttribute("ingrediente", ingrediente);
		model.addAttribute("elencoIngredienti", ingredienteService.findAll());

		return "ingredienteForm.html";
	}

	// cancello -> conferma
	@GetMapping("admin/toDeleteIngrediente/{id}")
	public String toDeleteIngrediente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingrediente", ingredienteService.findById(id));

		return "toDeleteIngrediente.html";
	}

	// cancellazione
	@GetMapping("/admin/deleteIngrediente/{id}")
	public String cancellaIngrediente(@PathVariable("id") Long id, Model model) {
		ingredienteService.deleteById(id);
		model.addAttribute("elencoIngredienti", ingredienteService.findAll());
		model.addAttribute("role", ingredienteService.getCredentialsService().getRoleAuthenticated());

		return "elencoIngredienti.html";
	}

}
