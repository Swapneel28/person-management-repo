package plan3.recruitment.backend.service;

import org.junit.jupiter.api.extension.ExtendWith;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import plan3.recruitment.backend.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@ExtendWith(DropwizardExtensionsSupport.class)
public class PersonStorageTest {
	
	PersonStorage personStorage = new PersonStorageImpl();

	@Test
	public void testSave() {
		 Person swapnil = Person.valueOf("Swapnil","Redekar","swapnil.redekar@example.com");
	     Person actualSwapnil = personStorage.save(swapnil);
	     assertEquals(swapnil,actualSwapnil);
	     
	     Person narendra = Person.valueOf("Narendra","Patel","n.patel@example.com");
	     Person actualNarendra = personStorage.save(narendra);
	     assertEquals(narendra,actualNarendra);
	     
	     Person duplicatePerson = personStorage.save(swapnil);
	     assertNull(duplicatePerson);
	}
	
	@Test
	public void testRemove() {
		Person swapnil = Person.valueOf("Swapnil","Redekar","swapnil.redekar@example.com");
		personStorage.save(swapnil);
		assertTrue(personStorage.remove(swapnil));
		
		Person nonExistingPerson = Person.valueOf("Rakesh","Patil","rakesh.p@example.com");
		assertFalse(personStorage.remove(nonExistingPerson));
	}
	
	@Test
	public void testUpdate() {
		 Person narendra = Person.valueOf("Narendra","Patel","n.patel@example.com");
		 personStorage.save(narendra);
		 
		 Person neeta = Person.valueOf("Neeta","Patel","n.patel@example.com");
		 Person updatedNeeta = personStorage.update(neeta.email, neeta);
		 assertEquals(neeta,updatedNeeta);
		 
		 Person shama = Person.valueOf("Shama","Khan","s.khan@example.com");
		 assertNull(personStorage.update(shama.email, shama));
	}
		
}