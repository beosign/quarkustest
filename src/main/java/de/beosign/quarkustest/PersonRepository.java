package de.beosign.quarkustest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

    public Person findByFirstName(String firstName) {
        return find("firstName", firstName).firstResult();
    }

    public Person findByLastName(String lastName) {
        return find("lastName", lastName).firstResult();
    }

    public List<Person> findAdults() {
        return find("age >= 18").list();
    }

    public List<Person> findWithAgeAtLeast(int minAge) {
        return find("age >= :age", Parameters.with("age", minAge)).list();
    }

    @Transactional
    public int updateAge(long id, int newAge) {
        return update("age = ?1 where id = ?2", newAge, id);
    }

    @Transactional
    public Person update(Person person) {
        return getEntityManager(Person.class).merge(person);
    }

}
