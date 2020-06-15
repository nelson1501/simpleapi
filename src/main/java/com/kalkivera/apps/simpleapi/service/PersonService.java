package com.kalkivera.apps.simpleapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kalkivera.apps.simpleapi.dto.Person;

public interface PersonService {
	
	public List<Person> findAll();
	
	public Person getPersonById(Long id);
	
	public Long savePerson(Person person);
	
	public Long updatePerson(Person person, Long id);
	
	public void deletePerson(Long id);
	
	public Long patchPerson(Person person, Long id);

}
