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

import it.uniroma3.siw.spring.controller.validator.ChefValidator;
import it.uniroma3.siw.spring.model.Buffet;
import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.service.BuffetService;
import it.uniroma3.siw.spring.service.ChefService;
import it.uniroma3.siw.spring.service.PiattoService;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;

	@Autowired
	private ChefValidator chefValidator;

	@Autowired
	private PiattoService piattoService;

	@Autowired
	private BuffetService buffetService;

	@PostMapping("/chef")
	public String addChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model)
			throws IOException {

		chefValidator.validate(chef, bindingResult);

		if (!bindingResult.hasErrors()) {

			chefService.save(chef);

			model.addAttribute("chef", chef);
			model.addAttribute("elencoChef", chefService.findAll());
			model.addAttribute("role", chefService.getCredentialsService().getRoleAuthenticated());

			return "elencoChef.html";
		}
		return "chefForm.html";
	}

	@GetMapping("/elencoChef")
	public String getElencoChef(Model model) {
		List<Chef> elencoChef = chefService.findAll();

		model.addAttribute("elencoChef", elencoChef);
		model.addAttribute("role", chefService.getCredentialsService());
		return "elencoChef.html";
	}

	@GetMapping("/elencoTuttiPiatti")
	public String getElencoPiatti(Model model) {
		List<Piatto> elencoTuttiPiatti = piattoService.findAll();

		model.addAttribute("elencoPiatti", elencoTuttiPiatti);
		model.addAttribute("role", piattoService.getCredentialsService());
		return "elencoPiatti.html";
	}

	@GetMapping("/elencoTuttiBuffet")
	public String getElencoBuffet(Model model) {
		List<Buffet> elencoTuttiBuffet = buffetService.findAll();

		model.addAttribute("elencoBuffet", elencoTuttiBuffet);
		model.addAttribute("role", buffetService.getCredentialsService());

		return "elencoBuffet.html";
	}

	// lo chef per il suo id
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);

		return "chef.html";
	}

	@GetMapping("/admin/chefForm")
	public String getChefForm(Model model) {
		model.addAttribute("chef", new Chef());
		model.addAttribute("elencoChef", chefService.findAll());

		return "chefForm.html";
	}

	@GetMapping("/admin/toDeleteChef/{id}")
	public String toDeleteChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", chefService.findById(id));

		return "toDeleteChef.html";
	}

	@GetMapping("/admin/deleteChef/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		chefService.deleteById(id);

		model.addAttribute("elencoChef", chefService.findAll());
		model.addAttribute("role", chefService.getCredentialsService());

		return "elencoChef.html";
	}

	// cancellazione diretta
	@GetMapping("/admin/cancellaChef/{id}")
	public String cancellaChef(@PathVariable("id") Long id, Model model) {

		Chef chef = chefService.findById(id);
		chefService.delete(chef);
		model.addAttribute("elencoChef", chefService.findAll());
		model.addAttribute("role", chefService.getCredentialsService());

		return ("elencoChef.html");
	}

	@GetMapping("/admin/toModificaChef/{id}")
	public String toModificaChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", chefService.findById(id));

		return "chefFormUpdate.html";
	}

//	@PostMapping("/admin/modificaChef/{id}")
//	public String modificaChef(@PathVariable("id") Long id, Model model, @ModelAttribute("chef") Chef chef)
//			throws IOException {
//
//		Chef chefId = chefService.findById(id);
//
//	
//		return "redirect:/elencoChef";
//	}

}
