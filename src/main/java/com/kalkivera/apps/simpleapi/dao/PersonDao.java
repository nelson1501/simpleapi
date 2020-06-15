package com.kalkivera.apps.simpleapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalkivera.apps.simpleapi.model.Person;

@Repository
public interface PersonDao extends JpaRepository<Person, Long> {

}
