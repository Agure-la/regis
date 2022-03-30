package org.acme;

import org.passay.PasswordGenerator;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class RandomPasswordGenerator implements PasswordGenerator {

    @Override
    public String generate(){
        return UUID.randomUUID().toString();
    }
}
