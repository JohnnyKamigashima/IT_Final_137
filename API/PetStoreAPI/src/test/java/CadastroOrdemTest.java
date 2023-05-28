import ENUMS.Status;
import POJO.Category;
import POJO.Ordem;
import POJO.Pet;
import POJO.Tag;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class CadastroOrdemTest extends BaseConfig
{
    static Gson gson = new Gson();
    String petName;
    Long petId;
    static Long ordemId;
    static Long ordemPetId;
    static int ordemQuantity;
    static String ordemShipDate;
    static Status ordemStatus;
    static Boolean ordemComplete;
    static String nomePet;
    static Category categoryPet;
    static List<Tag> tagPet = new ArrayList<>();
    static Status statusPet;
    static List<String> urlPhotosPet = new ArrayList<>();
    static Pet pet;
    static String jsonPet;
    static String apiKey;
    static Ordem ordem;
    static String jsonOrdem;

    @BeforeAll
    public static void setUp() {

        apiKey = "special-key";
        ordemPetId = 202305280923L;
        ordemId = 202305280923L;
        ordemQuantity = 1;
        ordemStatus = Status.placed;
        ordemShipDate = Instant.now().toString();
        ordemComplete = true;

        ordem = new Ordem(ordemId, ordemPetId, ordemQuantity, ordemShipDate, ordemStatus, ordemComplete);
        jsonOrdem = gson.toJson(ordem);
        categoryPet = new Category(1, "Gato");
        nomePet = "Bichento";
        tagPet.add(new Tag(1, "Felino"));
        urlPhotosPet.add("https://static.wikia.nocookie.net/harrypotter/images/a/aa/Bichento_FH.png/revision/latest/scale-to-width-down/220?cb=20180831030300&path-prefix=pt-br");
        statusPet = Status.available;
        apiKey = "special-key";

        pet = new Pet(ordemPetId, categoryPet, nomePet, urlPhotosPet, tagPet, statusPet);
        jsonPet = gson.toJson(pet);

        Response responsePet = (Response)
                given()
                        .header("Content-Type", contentType)
                        .when()
                        .get(baseUrl + petEndpoint + "/" + ordemPetId);

        // Verifica e cria um pet se ele não existir
        if (responsePet.getStatusCode() == 404) {
            given()
                    .header("Content-Type", contentType)
                    .body(jsonPet)
                    .when()
                    .post(baseUrl + petEndpoint)
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(ordemPetId))
                    .body("name", equalTo(nomePet))
                    .body("status", equalTo(statusPet.name()))
                    .body("category.id", equalTo(categoryPet.getId()))
                    .body("category.name", equalTo(categoryPet.getName()))
                    .body("photoUrls[0]", equalTo(urlPhotosPet.get(0)))
                    .body("tags[0].id", equalTo(tagPet.get(0).getId()))
                    .body("tags[0].name", equalTo(tagPet.get(0).getName()))
            ;
        }
        // Verifica e deleta a ordem antes de cada teste
        Response responseOrdem =
            given()
                .header("Content-Type", contentType)
            .when()
                .get(baseUrl + storeEndpoint + "/" + ordemId);

        if (responseOrdem.getStatusCode() == 200) {
            given()
                .header("Content-Type", contentType)
                .header("api_key", apiKey)
            .when()
                .delete(baseUrl + storeEndpoint + "/" + ordemId)
            ;
            System.out.println("Deletado com sucesso");
        } else {
            System.out.println("OrdemId não encontrado");
        }
    }

    @Test
    @DisplayName("Venda o Bichento para a Hermione Granger")
    @Order(1)
    void cadastroOrdemTest() {
        given()
            .header("Content-Type", contentType)
            .body(jsonOrdem)
        .when()
            .post(baseUrl + storeEndpoint)
        .then()
            .statusCode(200)
            .body("id", equalTo(ordemId))
            .body("petId", equalTo(ordemPetId))
            .body("quantity", equalTo(ordemQuantity))
            .body("status", equalTo(ordemStatus.toString()))
            .body("complete", equalTo(ordemComplete))
        ;

        Response petResponse =
            given()
                .header("Content-Type", contentType)
            .when()
                .get(baseUrl + petEndpoint + "/" + ordemPetId);

        Assertions.assertEquals(200, petResponse.getStatusCode());
        Assertions.assertEquals(ordemPetId, petResponse.getBody().jsonPath().getLong("id"));

        petId = petResponse.getBody().jsonPath().getLong("id");
        petName = petResponse.getBody().jsonPath().getString("name");

        given()
            .header("Content-Type", ContentType.URLENC)
            .body("status=" + Status.sold)
        .when()
            .post(baseUrl + petEndpoint +"/" + petId)
        .then()
            .statusCode(200)
            .body("code", equalTo(200))
            .body("message", equalTo(petId.toString()))
        ;
    }

    @Test
    @DisplayName("Consulte o status do animal após a venda")
    @Order(2)
    void consultaOrdemTest() {
        given()
                .header("Content-Type", contentType)
        .when()
                .get(baseUrl + petEndpoint + "/" + ordemPetId)
        .then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo(Status.sold.toString()));
    }

    @Test
    @DisplayName("Consulte a ordem de venda do animal")
    @Order(3)
    void consultaOrdemIdTest() {
        given()
                .header("Content-Type", contentType)
        .when()
                .get(baseUrl + storeEndpoint + "/" + ordemId)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(ordemId))
                .body("petId", equalTo(ordemPetId))
                .body("quantity", equalTo(ordemQuantity))
                .body("status", equalTo(ordemStatus.toString()))
                .body("complete", equalTo(ordemComplete));
    }
}
