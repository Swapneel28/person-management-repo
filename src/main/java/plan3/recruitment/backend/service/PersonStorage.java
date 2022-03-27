package plan3.recruitment.backend.service;

import java.util.List;
import java.util.Optional;

import plan3.recruitment.backend.model.Person;

public interface PersonStorage {

    /**
     * Return an {@link Optional} wrapping the {@link Person} with the supplied email address or an
     * {@link java.util.Optional#empty()} if the persons doesn't exist in this {@link PersonStorage}.
     */
     Optional<Person> fetch(String email);

    /**
     * Add the given {@link Person} to this {@link PersonStorage}
     */
    Person save(final Person person);

    /**
     * Remove the given {@link Person}. Return {@code true} if the {@link Person} existed and was removed, {@code false}
     * if the person didn't exist
     */
    boolean remove(final Person person);

    /**
     * Return all {@link Person}s in this {@link PersonStorage}
     */
    List<Person> list();
    
    Person update(final String email, final Person person);
}