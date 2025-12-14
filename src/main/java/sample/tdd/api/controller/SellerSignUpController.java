package sample.tdd.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.tdd.command.CreateSellerCommand;

@RestController
public record SellerSignUpController() {

    @PostMapping("/seller/signUp")
    ResponseEntity<?> singUp(@RequestBody CreateSellerCommand command) {

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String usernameRegex = "^[a-zA-z0-9-_]*$";

        if (command.email() == null) {
            return ResponseEntity.badRequest().build();
        } else if (!command.email().contains("@") || command.email().endsWith("@")) {
            return ResponseEntity.badRequest().build();
        } else if (!command.email().matches(emailRegex)) {
            return ResponseEntity.badRequest().build();
        } else if (command.username() == null) {
            return ResponseEntity.badRequest().build();
        } else if (command.username().isBlank()) {
            return ResponseEntity.badRequest().build();
        } else if (command.username().length() < 3) {
            return ResponseEntity.badRequest().build();
        } else if (!command.username().matches(usernameRegex)) {
            return ResponseEntity.badRequest().build();
        } else if (command.password() == null) {
            return ResponseEntity.badRequest().build();
        } else if (command.password().isBlank()) {
            return ResponseEntity.badRequest().build();
        } else if (command.password().length() < 8) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.noContent().build();
        }

    }
}

