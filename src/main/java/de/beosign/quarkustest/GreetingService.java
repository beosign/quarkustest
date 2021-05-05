package de.beosign.quarkustest;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GreetingService {
    private static final Logger log = LoggerFactory.getLogger(GreetingService.class);

    @PostConstruct
    void init() {
        log.debug("Greeting Service created");
    }

    public String getGreeting() {
        return "Greeted at " + new Date();
    }
}
