package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Piatto;
import it.uniroma3.siw.spring.service.PiattoService;

@Component
public class PiattoValidator implements Validator {

	@Autowired
	private PiattoService piattoService;

	@Override
	public boolean supports(Class<?> pClass) {
		return Piatto.class.equals(pClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (this.piattoService.alreadyExists((Piatto) target)) {
			errors.reject("buffet.duplicato");
		}
	}

}
