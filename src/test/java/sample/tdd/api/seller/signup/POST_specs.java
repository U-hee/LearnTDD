package sample.tdd.api.seller.signup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import sample.tdd.TddApplication;
import sample.tdd.command.CreateSellerCommand;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = TddApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DisplayName("POST /seller/signUp")
public class POST_specs {
    // 올바르게 요청하면 204 No Content 상태코드를 반환한다.
    @Test
    @DisplayName("올바르게 요청하면 204 No Content 상태코드를 반환한다.")
    void success_204(
            @Autowired TestRestTemplate client
    ) {
        // Arrange
        CreateSellerCommand command = new CreateSellerCommand("test@email.com", "test", "testPassword");

        // Act
            // 1st: API URI
            // 2cd: request Body
            // 3rd : Response Body
        ResponseEntity<Void> response = client.postForEntity("/seller/signUp", command, Void.class);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(204);
    }

    @Test
    @DisplayName("email 속성이 지정되지 않으면 400 Bad Request 상태코드를 반환한다.")
    void error_400(
            @Autowired TestRestTemplate client
    ) {
        // Arrange
        CreateSellerCommand command = new CreateSellerCommand(null, "test", "testPassword");

        // Act
        ResponseEntity<Void> response = client.postForEntity("/seller/signUp", command, Void.class);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(400);

    }

    @DisplayName("email 속성이 email 주소 형식이 아니면 400 Bad Request 상태코드를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",
            "invalid-email@",
            "invalid-email@aaa",
            "invalid-email@aaa.",
            "invalid-email@.com",
    })
    void error_400(String email, @Autowired TestRestTemplate client) {
        // Arrange
        CreateSellerCommand command = new CreateSellerCommand(email, "test", "testPassword");

        // Act
        ResponseEntity<Void> response = client.postForEntity("/seller/signUp", command, Void.class);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(400);

    }

    @DisplayName("username 속성이 지정되지 않으면 400 Bad Request 상태코드를 반환한다.")
    @Test
    void error_400_username(@Autowired TestRestTemplate client) {
        // Arrange
        CreateSellerCommand command = new CreateSellerCommand("test@email.com", null, "testPassword");

        // Act
        ResponseEntity<Void> response = client.postForEntity("/seller/signUp", command, Void.class);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @DisplayName("username 속성이 올바른 형식을 따르지 않으면 400 Bad Request 상태코드를 반환한다.")
    @ParameterizedTest
    @ValueSource( strings = {
            "",
            "2글",
            "test ",
            "test.",
            "test!"
    })
    void error_400_username_invalid(String username, @Autowired TestRestTemplate client) {
        // Arrange
        CreateSellerCommand command = new CreateSellerCommand("test@email.com", username, "testPassword");

        // Act
        ResponseEntity<Void> response = client.postForEntity("/seller/signUp", command, Void.class);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @DisplayName("username 속성이 올바른 형식을 따르면 204 No Content 상태코드를 반환한다.")
    @ParameterizedTest
    @ValueSource( strings = {
            "test",
            "ABCVEFDAFEWAFEAWFDSA",
            "01020322332",
            "test-",
            "test_"
    })
    void success_204_username_valid(String username, @Autowired TestRestTemplate client) {
        // Arrange
        CreateSellerCommand command = new CreateSellerCommand("test@email.com", username, "testPassword");

        // Act
        ResponseEntity<Void> response = client.postForEntity("/seller/signUp", command, Void.class);

        // Assert
        assertThat(response.getStatusCode().value()).isEqualTo(204);
    }
}
