package sample.tdd.api.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.tdd.Seller;
import sample.tdd.SellerRepository;
import sample.tdd.command.CreateSellerCommand;

@RestController
public record SellerSignUpController(SellerRepository sellerRepository) {

    static final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    static final String usernameRegex = "^[a-zA-z0-9-_]{3,}$";

    @PostMapping("/seller/signUp")
    ResponseEntity<?> singUp(@RequestBody CreateSellerCommand command) {
        if (!isCommandValid(command)) {
            return ResponseEntity.badRequest().build();
        }

        var seller = new Seller();
        seller.setEmail(command.email());
        seller.setUsername(command.username());
        try {
            sellerRepository.save(seller);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.noContent().build();
    }

    private static boolean isCommandValid(CreateSellerCommand command) {
        return isEmailValid(command.email()) && isUsernameValid(command.username()) && isPasswordValid( command.password());
    }

    private static boolean isEmailValid(String email) {
        return email != null && email.matches(emailRegex);
    }

    private static boolean isUsernameValid(String username) {
        return username != null && username.matches(usernameRegex);

    }

    private static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 8;
    }
}

