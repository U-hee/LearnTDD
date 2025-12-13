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
        if (command.email() == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}

