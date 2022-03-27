package plan3.recruitment.backend.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import plan3.recruitment.backend.model.Person;

public class PersonStorageImpl implements PersonStorage {
    private final static List<Person> persons = new LinkedList<Person>();
    
	@Override
	public Optional<Person> fetch(String email) {
	   return persons.stream().filter(p -> p.email.equals(email))
			   .findFirst();
    }

	@Override
	public Person save(Person person) {
		if(!persons.contains(person)){
	       persons.add(person);
	       return person;
		}
		return null;
	}

	@Override
	public boolean remove(Person person) {
	  if(!persons.contains(person)) {
			return false;
	  }
	  return persons.remove(person);
	}

	@Override
	public List<Person> list() {
	  Collections.sort(persons, (o1, o2) -> o1.lastname.compareTo(o2.lastname));	
	  return persons;
	}

	@Override
	public Person update(String email, Person person) {
	  Optional<Person> optional = fetch(email);
	  if(optional.isPresent()) {
		  Person existingPerson = optional.get();
		  remove(existingPerson);
		  save(person);
		  return person;
	  }
	  return null;
	}	
}