package sample.tdd.command;

public record CreateSellerCommand(
        String email,
        String username,
        String password
) {
}
