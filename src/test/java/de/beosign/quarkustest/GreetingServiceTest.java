package de.beosign.quarkustest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingServiceTest {
    @Inject
    GreetingService greetingService;

    @Test
    @TestTransaction
    public void store() {
        Greeting gr = new Greeting();
        gr.setMessage("My Msg");

        gr = greetingService.store(gr);

        assertThat(gr.getId(), is(not(-1)));
        assertThat(greetingService.find(gr.getId()).getMessage(), is("My Msg"));
    }

    @Test
    @TestTransaction
    public void delete() {
        Greeting gr = new Greeting();
        gr.setMessage("My Msg");

        gr = greetingService.store(gr);

        assertThat(gr.getId(), is(not(-1)));

        assertThat(greetingService.delete(gr.getId()), is(true));

        assertThat(greetingService.find(gr.getId()), is(nullValue()));
    }

}
