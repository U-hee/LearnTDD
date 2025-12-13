package sample.tdd.api.seller.signup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    void 올바르게_요청하면_204_No_Content_상태코드를_반환한다(
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
}
