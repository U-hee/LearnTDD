package sample.tdd.generator;

import java.util.UUID;

public class UsernameGenerator {

    public static String generateUsername() {
        return UUID.randomUUID() + "username";
    }
}
