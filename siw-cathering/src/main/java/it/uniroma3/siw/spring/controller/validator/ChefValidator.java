package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Chef;
import it.uniroma3.siw.spring.service.ChefService;

@Component
public class ChefValidator implements Validator {

	@Autowired
	private ChefService chefService;

	@Override
	public boolean supports(Class<?> pClass) {
		return Chef.class.equals(pClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// downcast di object
		if (this.chefService.alreadyExists((Chef) target)) {
			errors.reject("chef.duplicato");
		}
	}

}
