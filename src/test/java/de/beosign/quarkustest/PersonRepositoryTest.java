package de.beosign.quarkustest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.panache.common.Parameters;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class PersonRepositoryTest {
    @Inject
    PersonRepository personRepository;

    @Test
    @TestTransaction
    public void test_01_findAdults() {

        List<Person> adults = personRepository.findAdults();
        Collections.sort(adults, (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
        assertThat(adults, hasSize(2));
        assertThat(adults.get(0).getFirstName(), is("Marge"));
        assertThat(adults.get(1).getFirstName(), is("Homer"));
    }

    @Test
    @TestTransaction
    public void test_02_findPersonsWithAge() {

        List<Person> adults = personRepository.findWithAgeAtLeast(10);
        Collections.sort(adults, (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
        assertThat(adults, hasSize(3));
        assertThat(adults.get(0).getFirstName(), is("Bart"));
        assertThat(adults.get(1).getFirstName(), is("Marge"));
        assertThat(adults.get(2).getFirstName(), is("Homer"));
    }

    @Test
    @TestTransaction
    public void test_03_findByFirstName() {
        Person homer = personRepository.findByFirstName("Homer");
        assertThat(homer.getFirstName(), is("Homer"));
    }

    @Test
    @TestTransaction
    public void test_04a_updateAge() {
        Person homer = personRepository.findByFirstName("Homer");

        assertThat(personRepository.updateAge(homer.getId(), 66), is(1));

        personRepository.getEntityManager().clear();

        homer = personRepository.findByFirstName("Homer");
        assertThat(homer.getAge(), is(66));
    }

    @Test
    @TestTransaction
    public void test_04b_checkUnchanged() {
        Person homer = personRepository.findByFirstName("Homer");
        assertThat(homer.getAge(), is(42)); // changes in test_04a have been rolled back!
    }

    @Test
    @TestTransaction
    public void test_04c_updatePerson() {
        Person homer = personRepository.findByFirstName("Homer");

        homer.setFirstName("Homer J.");
        assertThat(personRepository.update(homer).getFirstName(), is("Homer J."));

        homer = personRepository.findByFirstName("Homer J.");
        assertThat(homer.getAge(), is(not(nullValue())));
    }

    @Test
    @TestTransaction
    public void test_05_delete() {
        assertThat(personRepository.count(), is(5L));
        personRepository.delete("firstName = :first", Parameters.with("first", "Bart"));
        assertThat(personRepository.count(), is(4L));

        Person homer = personRepository.findByFirstName("Homer");
        System.out.println("HOMER:" + homer);
    }

}
