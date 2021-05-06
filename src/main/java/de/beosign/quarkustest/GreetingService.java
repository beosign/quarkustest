package de.beosign.quarkustest;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GreetingService {
    private static final Logger log = LoggerFactory.getLogger(GreetingService.class);

    @Inject
    EntityManager em;

    @PostConstruct
    void init() {
        log.debug("Greeting Service created");
    }

    @Transactional
    public Greeting find(int id) {
        return em.find(Greeting.class, id);
    }

    @Transactional
    public Greeting store(Greeting greeting) {
        if (greeting.getId() == -1) {
            em.persist(greeting);
        } else {
            greeting = em.merge(greeting);
        }
        return greeting;
    }

    @Transactional
    public boolean delete(int id) {
        Greeting greeting = find(id);
        if (greeting == null) {
            log.debug("No greeting found with id {}", id);
            return false;
        }

        em.remove(greeting);
        log.debug("Greeting found with id {} to delete", id);
        return true;
    }
}
