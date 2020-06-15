package com.kalkivera.apps.simpleapi.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kalkivera.apps.simpleapi.dto.Person;
import com.kalkivera.apps.simpleapi.service.PersonService;

@RestController
@RequestMapping("/api")
public class PersonResource {

	@Autowired
	private PersonService personService;

	@GetMapping("/simpleapi/v1/person")
	public List<Person> getAll() {
		return personService.findAll();
	}

	@GetMapping("/simpleapi/v1/person/{id}")
	public EntityModel<Person> getPersonById(@PathVariable(required = true) Long id) {
		Person personById = personService.getPersonById(id);
		EntityModel<Person> model = new EntityModel<>(personById);
		WebMvcLinkBuilder link1 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAll());
		model.add(link1.withRel("all-person"));
		return model;
	}

	@PostMapping("/simpleapi/v1/person")
	public ResponseEntity<Object> createPerson(@Valid @RequestBody Person person) {
		Long personId = personService.savePerson(person);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(personId).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/simpleapi/v1/person/{id}")
	public ResponseEntity<Object> updatePerson(@Valid @RequestBody Person person, @PathVariable(required = true) Long id) {
		personService.updatePerson(person, id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/simpleapi/v1/person/{id}")
	public void deletePerson(@PathVariable(required = true) Long id) {
		personService.deletePerson(id);
	}

	@PatchMapping("/simpleapi/v1/person/{id}")
	public ResponseEntity<Object> patchPerson(@RequestBody Person person, @PathVariable(required = true) Long id) {
		personService.patchPerson(person, id);
		return ResponseEntity.ok().build();
	}

}
