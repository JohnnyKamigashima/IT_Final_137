import POJO.User;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class CadastroUsuarioTest extends BaseConfig {
        Gson gson = new Gson();
        long userId;
        String username;
        String userFirstName;
        String userLastName;
        String userEmail;
        String userPassword;
        String userPphone;
        int userStatus;
        String jsonUser;
        String apiKey;

        @BeforeEach
        public void setUp() {

            apiKey = "special-key";

            userId = 202305280923L;
            username = "HermioneGranger";
            userFirstName = "Hermione";
            userLastName = "Granger";
            userEmail = "hermionegranger@hogwar.ts";
            userPassword = "123456";
            userPphone = "123456789";
            userStatus = 1;

            User user = new User(userId, username, userFirstName, userLastName, userEmail, userPassword, userPphone, userStatus);
            jsonUser = gson.toJson(user);

            Response responseUser =
                    given()
                            .header("Content-Type", contentType)
                            .when()
                            .get(baseUrl + userEndpoint + "/" + username);

            if (responseUser.getStatusCode() == 200) {
                given()
                        .header("Content-Type", contentType)
                        .header("api_key", apiKey)
                        .when()
                        .delete(baseUrl + userEndpoint + "/" + username)
                        .then()
                        .statusCode(200);
                ;
                System.out.println("Deletado com sucesso");
            } else {
                System.out.println("Username nao encontrado");
            }
        }

        @Test
        @Order(2)
        @DisplayName("Cadastro Usuario")
        void cadastroUsuarioTest() {
            given()
                    .header("Content-Type", contentType)
                    .body(jsonUser)
                    .when()
                    .post(baseUrl + userEndpoint)
                    .then()
                    .statusCode(200)
                    .log().all()
                    .body("code", equalTo(200))
                    .body("message", equalTo(Long.toString(userId)))
            ;
        }
}
