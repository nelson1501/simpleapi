package com.kalkivera.apps.simpleapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.kalkivera.apps.simpleapi.dao.PersonDao;
import com.kalkivera.apps.simpleapi.dto.Person;
import com.kalkivera.apps.simpleapi.exception.PersonNotFoundException;
import com.kalkivera.apps.simpleapi.util.AppConstants;
import com.kalkivera.apps.simpleapi.util.AppUtil;

@Service
public class PersonServiceImpl implements PersonService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonDao personDao;

	@Autowired
	private MessageSource messageSource;

	@Override
	public List<Person> findAll() {
		List<com.kalkivera.apps.simpleapi.model.Person> list = personDao.findAll();
		if (list.isEmpty()) {
			log.error("Person not found exception");
			throw new PersonNotFoundException(
					AppUtil.getMessage(messageSource, AppConstants.SIMPLEAPI_PERSON_NOT_FOUND.getName()),
					AppUtil.getMessage(messageSource, AppConstants.SIMPLEAPI_PERSON_NOT_FOUND.getName()));
		}
		return list.stream()
				.map(person -> new Person(person.getFirstName(), person.getLastName(), person.getAddress(),
						person.getCity(), person.getCountry(), person.getZipCode(), person.getPhone(),
						person.getEmail()))
				.collect(Collectors.toList());
	}

	@Override
	public Person getPersonById(Long id) {
		Optional<com.kalkivera.apps.simpleapi.model.Person> personById = personDao.findById(id);
		if (personById.isPresent()) {
			Person person = new Person();
			BeanUtils.copyProperties(personById.get(), person);
			return person;
		} else {
			log.error("Person not found exception");
			throw new PersonNotFoundException(
					AppUtil.getMessage(messageSource, AppConstants.SIMPLEAPI_PERSON_NOT_FOUND.getName()),
					AppUtil.getMessage(messageSource, AppConstants.SIMPLEAPI_PERSON_NOT_FOUND.getName()));
		}

	}

	@Override
	public Long savePerson(Person person) {
		com.kalkivera.apps.simpleapi.model.Person target = new com.kalkivera.apps.simpleapi.model.Person();
		BeanUtils.copyProperties(person, target);
		target.setUserAuthName("guest");
		com.kalkivera.apps.simpleapi.model.Person person2 = personDao.save(target);
		return person2.getId();
	}

	@Override
	public Long updatePerson(Person person, Long id) {
		boolean isPersonExists = personDao.existsById(id);
		if (!isPersonExists) {
			log.error("Invalid person ID");
			throw new PersonNotFoundException(
					AppUtil.getMessage(messageSource, AppConstants.SIMPLEAPI_INVALID_PERSONID.getName()),
					AppUtil.getMessage(messageSource, AppConstants.SIMPLEAPI_INVALID_PERSONID.getName()));
		}
		com.kalkivera.apps.simpleapi.model.Person target = new com.kalkivera.apps.simpleapi.model.Person();
		BeanUtils.copyProperties(person, target);
		target.setId(id);
		target.setUserAuthName("guest");
		com.kalkivera.apps.simpleapi.model.Person person2 = personDao.save(target);
		return person2.getId();
	}

	@Override
	public void deletePerson(Long id) {
		boolean isPersonExists = personDao.existsById(id);
		if (!isPersonExists) {
			log.error("Invalid person ID");
			throw new PersonNotFoundException(
					AppUtil.getMessage(messageSource, AppConstants.SIMPLEAPI_INVALID_PERSONID.getName()),
					AppUtil.getMessage(messageSource, AppConstants.SIMPLEAPI_INVALID_PERSONID.getName()));
		}
		personDao.deleteById(id);
	}

	@Override
	public Long patchPerson(Person person, Long id) {
		com.kalkivera.apps.simpleapi.model.Person personObj = null;
		try {
			personObj = personDao.getOne(id);
			personObj.setAddress(person.getAddress() == null ? personObj.getAddress() : person.getAddress());
			personObj.setCity(person.getCity() == null ? personObj.getCity() : person.getCity());
			personObj.setCountry(person.getCountry() == null ? personObj.getCountry() : person.getCountry());
			personObj.setEmail(person.getEmail() == null ? personObj.getEmail() : person.getEmail());
			personObj.setFirstName(person.getFirstName() == null ? personObj.getFirstName() : person.getFirstName());
			personObj.setLastName(person.getLastName() == null ? personObj.getLastName() : person.getLastName());
			personObj.setPhone(person.getPhone() == null ? personObj.getPhone() : person.getPhone());
			personObj.setZipCode(person.getZipCode() == null ? personObj.getZipCode() : person.getZipCode());
		} catch (EntityNotFoundException e) {
			throw new PersonNotFoundException(e.getMessage(),
					AppUtil.getMessage(messageSource, AppConstants.SIMPLEAPI_INVALID_PERSONID.getName()));
		}
		com.kalkivera.apps.simpleapi.model.Person person2 = personDao.save(personObj);
		return person2.getId();
	}

}
